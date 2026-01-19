package app.watershedyatra.dao;

import java.util.List;

import app.bean.NRSCWorksBean;
import app.watershedyatra.bean.WatershedYatraBean;

public interface VillageWatershedYatraReportDao {

	List<WatershedYatraBean> showWatershedYatraVillageReport(Integer State, Integer district, Integer block, Integer grampan, String userdate, String userdateto);

	List<NRSCWorksBean> getWorkStatusReport(Integer State, String userdate, String userdateto);
	
	List<NRSCWorksBean> getActivityNRMWorkReportPost(Integer State, Integer district, Integer finyr, Integer activity);
	
	List<NRSCWorksBean> getActivityNRMWorkJalSakati(Integer finyr);

}
