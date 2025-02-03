package app.watershedyatra.dao;

import java.util.List;

import app.watershedyatra.bean.WatershedYatraBean;

public interface VillageWatershedYatraReportDao {

	List<WatershedYatraBean> showWatershedYatraVillageReport(Integer State, Integer district, Integer block, Integer grampan);
}
