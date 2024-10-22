package app.service.reports;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.reports.PhysicalActionAchievementBean;

@Service("PhysicalTarAchService")
public interface PhysicalTarAchService {
	
	List<PhysicalActionAchievementBean> getphysicalTarAchReport(Integer stCode, Integer distCode, Integer projId,Integer fromYear);

}
