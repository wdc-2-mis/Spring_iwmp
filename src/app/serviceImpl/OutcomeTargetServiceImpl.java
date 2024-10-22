package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.OutcomeTargetBean;
import app.dao.OutcomeTargetDao;
import app.dao.PhysicalAchievementDao;
import app.model.master.WdcpmksyMOutcome;
import app.model.project.WdcpmksyOutcomeTargetTranx;
import app.service.OutcomeTargetService;
@Service("outcomeTargetService")
public class OutcomeTargetServiceImpl implements OutcomeTargetService {

	@Autowired
	OutcomeTargetDao outcomeTargetDao;
	
	@Override
	public List<WdcpmksyMOutcome> getActivityDetail(Integer finYr) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.getActivityDetail(finYr);
	}

	@Override
	public String saveAsDraftOutcomeTarget(Integer pCode, Integer finYr, String outcomeid, String outcomedetailid,
			String target,String createdby) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.saveAsDraftOutcomeTarget(pCode, finYr, outcomeid, outcomedetailid, target, createdby);
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId, String userType) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.getUserToForward( regId, userType) ;
	}

	@Override
	public String forwardOutcomeTargetFromPIA(Integer pCode, Integer finYr, String outcomeid, String outcomedetailid,
			String target, Integer sentFrom, Integer sentTo, String createdBy) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.forwardOutcomeTargetFromPIA(pCode, finYr, outcomeid, outcomedetailid, target, sentFrom, sentTo, createdBy);
	}

	@Override
	public List<WdcpmksyOutcomeTargetTranx> getForwardedOutcome(Integer pCode, Integer finYr) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.getForwardedOutcome(pCode, finYr);
	}

	@Override
	public List<OutcomeTargetBean> viewMovement(Integer regId) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.viewMovement(regId);
	}

	@Override
	public List<OutcomeTargetBean> checkForAlreadyForwardedOutcome(Integer targetid, Integer regId) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.checkForAlreadyForwardedOutcome( targetid, regId);
	}

	@Override
	public String actionByWCDCSLNA(Integer sentTo, Integer sentFrom, Integer targetid, Character action, String remarks,
			String userType) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.actionByWCDCSLNA( sentTo, sentFrom, targetid, action, remarks, userType);
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId, String userType, Character action,
			Integer targetid) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.getUserToForward( regId, userType, action,	targetid);
	}

	@Override
	public List<OutcomeTargetBean> getOutcomeDetails(Integer targetid) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.getOutcomeDetails(targetid);
	}

	@Override
	public List<OutcomeTargetBean> getOutcomeTargetMovementDetails(Integer targetid) {
		// TODO Auto-generated method stub
		return outcomeTargetDao.getOutcomeTargetMovementDetails(targetid);
	}

}
