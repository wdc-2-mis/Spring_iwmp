package app.serviceImpl.outcomes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;
import app.dao.outcomes.BaseLineOutcomeDao;
import app.model.BlsOutDetailCrop;
import app.model.BlsOutDetailCropAchiev;
import app.model.BlsOutMain;
import app.model.MBlsOutcome;
import app.service.outcome.BaseLineOutcomeService;

@Service("BaseLineOutcomeService")
public class BaseLineOutcomeServiceImpl implements BaseLineOutcomeService{
	
	@Autowired
	BaseLineOutcomeDao baseLineOutcomeDao;

	@Override
	public LinkedHashMap<Integer, String> getProjectByRegId(Integer regId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getProjectByRegId(regId);
	}

	@Override
	public List<MBlsOutcome> getOutComeMaster() {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getOutComeMaster();
	}

	@Override
	public String saveBLSasDraft(Integer projId, Integer vcode, String plotNo, BigDecimal projectArea,
			Integer irrigationStatus, Integer ownership, String ownerName, Integer landClassification,String landSubClassification, String noOfCrop,
			List<Integer> season, List<Integer> cropType, List<BigDecimal> cropArea, List<BigDecimal> cropProduction,
			List<BigDecimal> avgIncome, String forestLandType, String userId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.saveBLSasDraft(projId,vcode,plotNo,projectArea,irrigationStatus,ownership,ownerName,landClassification, landSubClassification, noOfCrop,season,cropType,cropArea,cropProduction,avgIncome,forestLandType,userId);
	}

	@Override
	public List<NewBaseLineSurveyBean> getBaselineNewDraft(Integer projectId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getBaselineNewDraft(projectId);
	}


	@Override
	public LinkedHashMap<Integer, List<NewBaseLineSurveyBean>> getbaselinefinaldata(Integer project) {
		return baseLineOutcomeDao.getbaselinefinaldata(project);
	}

	@Override
	public BlsOutDetailCropAchiev findbaselineedata(Integer id) {
		return baseLineOutcomeDao.findbaselineedata(id);
	}

	@Override
	public LinkedHashMap<Integer, String> getcroptypeCode() {
		return baseLineOutcomeDao.getcroptypeCode();
	}

	@Override
	public boolean deleteBaselineNewDraft(Integer main, Integer detail, String detailtrans) {
		// TODO Auto-generated method stub
		return  baseLineOutcomeDao.deleteBaselineNewDraft(main, detail, detailtrans) ;
	}

	@Override
	public boolean completeBaselineNewDraft(Integer project, List<String> villcode ) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.completeBaselineNewDraft(project, villcode);
	}


	@Override
	public boolean updateBaselineCropAchiev(BlsOutDetailCropAchiev blsOutDetailCropAchiev) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.updateBaselineCropAchiev(blsOutDetailCropAchiev);
	}




	@Override
	public HashMap<Integer, String> getVillageOfProject(Integer projId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getVillageOfProject(projId);
	}

	@Override
	public List<NewBaseLineSurveyBean> getPlotDataOfAVillage(Integer vcode, String plotno, Integer projid) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getPlotDataOfAVillage(vcode, plotno,projid);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectforBlsUpdateByRegId(Integer regId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getProjectforBlsUpdateByRegId(regId);
	}

	@Override
	public LinkedHashMap<String, String> getPlotNoByProjectVillage(Integer projectId,Integer villageCode) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getPlotNoByProjectVillage(projectId, villageCode);
	}

	@Override
	public HashMap<Integer, String> getVillageOfProjectforBlsUpdate(Integer projId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getVillageOfProjectforBlsUpdate(projId);
	}

	@Override
	public List<NewBaseLineSurveyBean> getCropDataOfPlotProject(Integer vcode, String plotno, Integer projId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getCropDataOfPlotProject(vcode,plotno,projId);
	}

	@Override
	public List<NewBaseLineSurveyBean> getPlotDataOfAVillageForUpdate(Integer vcode, String plotno) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getPlotDataOfAVillageForUpdate(vcode, plotno);
	}

	@Override
	public boolean deleteBaselineCropAchiev(Integer pkId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.deleteBaselineCropAchiev(pkId);
	}

	

	@Override
	public boolean deleteAllBaselineCropAchiev(Integer blsOutDetailId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.deleteAllBaselineCropAchiev(blsOutDetailId);
	}

	@Override
	public String updateBLS(Integer projId, Integer vcode, String plotNo, BigDecimal projectArea,
			Integer irrigationStatus, Integer ownership, String ownerName, Integer landClassification,String landSubClassification, Integer noOfCrop,
			List<Integer> season, List<Integer> cropType, List<BigDecimal> cropArea, List<BigDecimal> cropProduction,
			List<BigDecimal> avgIncome, Integer forestLandType, String userId, Integer blsoutDetailId, BigDecimal microIrrigation) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.updateBLS(projId,vcode,plotNo,projectArea,irrigationStatus,ownership,ownerName,landClassification,landSubClassification,noOfCrop,season,cropType,cropArea,cropProduction,avgIncome,forestLandType,userId,blsoutDetailId, microIrrigation);
	}

	@Override
	public LinkedHashMap<String, String> getcroptypeCode(Integer projectId, Integer villageCode,String plotNo,Integer season) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getcroptypeCode(projectId,villageCode,plotNo,season);
	}

	@Override
	public BigDecimal gettotalarea(Integer projId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.gettotalarea(projId);
	}

	@Override
	public BlsOutDetailCrop findbaselineDraftdata(Integer id) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.findbaselineDraftdata(id);
	}

	@Override
	public String updateDraftWiseCropData(BlsOutDetailCrop bod) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.updateDraftWiseCropData(bod);
	}

	@Override
	public List<NewBaseLineSurveyBean> getDraftCropDataOfPlotProject(Integer vcode, String plotno, Integer projId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getDraftCropDataOfPlotProject(vcode, plotno, projId);
	}

	@Override
	public HashMap<Integer, String> getVillageOfProjectMicro(Integer projId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.getVillageOfProjectMicro(projId);
	}

	@Override
	public String changeCropDeleteStatus(Integer rowId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeDao.changeCropDeleteStatus(rowId);
	}

	

	


}
