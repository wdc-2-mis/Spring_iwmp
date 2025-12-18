package app.mahotsav.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.bean.DashboardMahotsavBean;
import app.mahotsav.dao.DashboardMahotsavDao;
import app.mahotsav.service.DashboardMahotsavServices;

@Service("DashboardMahotsavServices")
public class DashboardMahotsavServicesImpl implements DashboardMahotsavServices{

	@Autowired
	DashboardMahotsavDao dao;
	
	@Override
	public Map<String, List<DashboardMahotsavBean>> getMahotsavInagrtnYtraAtVillData() {
		// TODO Auto-generated method stub
		return dao.getMahotsavInagrtnYtraAtVillData();
	}

	@Override
	public List<DashboardMahotsavBean> getMahotsavSocialMedia() {
		// TODO Auto-generated method stub
		return dao.getMahotsavSocialMedia();
	}

	@Override
	public List<DashboardMahotsavBean> getParticipantsListofMahotsav() {
		// TODO Auto-generated method stub
		return dao.getParticipantsListofMahotsav();
	}

	@Override
	public List<DashboardMahotsavBean> getStateWiseParticipants() {
		// TODO Auto-generated method stub
		return dao.getStateWiseParticipants();
	}

	

}
