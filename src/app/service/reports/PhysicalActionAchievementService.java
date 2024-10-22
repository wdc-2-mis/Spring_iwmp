package app.service.reports;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.reports.ActivityWiseUptoPlanAchieveWorkBean;
import app.bean.reports.PhysicalActionAchievementBean;

@Service("PhysicalActionAchievementService")
public interface PhysicalActionAchievementService {
	
	LinkedHashMap<Integer, String> getYearForPhysicalActionAchievementReport(Integer pCode);
	List<PhysicalActionAchievementBean> getPhysicalActionAchievementReport(Integer stCode, Integer distCode, Integer projId,Integer fromYear);
	List<ActivityWiseUptoPlanAchieveWorkBean> getActivityWiseUptoPlanAchievWorkReport(Integer stCode, Integer distCode, Integer projId,Integer fromYear);
	LinkedHashMap<Integer, String> getYearForAchDashboard();
}
