package app.mahotsav.dao;

import java.util.List;
import java.util.Map;

import app.mahotsav.bean.DashboardMahotsavBean;

public interface DashboardMahotsavDao {
	
	Map<String, List<DashboardMahotsavBean>> getMahotsavInagrtnYtraAtVillData();
	
	List<DashboardMahotsavBean> getMahotsavSocialMedia();
	
	List<DashboardMahotsavBean> getParticipantsListofMahotsav();
	
	List<DashboardMahotsavBean> getStateWiseParticipants();
	

}
