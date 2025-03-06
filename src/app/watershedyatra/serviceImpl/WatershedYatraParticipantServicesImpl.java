package app.watershedyatra.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.dao.WatershedYatraParticipantDao;
import app.watershedyatra.service.WatershedYatraParticipantServices;

@Service("WatershedYatraParticipantServices")
public class WatershedYatraParticipantServicesImpl implements WatershedYatraParticipantServices{

	
	@Autowired
	WatershedYatraParticipantDao dao;
	
	
	
	@Override
	public List<NodalOfficerBean> getWatershedYatraParticipant(String userdate, String userdateto) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraParticipant(userdate, userdateto);
	}



	@Override
	public List<NodalOfficerBean> getWatershedYatraParticipantgrand(String userdate, String userdateto) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraParticipantgrand(userdate, userdateto);
	}

}
