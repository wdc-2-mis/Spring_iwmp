package app.project.serviceORdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.PhysicalAchievementBean;

@Service("PhysicalAchievementWithoutPlanService")
public class PhysicalAchievementWithoutPlanServiceImpl implements PhysicalAchievementWithoutPlanService{

	@Autowired
	PhysicalAchievementWithoutPlanDao dao;
	
	
	
	@Override
	public String getFinancialYearForAchievement(Integer pCode) {
		// TODO Auto-generated method stub
		return dao.getFinancialYearForAchievement(pCode);
	}



	@Override
	public List<String> getCurrentFinYrMonthIdkdy(Integer pCode, String finYr) {
		// TODO Auto-generated method stub
		return dao.getCurrentFinYrMonthIdkdy(pCode, finYr);
	}



	@Override
	public List<PhysicalAchievementBean> getActivityWithTargetkdy(Integer pCode, Integer finYr, Integer monthId) {
		// TODO Auto-generated method stub
		return dao.getActivityWithTargetkdy(pCode, finYr, monthId);
	}

}
