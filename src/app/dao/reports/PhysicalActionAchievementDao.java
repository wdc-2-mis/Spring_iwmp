package app.dao.reports;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.reports.ActivityWiseUptoPlanAchieveWorkBean;
import app.bean.reports.PhysicalActionAchievementBean;

public interface PhysicalActionAchievementDao {
	
	LinkedHashMap<Integer, String> getYearForPhysicalActionAchievementReport(Integer pCode);
	List<PhysicalActionAchievementBean> getPhysicalActionAchievementReport(Integer stCode, Integer distCode, Integer projId,Integer fromYear);
	List<ActivityWiseUptoPlanAchieveWorkBean> getActivityWiseUptoPlanAchievWorkReport(Integer stCode, Integer distCode, Integer projId,String fromYear, String sdate, String edate);
	LinkedHashMap<Integer, String> getYearForAchDashboard();
	List<ActivityWiseUptoPlanAchieveWorkBean> getActivityWiseEPALivProdWorkReport(Integer stCode, Integer distCode, Integer projId,Integer fromYear, String headType);
	
}
