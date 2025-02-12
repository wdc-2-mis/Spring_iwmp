package app.watershedyatra.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.dao.VillageWatershedYatraReportDao;
import app.watershedyatra.service.VillageWatershedYatraReportService;

@Service("VillageWatershedYatraReportService")
public class VillageWatershedYatraReportServiceImpl implements VillageWatershedYatraReportService{

	@Autowired
	VillageWatershedYatraReportDao dao;
	
	@Override
	public List<WatershedYatraBean> showWatershedYatraVillageReport(Integer State, Integer district, Integer block,
			Integer grampan, String userdate) {
		
		return dao.showWatershedYatraVillageReport(State, district, block, grampan, userdate);
	}

}
