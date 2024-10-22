package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.reports.PhysicalActionAchievementBean;
import app.dao.reports.PhysicalTarAchDao;
import app.service.reports.PhysicalTarAchService;
@Service("PhysicalTarAchService")
public class PhysicalTarAchServiceImpl implements PhysicalTarAchService{

	
	@Autowired
	PhysicalTarAchDao dao;
	
	@Override
	public List<PhysicalActionAchievementBean> getphysicalTarAchReport(Integer stCode, Integer distCode, Integer projId, Integer fromYear) {
		
		return dao.getphysicalTarAchReport(stCode, distCode, projId, fromYear) ;
	}
}
