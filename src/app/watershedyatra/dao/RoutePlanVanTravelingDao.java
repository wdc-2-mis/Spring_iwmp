package app.watershedyatra.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import app.watershedyatra.bean.NodalOfficerBean;

public interface RoutePlanVanTravelingDao {
	
	String saveRoutePlanVanTravelingLMS(Integer state, Integer district, Integer block, Integer grampan, 
			Integer village, String location, String datetime, HttpSession session, Integer district1, Integer block1, Integer grampan1, 
			Integer village1, String location1, String datetime1);
	
	List<NodalOfficerBean> getRoutePlanVanTraveling(Integer stcd);

	String getExistingVillageCodes(Integer villageCode);

}
