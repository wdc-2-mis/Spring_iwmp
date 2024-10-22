package app.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.DolrSupportBean;
import app.bean.NetTretatbleAreaBean;
import app.bean.StateProfileWDCBean;
import app.model.master.SlnaStProfile;

@Service("stateProfileWDCService")
public interface StateProfileWDCService {

	LinkedHashMap<Integer, List<StateProfileWDCBean>> getfpodatastatewise(Integer sCode);

	String savestateprofiledata(String status, Integer stateCode, Integer district, Integer blocks, Integer mircowatersheds,
			BigDecimal geoarea, BigDecimal untreatarea, BigDecimal iwmpProjects, BigDecimal waterShedP,
			BigDecimal assIrrigation, String loginID,BigDecimal areacoverwdciwmp);

	LinkedHashMap<Integer,List<SlnaStProfile>> getstateprofiledata(Integer stateCode);
	LinkedHashMap<Integer,List<NetTretatbleAreaBean>> getnetTreatledata();
	
}
