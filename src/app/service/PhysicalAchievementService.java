package app.service;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Service;

import app.bean.AssetIdBean;
import app.bean.PhysicalAchievementBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.model.IwmpProjectAssetStatus;
import app.model.WdcpmksyProjectAssetEPAStatus;
import app.model.WdcpmksyProjectAssetLivelihoodStatus;
import app.model.WdcpmksyProjectAssetProductionStatus;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyProjectPhyAssetAchievement;
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.model.project.WdcpmksyProjectPhysicalAchievementTranx;

@Service("PhysicalAchievementService")
public interface PhysicalAchievementService {
	
	List<PhysicalAchievementBean> getActivityWithTarget(Integer pCode,Integer finYr,Integer monthId);
	List<String> getCurrentFinYrMonthId(Integer pCode,String finYr);
	String getFinancialYearForPlan(Integer pCode);
	List<IwmpProjectPhysicalAsset> getAssetofActivity(Integer pCode,Integer activityCode,Integer finYr, String enddt);

	String saveAssetAchievement(List<String>assetAcheivement,Integer finYr,Integer monthId, String userid);
 

	LinkedHashMap<Integer,String> getUserToForward(Integer regId,String userType,Character action,Integer achid);
	String checkDistrictApprovalReuiredOrNot(Integer regId);
	String saveAchievementAsDraft(Integer pCode,String activityCode,String ach,Integer finYr,Integer monthId,Integer sentFrom,Integer sentTo,String createdBy, boolean blsconf);
	LinkedHashMap<String,List<PhysicalAchievementBean>> getAchievementData(Integer regId);
	List<PhysicalAchievementBean> checkForAlreadyForwardedAchievement(Integer achId,Integer regId);
	String forwardAchievementByWCDC(Integer sentTo,Integer sentFrom,Integer achid,Character action,String remarks,String userType);
	List<PhysicalAchievementBean> viewMovement(Integer regId);
	List<WdcpmksyProjectPhysicalAchievementDetails> getAchievementDetails(Integer achievementid);
	List<PhysicalAchievementBean> getAchievementMovementDetails(Integer achievementid);
	String getMinumFinYear(Integer pCode);
	List<PhysicalAchievementBean> viewAchievement(Integer regId);
	List<PhysicalAchievementBean> viewCompletedMovement(Integer regId);
	
	
	List<IwmpProjectAssetStatus> getProjectPhysicalAssetStatus(BigInteger assetid);
	List<WdcpmksyProjectPhyAssetAchievement> getProjectPhysicalAssetAchievement(BigInteger assetid);
	List<WdcpmksyProjectAssetLivelihoodStatus> getProjectLivelihoodAssetStatus(int assetid);
	List<WdcpmksyProjectAssetProductionStatus> getProjectProductionAssetStatus(int assetid);
	List<WdcpmksyProjectAssetEPAStatus> getProjectEPAAssetStatus(int assetid);
}
