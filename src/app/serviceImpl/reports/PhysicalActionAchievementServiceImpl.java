package app.serviceImpl.reports;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.reports.ActivityWiseUptoPlanAchieveWorkBean;
import app.bean.reports.PhysicalActionAchievementBean;
import app.dao.reports.PhysicalActionAchievementDao;
import app.service.reports.PhysicalActionAchievementService;

@Service("PhysicalActionAchievementService")
public class PhysicalActionAchievementServiceImpl implements PhysicalActionAchievementService{

	@Autowired
	PhysicalActionAchievementDao dao;
	
	@Override
	public LinkedHashMap<Integer, String> getYearForPhysicalActionAchievementReport(Integer pCode) {
		// TODO Auto-generated method stub
		return dao.getYearForPhysicalActionAchievementReport(pCode);
	}
	
	@Override
	public List<PhysicalActionAchievementBean> getPhysicalActionAchievementReport(Integer stCode, Integer distCode, Integer projId, Integer fromYear) {
		
		return dao.getPhysicalActionAchievementReport(stCode, distCode, projId, fromYear) ;
	}

	@Override
	public List<ActivityWiseUptoPlanAchieveWorkBean> getActivityWiseUptoPlanAchievWorkReport(Integer stCode,
			Integer distCode, Integer projId, String fromYear, String sdate, String edate) {
		// TODO Auto-generated method stub
		return dao.getActivityWiseUptoPlanAchievWorkReport(stCode, distCode, projId, fromYear, sdate, edate);
	}

	@Override
	public LinkedHashMap<Integer, String> getYearForAchDashboard() {
		return dao.getYearForAchDashboard();
	}

	@Override
	public List<ActivityWiseUptoPlanAchieveWorkBean> getActivityWiseEPALivProdWorkReport(Integer stCode,
			Integer distCode, Integer projId, Integer fromYear, String headType) {
		// TODO Auto-generated method stub
		return dao.getActivityWiseEPALivProdWorkReport(stCode, distCode, projId, fromYear, headType);
	}

}
