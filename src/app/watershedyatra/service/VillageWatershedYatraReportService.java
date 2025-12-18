package app.watershedyatra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.NRSCWorksBean;
import app.watershedyatra.bean.WatershedYatraBean;

@Service("VillageWatershedYatraReportService")
public interface VillageWatershedYatraReportService {
	
	List<WatershedYatraBean> showWatershedYatraVillageReport(Integer State, Integer district, Integer block, Integer grampan, String userdate, String userdateto);
	
	List<NRSCWorksBean> getWorkStatusReport(Integer State,  String userdate, String userdateto);
	
	List<NRSCWorksBean> getActivityNRMWorkReportPost(Integer State, Integer district, Integer finyr, Integer activity);
	
	List<NRSCWorksBean> getActivityNRMWorkJalSakati();

}
