package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.BaseLineSurveyBean;
import app.dao.AuditDao;
import app.dao.BaseLineSurveyDao;
import app.model.BaseLineSurveyActivityDetails;
import app.model.BaseLineSurveyMain;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.master.WdcpmksyMOutcome;
import app.model.master.WdcpmksyMOutcomeDetail;
import app.service.BaseLineSurveyService;

@Service("baseLineSurveyService")
public class BaseLineSurveyServiceImpl implements BaseLineSurveyService{
	
	@Autowired
	BaseLineSurveyDao baseLineSurveyDao;

	
	 @Override 
	 public List<IwmpMPhyHeads> getPhysicalHead() { 
		 // TODO Auto-generated method stub 
		 return baseLineSurveyDao.getPhysicalHead();
	 }
	

	@Override
	public List<IwmpMPhyActivity> getPhysicalActivity() {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getPhysicalActivity();
	}

	@Override
	public List<WdcpmksyMOutcome> getOutcomeHead() {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getOutcomeHead();
	}

	@Override
	public List<WdcpmksyMOutcomeDetail> getOutcomeActivity() {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getOutcomeActivity();
	}


	@Override
	public String saveDataAsDraft(BaseLineSurveyMain main,List<BaseLineSurveyActivityDetails> finalList) {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.saveDataAsDraft(main,finalList);
	}


	@Override
	public String getSanctionedArea(Integer projId) {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getSanctionedArea(projId);
	}


	@Override
	public List<BaseLineSurveyBean> getPreFilledData(Integer projId) {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getPreFilledData(projId);
	}


	@Override
	public String completeBaseLineSurvey(Integer projId) {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.completeBaseLineSurvey(projId);
	}
	
	@Override
	public LinkedHashMap<Integer, String> getProjectforSLNA(Integer regId) {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getProjectforSLNA(regId);
	}


	@Override
	public List<BaseLineSurveyBean> getBaseLineSurveyDetailByDistCode(Integer stCode) {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getBaseLineSurveyDetailByDistCode(stCode);
	}


	@Override
	public List<BaseLineSurveyBean> getBaseLineSurveyDetailByProjId(Integer projId) {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getBaseLineSurveyDetailByProjId(projId);
	}


	@Override
	public LinkedHashMap<String,BaseLineSurveyBean> getBaseLineSurveyDetail() {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getBaseLineSurveyDetail();
	}


	@Override
	public LinkedHashMap<String, BaseLineSurveyBean> getBaseLineSurveyDetailByStCodeDistCode(Integer stCode,
			Integer distCode) {
		// TODO Auto-generated method stub
		return baseLineSurveyDao.getBaseLineSurveyDetailByStCodeDistCode(stCode, distCode);
	}

}
