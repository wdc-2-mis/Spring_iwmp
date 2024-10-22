package app.serviceImpl;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.AssetIdBean;
import app.bean.PhysicalAchievementBean;
import app.model.WdcpmksyProjectAssetLivelihoodStatus;
import app.model.WdcpmksyProjectAssetProductionStatus;
import app.dao.PhysicalAchievementDao;
import app.model.IwmpProjectAssetStatus;
import app.model.WdcpmksyProjectAssetEPAStatus;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyProjectPhyAssetAchievement;
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.model.project.WdcpmksyProjectPhysicalAchievementTranx;
import app.service.PhysicalAchievementService;

@Service("PhysicalAchievementService")
public class PhysicalAchievementServiceImpl implements PhysicalAchievementService{
	
	@Autowired
	PhysicalAchievementDao physicalAchievementDao;
	
	@Override
	public List<PhysicalAchievementBean> getActivityWithTarget(Integer pCode,Integer finYr,Integer monthId) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getActivityWithTarget(pCode,finYr,monthId);
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getAssetofActivity(Integer pCode, Integer activityCode,Integer finYr,String enddt) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getAssetofActivity(pCode,activityCode,finYr, enddt);
	}

	@Override
	public List<String> getCurrentFinYrMonthId(Integer pCode,String finYr) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getCurrentFinYrMonthId(pCode,finYr);
	}

	@Override
	public String getFinancialYearForPlan(Integer pCode) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getFinancialYearForPlan(pCode);
	}

	@Override
	public String saveAssetAchievement(List<String> assetAcheivement, Integer finYr, Integer monthId, String userid) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.saveAssetAchievement(assetAcheivement,finYr,monthId, userid);
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId,String userType,Character action,Integer achid) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getUserToForward(regId,userType,action,achid);
	}

	@Override
	public String checkDistrictApprovalReuiredOrNot(Integer regId) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.checkDistrictApprovalReuiredOrNot(regId);
	}
 
	@Override
	public String saveAchievementAsDraft(Integer pCode, String activityCode, String ach, Integer finYr,
			Integer monthId, Integer sentFrom, Integer sentTo,String createdBy, boolean blsconf) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.saveAchievementAsDraft(pCode, activityCode, ach, finYr, monthId, sentFrom, sentTo, createdBy, blsconf);
	}

	@Override
	public LinkedHashMap<String, List<PhysicalAchievementBean>> getAchievementData(Integer regId) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getAchievementData(regId);
	}

	@Override
	public List<PhysicalAchievementBean> checkForAlreadyForwardedAchievement(Integer achId, Integer regId) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.checkForAlreadyForwardedAchievement(achId, regId);
	}

	@Override
	public String forwardAchievementByWCDC(Integer sentTo, Integer sentFrom, Integer achid, Character action, String remarks,
			String userType) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.forwardAchievementByWCDC(sentTo, sentFrom, achid, action, remarks, userType);
	}

	@Override
	public List<PhysicalAchievementBean> viewMovement(Integer regId) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.viewMovement(regId);
	}

	@Override
	public List<WdcpmksyProjectPhysicalAchievementDetails> getAchievementDetails(Integer achievementid) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getAchievementDetails(achievementid);
	}

	@Override
	public List<PhysicalAchievementBean> getAchievementMovementDetails(Integer achievementid) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getAchievementMovementDetails(achievementid);
	}

	@Override
	public String getMinumFinYear(Integer pCode) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getMinumFinYear(pCode);
	}

	@Override
	public List<PhysicalAchievementBean> viewAchievement(Integer regId) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.viewAchievement(regId);
	}

	@Override
	public List<PhysicalAchievementBean> viewCompletedMovement(Integer regId) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.viewCompletedMovement(regId);
	}

	@Override
	public List<IwmpProjectAssetStatus> getProjectPhysicalAssetStatus(BigInteger assetid) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getProjectPhysicalAssetStatus(assetid);
	}

	@Override
	public List<WdcpmksyProjectPhyAssetAchievement> getProjectPhysicalAssetAchievement(BigInteger assetid) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getProjectPhysicalAssetAchievement(assetid);
	}

	@Override
	public List<WdcpmksyProjectAssetLivelihoodStatus> getProjectLivelihoodAssetStatus(int assetid) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getProjectLivelihoodAssetStatus(assetid);
	}

	@Override
	public List<WdcpmksyProjectAssetProductionStatus> getProjectProductionAssetStatus(int assetid) {
		return physicalAchievementDao.getProjectProductionAssetStatus(assetid);
	}

	@Override
	public List<WdcpmksyProjectAssetEPAStatus> getProjectEPAAssetStatus(int assetid) {
		// TODO Auto-generated method stub
		return physicalAchievementDao.getProjectEPAAssetStatus(assetid);
	}

}
