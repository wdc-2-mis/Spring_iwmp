package app.mahotsav.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.bean.WatrshdInagrtnPreYtraDashBean;
import app.mahotsav.bean.DashboardMahotsavBean;
import app.mahotsav.bean.SocialMediaReport;

@Service("DashboardMahotsavServices")
public interface DashboardMahotsavServices {
	
	Map<String, List<DashboardMahotsavBean>> getMahotsavInagrtnYtraAtVillData();
	
	List<DashboardMahotsavBean> getMahotsavSocialMedia();
	
	List<DashboardMahotsavBean> getParticipantsListofMahotsav();
	
	List<DashboardMahotsavBean> getStateWiseParticipants();
	
	List<DashboardMahotsavBean> getStWiseWatershedMahotsavDashboardData();
	
	List<DashboardMahotsavBean> getStWiseWatershedMahotsavProjectLevelData();

	List<SocialMediaReport> getSocialpDashboardData();
	
	
	

}
