package app.dao.outcomes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;
import app.model.BlsOutDetailCrop;
import app.model.BlsOutDetailCropAchiev;
import app.model.BlsOutMain;
import app.model.MBlsOutcome;

public interface BaseLineOutcomeDao {
	LinkedHashMap<Integer,String> getProjectByRegId(Integer regId);
	List<MBlsOutcome> getOutComeMaster();
	String saveBLSasDraft(Integer projId,Integer vcode,String plotNo,BigDecimal projectArea,Integer irrigationStatus,Integer ownership,String ownerName,Integer landClassification,String landSubClassification,String noOfCrop,List<Integer> season,List<Integer> cropType,List<BigDecimal> cropArea,List<BigDecimal> cropProduction,List<BigDecimal> avgIncome,String forestLandType,String userId);
	String updateBLS(Integer projId,Integer vcode,String plotNo,BigDecimal projectArea,Integer irrigationStatus,Integer ownership,String ownerName,Integer landClassification,String landSubClassification, Integer noOfCrop,List<Integer> season,List<Integer> cropType,List<BigDecimal> cropArea,List<BigDecimal> cropProduction,List<BigDecimal> avgIncome,Integer forestLandType,String userId,Integer blsoutDetailId, BigDecimal microIrrigation);
	List<NewBaseLineSurveyBean> getBaselineNewDraft(Integer projectId);

	LinkedHashMap<Integer, List<NewBaseLineSurveyBean>> getbaselinefinaldata(Integer project);
	BlsOutDetailCropAchiev findbaselineedata(Integer id);
	BlsOutDetailCrop findbaselineDraftdata(Integer id);
	LinkedHashMap<Integer, String> getcroptypeCode();
	LinkedHashMap<String,String> getcroptypeCode(Integer projectId,Integer villageCode,String plotNo, Integer season);


	boolean deleteBaselineNewDraft(Integer main, Integer detail, String detailtrans);
	boolean completeBaselineNewDraft(Integer project, List<String> villcode);

	boolean updateBaselineCropAchiev(BlsOutDetailCropAchiev blsOutDetailCropAchiev);
	boolean deleteBaselineCropAchiev(Integer pkId);
	
	boolean deleteAllBaselineCropAchiev(Integer blsOutDetailId);

	HashMap<Integer,String> getVillageOfProject(Integer projId);
	List<NewBaseLineSurveyBean> getPlotDataOfAVillage(Integer vcode,String plotno, Integer projid);
	List<NewBaseLineSurveyBean> getPlotDataOfAVillageForUpdate(Integer vcode,String plotno);
	List<NewBaseLineSurveyBean> getCropDataOfPlotProject(Integer vcode,String plotno,Integer projId);
	
	LinkedHashMap<Integer, String> getProjectforBlsUpdateByRegId(Integer regId) ;
	
	LinkedHashMap<String,String> getPlotNoByProjectVillage(Integer projectId,Integer vcode);
	
	HashMap<Integer,String> getVillageOfProjectforBlsUpdate(Integer projId);
	BigDecimal gettotalarea(Integer projId);
	String updateDraftWiseCropData(BlsOutDetailCrop bod);
	List<NewBaseLineSurveyBean> getDraftCropDataOfPlotProject(Integer vcode,String plotno,Integer projId);
	HashMap<Integer, String> getVillageOfProjectMicro(Integer projId);
	String changeCropDeleteStatus(Integer rowId);
}
