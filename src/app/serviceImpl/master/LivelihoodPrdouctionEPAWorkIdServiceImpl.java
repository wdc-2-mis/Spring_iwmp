package app.serviceImpl.master;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.AssetIdBean;
import app.bean.reports.LivelihoodPrdouctionEPAWorkIdBean;
import app.dao.master.LivelihoodPrdouctionEPAWorkIdDao;
import app.model.outcome.EpaDetail;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.ProductionDetail;
import app.model.project.WdcpmksyEpaWorkid;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyPrdouctionWorkid;
import app.service.master.LivelihoodPrdouctionEPAWorkIdService;

@Service("LivelihoodPrdouctionEPAWorkIdService")
public class LivelihoodPrdouctionEPAWorkIdServiceImpl implements LivelihoodPrdouctionEPAWorkIdService{
	
	@Autowired(required = true)
	LivelihoodPrdouctionEPAWorkIdDao dao;

	@Override
	public List<EpaDetail> getEpaDetails(Integer projId, String scheme, Integer activity) {
		
		return dao.getEpaDetails(projId, scheme, activity);
	}

	@Override
	public List<LivelihoodDetail> getLivelihoodDetails(Integer projId, String scheme, Integer activity) {
		
		return dao.getLivelihoodDetails(projId, scheme, activity);
	}

	@Override
	public List<ProductionDetail> getProductionDetails(Integer projId, String scheme, Integer activity) {
		
		return dao.getProductionDetails(projId, scheme, activity);
	}

	@Override
	public List<WdcpmksyEpaWorkid> getEPAbyProjSchemeDraft(Integer projId, String finYr,
			Integer regId) {
		
		return dao.getEPAbyProjSchemeDraft( projId, finYr, regId);
	}
	
	@Override
	public List<WdcpmksyLivelihoodWorkid> getLivelihoodbyProjSchemeDraft(Integer projId, String finYr, Integer regId) {
		
		return dao.getLivelihoodbyProjSchemeDraft(projId, finYr, regId);
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getProductionbyProjSchemeDraft(Integer projId, String finYr, Integer regId) {
		
		return dao.getProductionbyProjSchemeDraft( projId, finYr, regId);
	}

	@Override
	public List<AssetIdBean> getAssetIdForEPACreation(Integer activityCode) {
		
		return dao.getAssetIdForEPACreation(activityCode);
	}

	@Override
	public List<AssetIdBean> getAssetIdForLivelihoodCreation(Integer activityCode) {
		
		return dao.getAssetIdForLivelihoodCreation( activityCode);
	}

	@Override
	public List<AssetIdBean> getAssetIdForProductionCreation(Integer activityCode) {
		
		return dao.getAssetIdForProductionCreation(activityCode);
	}

	@Override
	public String saveEPALivelihoodProducttionAssetAsDraft(String finyr, Integer projcd, Integer[] aapid,
			Integer[] activity, Integer[] vcode, Integer loginid, String[] nearby, String[] areacov) {
		
		return dao.saveEPALivelihoodProducttionAssetAsDraft(finyr, projcd, aapid, activity, vcode, loginid, nearby, areacov);
	}

	@Override
	public String deleteAssetEPALivelihoodProducttion(Integer tempassetid, String finyr, Integer projcd) {
		
		return dao.deleteAssetEPALivelihoodProducttion(tempassetid, finyr, projcd);
	}

	@Override
	public String completeAssetEPALivelihoodProducttion(List<BigInteger> tempassetid, String finyr, Integer projcd) {
		// TODO Auto-generated method stub
		return dao.completeAssetEPALivelihoodProducttion(tempassetid, finyr, projcd);
	}

	@Override
	public List<LivelihoodPrdouctionEPAWorkIdBean> getLivelihoodPrdouctionEPAWorkIdList(Integer State, Integer district,
			Integer project, String scheme) {
		// TODO Auto-generated method stub
		return dao.getLivelihoodPrdouctionEPAWorkIdList(State, district, project, scheme);
	}

	@Override
	public String forwardEPALivelihoodProducttionWCDC(List<BigInteger> tempassetid, Integer sentfrom, Integer sentto,
			HttpSession session, String scheme, Integer projcd) {
		// TODO Auto-generated method stub
		return dao.forwardEPALivelihoodProducttionWCDC(tempassetid, sentfrom, sentto, session, scheme, projcd);
	}

	@Override
	public List<WdcpmksyEpaWorkid> getListOfAssetUserWiseEpa(Integer regId, String userType) {
		// TODO Auto-generated method stub
		return dao.getListOfAssetUserWiseEpa(regId, userType);
	}

	@Override
	public List<WdcpmksyEpaWorkid> getPendngAssetAtWCDCProjectWiseEpa(Integer regId, String userType, Integer projId) {
		// TODO Auto-generated method stub
		return dao.getPendngAssetAtWCDCProjectWiseEpa(regId, userType, projId);
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getListOfAssetUserWiseLivelihood(Integer regId, String userType) {
		// TODO Auto-generated method stub
		return dao.getListOfAssetUserWiseLivelihood(regId, userType);
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getPendngAssetAtWCDCProjectWiseLivelihood(Integer regId, String userType,
			Integer projId) {
		// TODO Auto-generated method stub
		return dao.getPendngAssetAtWCDCProjectWiseLivelihood(regId, userType, projId);
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getListOfAssetUserWiseProduction(Integer regId, String userType) {
		// TODO Auto-generated method stub
		return dao.getListOfAssetUserWiseProduction(regId, userType);
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getPendngAssetAtWCDCProjectWiseProduction(Integer regId, String userType,
			Integer projId) {
		// TODO Auto-generated method stub
		return dao.getPendngAssetAtWCDCProjectWiseProduction(regId, userType, projId);
	}

	@Override
	public String completeAsset(Integer sentfrom, List<BigInteger> tempassetid, String scheme) {
		// TODO Auto-generated method stub
		return dao.completeAsset(sentfrom, tempassetid, scheme);
	}

	@Override
	public String rejectAssetbyWCDC(Integer sentfrom, List<BigInteger> assetid, List<String> remarks, String scheme) {
		// TODO Auto-generated method stub
		return dao.rejectAssetbyWCDC(sentfrom, assetid, remarks, scheme);
	}

	@Override
	public List<WdcpmksyEpaWorkid> getCompletedAssetListEPA(String userId, Integer projId, Integer regid) {
		// TODO Auto-generated method stub
		return dao.getCompletedAssetListEPA(userId, projId, regid);
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getCompletedAssetListLivelihood(String userId, Integer projId, Integer regid) {
		// TODO Auto-generated method stub
		return dao.getCompletedAssetListLivelihood(userId, projId, regid);
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getCompletedAssetListProduction(String userId, Integer projId, Integer regid) {
		// TODO Auto-generated method stub
		return dao.getCompletedAssetListProduction(userId, projId, regid);
	}

	@Override
	public List<WdcpmksyEpaWorkid> getviewforwardedAssetEPA(Integer regId, String userType, String userid,
			Integer projcd) {
		// TODO Auto-generated method stub
		return dao.getviewforwardedAssetEPA(regId, userType, userid, projcd);
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getviewforwardedAssetLivelihood(Integer regId, String userType, String userid,
			Integer projcd) {
		// TODO Auto-generated method stub
		return dao.getviewforwardedAssetLivelihood(regId, userType, userid, projcd);
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getviewforwardedAssetProduction(Integer regId, String userType, String userid,
			Integer projcd) {
		// TODO Auto-generated method stub
		return dao.getviewforwardedAssetProduction(regId, userType, userid, projcd);
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId) {
		// TODO Auto-generated method stub
		return dao.getUserToForward(regId);
	}

	@Override
	public List<WdcpmksyEpaWorkid> getEPAWorkId(int epaActivityId, int projId) {
		
		return dao.getEPAWorkId(epaActivityId, projId);
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getLivelihoodWorkid(int livelihoodCoreactivityId, int projId) {
		// TODO Auto-generated method stub
		return dao.getLivelihoodWorkid(livelihoodCoreactivityId, projId);
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getProductionWorkId(int productivityCoreactivityId, int projId) {
		// TODO Auto-generated method stub
		return dao.getProductionWorkId(productivityCoreactivityId, projId);
	}

	@Override
	public LinkedHashMap<Integer, String> getActivityEPALivelihoodProduction(Integer projId, String headcd) {
		// TODO Auto-generated method stub
		return dao.getActivityEPALivelihoodProduction(projId, headcd);
	}

	
	
	
	
	
	
	
	
	
}
