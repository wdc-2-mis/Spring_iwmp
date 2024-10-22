package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.BaseLineSurveyBean;
import app.model.BaseLineSurveyActivityDetails;
import app.model.BaseLineSurveyMain;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.master.WdcpmksyMOutcome;
import app.model.master.WdcpmksyMOutcomeDetail;

public interface BaseLineSurveyService {
	
	List<IwmpMPhyHeads> getPhysicalHead();
	List<IwmpMPhyActivity> getPhysicalActivity();
	List<WdcpmksyMOutcome> getOutcomeHead();
	List<WdcpmksyMOutcomeDetail> getOutcomeActivity();
	String saveDataAsDraft(BaseLineSurveyMain main,List<BaseLineSurveyActivityDetails> finalList);
	String getSanctionedArea(Integer projId);
	List<BaseLineSurveyBean> getPreFilledData(Integer projId);
	String completeBaseLineSurvey(Integer projId);
	LinkedHashMap<Integer,String> getProjectforSLNA(Integer regId);
	List<BaseLineSurveyBean> getBaseLineSurveyDetailByDistCode(Integer stCode);
	LinkedHashMap<String,BaseLineSurveyBean> getBaseLineSurveyDetail();
	List<BaseLineSurveyBean> getBaseLineSurveyDetailByProjId(Integer projId);
	LinkedHashMap<String,BaseLineSurveyBean> getBaseLineSurveyDetailByStCodeDistCode(Integer stCode,Integer distCode);

}
