package app.watershedyatra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.watershedyatra.bean.WatershedYatraBean;

@Service("VillageWatershedYatraReportService")
public interface VillageWatershedYatraReportService {
	
	List<WatershedYatraBean> showWatershedYatraVillageReport(Integer State, Integer district, Integer block, Integer grampan, String userdate);

}
