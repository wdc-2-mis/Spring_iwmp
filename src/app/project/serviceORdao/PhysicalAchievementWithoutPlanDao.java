package app.project.serviceORdao;

import java.util.List;

import app.bean.PhysicalAchievementBean;

public interface PhysicalAchievementWithoutPlanDao {
	
	String getFinancialYearForAchievement(Integer pCode);
	
	List<String> getCurrentFinYrMonthIdkdy(Integer pCode,String finYr);
	
	List<PhysicalAchievementBean> getActivityWithTargetkdy(Integer pCode, Integer finYr, Integer monthId);

}
