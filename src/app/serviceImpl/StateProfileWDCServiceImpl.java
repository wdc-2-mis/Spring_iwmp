package app.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.NetTretatbleAreaBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.StateProfileWDCBean;
import app.dao.StateProfileWDCDao;
import app.model.master.SlnaStProfile;
import app.service.StateProfileWDCService;

@Service("stateProfileWDCService")
public class StateProfileWDCServiceImpl implements StateProfileWDCService{

	@Autowired(required = true)
	StateProfileWDCDao stateProfileWDCDao;
	
	
	@Override
	public LinkedHashMap<Integer, List<StateProfileWDCBean>>  getfpodatastatewise(Integer sCode) {
		return stateProfileWDCDao.getfpodatastatewise(sCode);
	}


	@Override
	public String savestateprofiledata(String status, Integer stateCode, Integer district, Integer blocks, Integer mircowatersheds,
			BigDecimal geoarea, BigDecimal untreatarea, BigDecimal iwmpProjects, BigDecimal waterShedP,
			BigDecimal assIrrigation, String loginID,BigDecimal areacoverwdciwmp) {
		return stateProfileWDCDao.savestateprofiledata(status, stateCode, district, blocks, mircowatersheds, geoarea, untreatarea, iwmpProjects, waterShedP, assIrrigation, loginID, areacoverwdciwmp );
	}


	@Override
	public LinkedHashMap<Integer, List<SlnaStProfile>> getstateprofiledata(Integer stateCode) {
		
		return stateProfileWDCDao.getstateprofiledata(stateCode);
	}


	@Override
	public LinkedHashMap<Integer, List<NetTretatbleAreaBean>> getnetTreatledata() {
		return stateProfileWDCDao.getnetTreatledata();
	}
}
