package app.watershedyatra.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.dao.RoutePlanVanTravelingDao;
import app.watershedyatra.service.RoutePlanVanTravelingServices;

@Service("RoutePlanVanTravelingServices")
public class RoutePlanVanTravelingServicesImpl implements RoutePlanVanTravelingServices{

	@Autowired
	RoutePlanVanTravelingDao dao;
	
	
	
	
	@Override
	public String saveRoutePlanVanTravelingLMS(Integer state, Integer district, Integer block, Integer grampan,
			Integer village, String location, String datetime, HttpSession session, Integer district1, Integer block1,
			Integer grampan1, Integer village1, String location1, String datetime1) {
		// TODO Auto-generated method stub
		return dao.saveRoutePlanVanTravelingLMS(state, district, block, grampan, village, location, datetime, session, district1, block1, grampan1, village1, location1, datetime1);
	}




	@Override
	public List<NodalOfficerBean> getRoutePlanVanTraveling(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getRoutePlanVanTraveling(stcd);
	}




	@Override
	public String getExistingVillageCodes(Integer villageCode) {
		// TODO Auto-generated method stub
		return dao.getExistingVillageCodes(villageCode);
	}

}
