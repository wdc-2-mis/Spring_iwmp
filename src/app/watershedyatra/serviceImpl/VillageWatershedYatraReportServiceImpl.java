package app.watershedyatra.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.NRSCWorksBean;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.dao.VillageWatershedYatraReportDao;
import app.watershedyatra.service.VillageWatershedYatraReportService;

@Service("VillageWatershedYatraReportService")
public class VillageWatershedYatraReportServiceImpl implements VillageWatershedYatraReportService{

	@Autowired
	VillageWatershedYatraReportDao dao;
	
	@Override
	public List<WatershedYatraBean> showWatershedYatraVillageReport(Integer State, Integer district, Integer block,
			Integer grampan, String userdate, String userdateto) {
		
		return dao.showWatershedYatraVillageReport(State, district, block, grampan, userdate, userdateto);
	}

	@Override
	public List<NRSCWorksBean> getWorkStatusReport(Integer State, String userdate, String userdateto) {
		// TODO Auto-generated method stub
		return dao.getWorkStatusReport(State, userdate, userdateto);
	}

	@Override
	public List<NRSCWorksBean> getActivityNRMWorkReportPost(Integer State, Integer district, Integer finyr, Integer activity) {
		// TODO Auto-generated method stub
		return dao.getActivityNRMWorkReportPost(State, district, finyr, activity);
	}

	@Override
	public List<NRSCWorksBean> getActivityNRMWorkJalSakati() {
		// TODO Auto-generated method stub
		return dao.getActivityNRMWorkJalSakati();
	}

}
