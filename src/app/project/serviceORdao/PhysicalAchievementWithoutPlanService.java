package app.project.serviceORdao;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.PhysicalAchievementBean;

@Service("PhysicalAchievementWithoutPlanService")
public interface PhysicalAchievementWithoutPlanService {
	
	String getFinancialYearForAchievement(Integer pCode);
	
	List<String> getCurrentFinYrMonthIdkdy(Integer pCode,String finYr);
	
	List<PhysicalAchievementBean> getActivityWithTargetkdy(Integer pCode, Integer finYr, Integer monthId);

}
