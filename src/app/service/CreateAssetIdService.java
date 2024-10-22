package app.service;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import app.bean.AssetIdBean;
import app.bean.DolrSupportBean;
import app.bean.PhysicalActionPlanBean;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpMProject;
import app.model.master.IwmpBlock;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.IwmpProjectPhysicalAssetTemp;
import app.model.project.WdcpmksyEpaWorkid;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyPrdouctionWorkid;


public interface CreateAssetIdService {
	List<IwmpProjectPhysicalAap> getActionPlan(Integer projId,Integer finYr);
	List<IwmpProjectPhysicalAssetTemp> getAssetTemp(Integer projId,Integer finYr, int aapid);
	List<IwmpProjectPhysicalAsset> getAssetAsset(Integer projId,Integer finYr, int aapid);
	
	
	
	List<AssetIdBean> getAssetIdForCreation(Integer activityCode);
	List<IwmpMPhySubactivity> getSubactivityByActivityCode(Integer activityCode);
	List<IwmpBlock> getBlockFromProjectLocation(Integer projId);
	LinkedHashMap<Integer,String> getVillageFromProjectLocationBlockWise(Integer projId,Integer bCode);
	String saveAssetAsDraft(Integer finyr, Integer projcd,Integer[] aapid, Integer[] activity,Integer[] vcode,Integer sentFrom,Integer[] subactivity, String[] landid);
	List<IwmpProjectPhysicalAssetTemp> getDraftPlanDetailsByProjYr(Integer projId,Integer finYr,Integer regId);
	String deleteAsset(Integer tempassetid);
	String forwardAssetToWCDC(List<BigInteger> tempassetid,Integer sentfrom,Integer sentto,String createdby);
	LinkedHashMap<Integer,String> getUserToForward(Integer regId);
	List<IwmpProjectPhysicalAssetTemp> getforwardedAssetUserWise(Integer regId,String userType,String userid);
	List<IwmpProjectPhysicalAssetTemp> getListOfAssetUserWise(Integer regId,String userType);
	List<IwmpProjectPhysicalAssetTemp> getPendngAssetAtWCDCProjectWise(Integer regId,String userType,Integer projId,Integer dCode);
	String forwardAssettoSLNA(Integer sentfrom,List<BigInteger> assetid,Integer sentto);
	Integer getSentToSLNAForAsset(Integer sentFrom);
	String rejectAssetbyWCDC(Integer sentfrom,List<BigInteger> assetid,List<String> remarks);
	String completeAsset(Integer sentfrom,List<BigInteger> tempassetid,Integer sentto);
	String rejectAssetbySLNA(Integer sentfrom,List<BigInteger> assetid,List<String> remarks);
	List<IwmpProjectPhysicalAsset> getassetList(String userId);
	List<IwmpProjectPhysicalAssetTemp> getRejectedAssetList(String userId);
	List<IwmpProjectPhysicalAsset> getfinalassetList(Integer dCode, Integer pCode, String userId);
	LinkedHashMap<Integer,String> getListOfProjects(Integer regId);
	List<IwmpProjectPhysicalAsset> getfinalassetdiList(Integer pCode, String userId);
	List<IwmpMProject> getProjectForPendingAsset(Integer regId);
	List<IwmpMProject> getProjectForForwardedAsset(Integer regId,String userType,String userId);
	List<IwmpProjectPhysicalAssetTemp> getProjectFromDistrictForPendingAsset(Integer dCode,Integer regId);
	List<IwmpProjectPhysicalAssetTemp> getforwardedAssetUserWiseForProject(Integer regId,String userType,Integer projId,Integer dCode);
	
	String saveAssetStatus(HttpServletRequest request, String[] assetid, String projid, String finYear, String finalAssetid);
	String updateAssetStatus(HttpServletRequest request, String[] assetid,  String[] statusid);
	List<IwmpProjectPhysicalAsset> getassetListkd(Integer projId);
	List<IwmpProjectPhysicalAsset> getassetWorkWiseList(Integer pCode, String userId, Integer fYear);
	List<WdcpmksyLivelihoodWorkid> getassetHeadWiseList(Integer pCode, String userId, String hCode, Integer hActCode);
	String saveAssetLivelihoodStatus(HttpServletRequest request, String[] assetid, String projid, String head, String finalAssetid);
	List<WdcpmksyPrdouctionWorkid> getassetProdHeadWiseList(Integer pCode, String userId, Integer hActCode);
	List<WdcpmksyEpaWorkid> getassetepaHeadWiseList(Integer pCode, String userId, Integer hActCode);
	String saveAssetProductionStatus(HttpServletRequest request, String[] assetid, String projid, String head, String finalAssetid);
	String saveAssetEPAStatus(HttpServletRequest request, String[] assetid, String projid, String head, String finalAssetid);
	List<AssetIdBean> getassetheadcompletiondata(Integer pCode, String hCode, Integer headactivity);
	List<AssetIdBean> getassetheadforcloseddata(Integer pCode, String hCode);
	List<IwmpMFinYear> getAllFinancialYearDetails();
	List<IwmpMMonth> getAllMonthDetailData();
	List<AssetIdBean> getassetcompletiondata(Integer pCode, Integer fCode);
	List<AssetIdBean> getassetforcloseddata(Integer pCode, Integer fCode);
	LinkedHashMap<Integer, String> getHeadActivitydesc(String headtype);
}
