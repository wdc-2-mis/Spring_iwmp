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
	List<ActivityWiseUptoPlanAchieveWorkBean> getActivityWiseUptoPlanAchievWorkReport(Integer stCode, Integer distCode, Integer projId,String fromYear, String sdate, String edate);
	LinkedHashMap<Integer, String> getYearForAchDashboard();
	List<ActivityWiseUptoPlanAchieveWorkBean> getActivityWiseEPALivProdWorkReport(Integer stCode, Integer distCode, Integer projId,Integer fromYear, String headType);
}
