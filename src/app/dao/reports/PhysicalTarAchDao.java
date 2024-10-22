package app.dao.reports;

import java.util.List;

import app.bean.reports.PhysicalActionAchievementBean;

public interface PhysicalTarAchDao {

	List<PhysicalActionAchievementBean> getphysicalTarAchReport(Integer stCode, Integer distCode, Integer projId,Integer fromYear);
}
