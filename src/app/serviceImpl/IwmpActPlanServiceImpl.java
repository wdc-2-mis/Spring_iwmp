package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.RevisedActPlanBean;
import app.dao.IwmpActPlanDao;
import app.model.IwmpActPlan;
import app.service.IwmpActPlanService;

@Service("IwmpActPlanService")
public class IwmpActPlanServiceImpl implements IwmpActPlanService{

	@Autowired(required = true)
	IwmpActPlanDao iwmpActPlanDao;
	
//	public void addActPlan(List<IwmpActPlan> iwmpActPlan) {
//		// TODO Auto-generated method stub
//		iwmpActPlanDao.addActPlan(iwmpActPlan);
//	}

	
	public String saveActPlan(Integer dcode, Integer projid, Integer finyear, Character dprstatus, String authname,
			Integer sentFrom) {
		return iwmpActPlanDao.saveActPlan(dcode, projid, finyear, dprstatus, authname, sentFrom);
	}

	@Override
	public List<RevisedActPlanBean> getAlreadyDistActPlanData(Integer dCode) {
		
		return iwmpActPlanDao.getAlreadyDistActPlanData(dCode);
	}

	@Override
	public String getFinalStatus(Integer actPlanId) {
		// TODO Auto-generated method stub
		return iwmpActPlanDao.getFinalStatus(actPlanId);
	}

	@Override
	public String deleteActPlan(Integer actPlanId) {
		// TODO Auto-generated method stub
		return iwmpActPlanDao.deleteActPlan(actPlanId);
	}

}
