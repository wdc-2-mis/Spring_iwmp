package app.watershedyatra.dao;

import java.util.List;

import app.watershedyatra.bean.NodalOfficerBean;

public interface WatershedYatraParticipantDao {

	List<NodalOfficerBean> getWatershedYatraParticipant(String userdate, String userdateto);
	List<NodalOfficerBean> getWatershedYatraParticipantgrand(String userdate, String userdateto);
	
}
