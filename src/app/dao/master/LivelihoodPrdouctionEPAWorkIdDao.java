package app.dao.master;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.bean.AssetIdBean;
import app.bean.reports.LivelihoodPrdouctionEPAWorkIdBean;
import app.model.outcome.EpaDetail;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.ProductionDetail;
import app.model.project.WdcpmksyEpaWorkid;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyPrdouctionWorkid;

public interface LivelihoodPrdouctionEPAWorkIdDao {
	
	List<EpaDetail> getEpaDetails(Integer projId, String scheme, Integer activity);
	List<LivelihoodDetail> getLivelihoodDetails(Integer projId, String scheme, Integer activity);
	List<ProductionDetail> getProductionDetails(Integer projId, String scheme, Integer activity);
	
	List<WdcpmksyEpaWorkid> getEPAbyProjSchemeDraft(Integer projId,String finYr,Integer regId);
	List<WdcpmksyLivelihoodWorkid> getLivelihoodbyProjSchemeDraft(Integer projId, String finYr, Integer regId);
	List<WdcpmksyPrdouctionWorkid> getProductionbyProjSchemeDraft(Integer projId, String finYr, Integer regId);
	
	List<AssetIdBean> getAssetIdForEPACreation(Integer activityCode);
	List<AssetIdBean> getAssetIdForLivelihoodCreation(Integer activityCode);
	List<AssetIdBean> getAssetIdForProductionCreation(Integer activityCode);
	
	String saveEPALivelihoodProducttionAssetAsDraft(String finyr, Integer projcd, Integer[] aapid, Integer[] activity, Integer[] vcode, Integer loginid, String[] nearby, String[] areacov);
	String deleteAssetEPALivelihoodProducttion(Integer tempassetid, String finyr, Integer projcd);
	String completeAssetEPALivelihoodProducttion(List<BigInteger> tempassetid, String finyr, Integer projcd);
	
	List<LivelihoodPrdouctionEPAWorkIdBean> getLivelihoodPrdouctionEPAWorkIdList(Integer State, Integer district, Integer project, String scheme);
	String forwardEPALivelihoodProducttionWCDC(List<BigInteger> tempassetid, Integer sentfrom, Integer sentto, HttpSession session, String scheme, Integer projcd );
	
	List<WdcpmksyEpaWorkid> getListOfAssetUserWiseEpa(Integer regId,String userType);
	List<WdcpmksyEpaWorkid> getPendngAssetAtWCDCProjectWiseEpa(Integer regId,String userType,Integer projId);
	
	List<WdcpmksyLivelihoodWorkid> getListOfAssetUserWiseLivelihood(Integer regId,String userType);
	List<WdcpmksyLivelihoodWorkid> getPendngAssetAtWCDCProjectWiseLivelihood(Integer regId,String userType,Integer projId);
	
	List<WdcpmksyPrdouctionWorkid> getListOfAssetUserWiseProduction(Integer regId,String userType);
	List<WdcpmksyPrdouctionWorkid> getPendngAssetAtWCDCProjectWiseProduction(Integer regId,String userType,Integer projId);
	
	String completeAsset(Integer sentfrom,List<BigInteger> tempassetid, String scheme);
	String rejectAssetbyWCDC(Integer sentfrom,List<BigInteger> assetid,List<String> remarks, String scheme);
	List<WdcpmksyEpaWorkid> getCompletedAssetListEPA(String userId, Integer projId, Integer regid);
	List<WdcpmksyLivelihoodWorkid> getCompletedAssetListLivelihood(String userId, Integer projId, Integer regid);
	List<WdcpmksyPrdouctionWorkid> getCompletedAssetListProduction(String userId, Integer projId, Integer regid);
	
	List<WdcpmksyEpaWorkid> getviewforwardedAssetEPA(Integer regId, String userType, String userid, Integer projcd);
	List<WdcpmksyLivelihoodWorkid> getviewforwardedAssetLivelihood(Integer regId, String userType, String userid, Integer projcd);
	List<WdcpmksyPrdouctionWorkid> getviewforwardedAssetProduction(Integer regId, String userType, String userid, Integer projcd);
	LinkedHashMap<Integer,String> getUserToForward(Integer regId);
	List<WdcpmksyEpaWorkid> getEPAWorkId(int epaDetailId, int projId);
	List<WdcpmksyLivelihoodWorkid> getLivelihoodWorkid(int livelihoodCoreactivityId, int projId);
	List<WdcpmksyPrdouctionWorkid> getProductionWorkId(int productivityCoreactivityId, int projId);
	LinkedHashMap<Integer, String> getActivityEPALivelihoodProduction(Integer projId, String headcd);
	
	
	
}
