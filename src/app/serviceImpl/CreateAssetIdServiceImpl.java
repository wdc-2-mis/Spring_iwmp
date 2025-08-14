package app.serviceImpl;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.AssetIdBean;
import app.dao.CreateAssetIdDao;
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
import app.service.CreateAssetIdService;
import app.service.ProjectMasterService;

@Service("CreateAssetIdService")
public class CreateAssetIdServiceImpl implements CreateAssetIdService{
	
	@Autowired(required = true)
	CreateAssetIdDao createAssetIdDao;

	@Override
	public List<IwmpProjectPhysicalAap> getActionPlan(Integer projId, Integer finYr) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getActionPlan(projId, finYr);
	}

	@Override
	public List<AssetIdBean> getAssetIdForCreation(Integer activityCode) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getAssetIdForCreation(activityCode);
	}

	@Override
	public List<IwmpBlock> getBlockFromProjectLocation(Integer projId) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getBlockFromProjectLocation(projId);
	}

	@Override
	public LinkedHashMap<Integer, String> getVillageFromProjectLocationBlockWise(Integer projId, Integer bCode) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getVillageFromProjectLocationBlockWise(projId,bCode);
	}

	@Override
	public String saveAssetAsDraft(Integer finyr, Integer projcd, Integer[] aapid, Integer[] activity,
			Integer[] vcode,Integer sentFrom,Integer[] subactivity, String[] landid) {
		// TODO Auto-generated method stub
		return createAssetIdDao.saveAssetAsDraft(finyr,projcd,aapid,activity,vcode,sentFrom,subactivity, landid);
	}
	
	@Override
	public List<IwmpProjectPhysicalAssetTemp> getDraftPlanDetailsByProjYr(Integer projId, Integer finYr,Integer regId) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getDraftPlanDetailsByProjYr(projId, finYr,regId);
	}

	@Override
	public String deleteAsset(Integer tempassetid) {
		// TODO Auto-generated method stub
		return createAssetIdDao.deleteAsset(tempassetid);
	}
	
	@Override
	public String forwardAssetToWCDC(List<BigInteger> tempassetid,Integer sentfrom,Integer sentto,String createdby) {
		// TODO Auto-generated method stub
		return createAssetIdDao.forwardAssetToWCDC(tempassetid,sentfrom, sentto,createdby);
	}


	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getUserToForward(regId);
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getassetList(String userId) {
		return createAssetIdDao.getassetList(userId);
	}
	
	@Override
	public List<IwmpProjectPhysicalAssetTemp> getforwardedAssetUserWise(Integer regId,String userType,String userid) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getforwardedAssetUserWise(regId,userType,userid);
	}

	@Override
	public List<IwmpProjectPhysicalAssetTemp> getListOfAssetUserWise(Integer regId,String userType) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getListOfAssetUserWise(regId,userType);
	}
	
	@Override
	public List<IwmpProjectPhysicalAssetTemp> getPendngAssetAtWCDCProjectWise(Integer regId,String userType,Integer projId,Integer dCode) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getPendngAssetAtWCDCProjectWise(regId,userType,projId,dCode);
	}

	@Override
	public String forwardAssettoSLNA(Integer sentfrom, List<BigInteger> assetid,Integer sentto) {
		// TODO Auto-generated method stub
		return createAssetIdDao.forwardAssettoSLNA(sentfrom,assetid,sentto);
	}

	@Override
	public Integer getSentToSLNAForAsset(Integer sentFrom) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getSentToSLNAForAsset(sentFrom);
	}

	@Override
	public String rejectAssetbyWCDC(Integer sentfrom, List<BigInteger> assetid,List<String> remarks) {
		// TODO Auto-generated method stub
		return createAssetIdDao.rejectAssetbyWCDC(sentfrom,assetid,remarks);
	}

	@Override
	public String completeAsset(Integer sentfrom, List<BigInteger> tempassetid, Integer sentto) {
		// TODO Auto-generated method stub
		return createAssetIdDao.completeAsset(sentfrom,tempassetid,sentto);
	}
	
	@Override
	public String rejectAssetbySLNA(Integer sentfrom, List<BigInteger> assetid,List<String> remarks) {
		// TODO Auto-generated method stub
		return createAssetIdDao.rejectAssetbySLNA(sentfrom,assetid,remarks);
	}

	@Override
	public List<IwmpProjectPhysicalAssetTemp> getRejectedAssetList(String userId) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getRejectedAssetList(userId);
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getfinalassetList(Integer dCode, Integer pCode, String userId) {
		return createAssetIdDao.getfinalassetList(dCode, pCode, userId);
	}

	@Override
	public LinkedHashMap<Integer, String> getListOfProjects(Integer regId) {
		
		return createAssetIdDao.getListOfProjects(regId);
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getfinalassetdiList(Integer pCode, String userId) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getfinalassetdiList(pCode, userId);
	}
	
	@Override
	public List<IwmpMProject> getProjectForPendingAsset(Integer regId) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getProjectForPendingAsset(regId);
	}

	@Override
	public List<IwmpProjectPhysicalAssetTemp> getProjectFromDistrictForPendingAsset(Integer dCode,Integer regId) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getProjectFromDistrictForPendingAsset(dCode,regId);
	}

	@Override
	public List<IwmpMProject> getProjectForForwardedAsset(Integer regId,String userType,String userId) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getProjectForForwardedAsset(regId,userType,userId);
	}

	@Override
	public List<IwmpProjectPhysicalAssetTemp> getforwardedAssetUserWiseForProject(Integer regId, String userType,
			Integer projId,Integer dCode) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getforwardedAssetUserWiseForProject(regId,userType,projId,dCode);
	}

	@Override
	public String saveAssetStatus(HttpServletRequest request, String[] assetid,  String projid, String finYear, String finalAssetid) {
		
		return createAssetIdDao.saveAssetStatus(request,  assetid,  projid, finYear, finalAssetid);
	}

	@Override
	public String updateAssetStatus(HttpServletRequest request, String[] assetid,  String[] statusid) {
		return createAssetIdDao.updateAssetStatus(request, assetid,  statusid);
	}

	@Override
	public List<IwmpMPhySubactivity> getSubactivityByActivityCode(Integer activityCode) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getSubactivityByActivityCode(activityCode);
	}

	@Override
	public List<IwmpProjectPhysicalAssetTemp> getAssetTemp(Integer projId, Integer finYr, int aapid) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getAssetTemp(projId, finYr, aapid);
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getAssetAsset(Integer projId, Integer finYr, int aapid) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getAssetAsset(projId, finYr, aapid);
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getassetListkd(Integer projId) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getassetListkd( projId);
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getassetWorkWiseList(Integer pCode, String userId, Integer fYear) {
		
		return createAssetIdDao.getassetWorkWiseList(pCode, userId, fYear);
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getassetHeadWiseList(Integer pCode, String userId, String hCode, Integer hActCode) {
		return createAssetIdDao.getassetHeadWiseList(pCode, userId, hCode,hActCode);
	}

	@Override
	public String saveAssetLivelihoodStatus(HttpServletRequest request, String[] assetid, String projid, String head,
			String finalAssetid) {
		// TODO Auto-generated method stub
		return createAssetIdDao.saveAssetLivelihoodStatus(request, assetid, projid, head, finalAssetid);
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getassetProdHeadWiseList(Integer pCode, String userId, Integer hActCode) {
		return createAssetIdDao.getassetProdHeadWiseList(pCode, userId, hActCode);
	}

	@Override
	public List<WdcpmksyEpaWorkid> getassetepaHeadWiseList(Integer pCode, String userId, Integer hActCode) {
		return createAssetIdDao.getassetepaHeadWiseList(pCode, userId, hActCode);
	}

	@Override
	public String saveAssetProductionStatus(HttpServletRequest request, String[] assetid, String projid, String head, String finalAssetid) {
		return createAssetIdDao.saveAssetProductionStatus(request, assetid, projid, head, finalAssetid);
	}

	@Override
	public String saveAssetEPAStatus(HttpServletRequest request, String[] assetid, String projid, String head, String finalAssetid) {
		return createAssetIdDao.saveAssetEPAStatus(request, assetid, projid, head, finalAssetid);
	}

	@Override
	public List<AssetIdBean> getassetheadcompletiondata(Integer pCode, String hCode, Integer headactivity) {
		return createAssetIdDao.getassetheadcompletiondata(pCode, hCode, headactivity);
	}

	@Override
	public List<AssetIdBean> getassetheadforcloseddata(Integer pCode, String hCode) {
		return createAssetIdDao.getassetheadforcloseddata(pCode, hCode);
	}

	@Override
	public List<IwmpMFinYear> getAllFinancialYearDetails() {
		// TODO Auto-generated method stub
		return createAssetIdDao.getAllFinancialYearDetails();
	}

	@Override
	public List<IwmpMMonth> getAllMonthDetailData() {
		// TODO Auto-generated method stub
		return createAssetIdDao.getAllMonthDetailData();
	}

	@Override
	public List<AssetIdBean> getassetcompletiondata(Integer pCode, Integer fCode) {
		return createAssetIdDao.getassetcompletiondata(pCode, fCode);
	}

	@Override
	public List<AssetIdBean> getassetforcloseddata(Integer pCode, Integer fCode) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getassetforcloseddata(pCode, fCode);
	}

	@Override
	public LinkedHashMap<Integer, String> getHeadActivitydesc(String headtype) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getHeadActivitydesc(headtype);
	}

	@Override
	public List<AssetIdBean> getListofWorkWiseStatus(Integer projid, Integer fyear, String hactivity, String wstatus) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getListofWorkWiseStatus(projid, fyear, hactivity, wstatus);
	}

	@Override
	public List<AssetIdBean> getWorkWiseStatus(Integer workid, String activityid, Integer stcd) {
		// TODO Auto-generated method stub
		return createAssetIdDao.getWorkWiseStatus(workid, activityid, stcd);
	}
}
