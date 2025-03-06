package app.watershedyatra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.watershedyatra.bean.NodalOfficerBean;

@Service("WatershedYatraParticipantServices")
public interface WatershedYatraParticipantServices {
	
	List<NodalOfficerBean> getWatershedYatraParticipant(String userdate, String userdateto);
	List<NodalOfficerBean> getWatershedYatraParticipantgrand(String userdate, String userdateto);

}
