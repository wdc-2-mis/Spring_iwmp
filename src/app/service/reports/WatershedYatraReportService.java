package app.service.reports;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.bean.AddOutcomeParaBean;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.PreYatraPreparationBean;
import app.watershedyatra.bean.WatershedYatraStatusBean;

@Service("WatershedYatraReportService")
public interface WatershedYatraReportService {
	
	Map<String, String> getDistrictList(int stateCode);
	Map<String, String> getblockList(Integer userState, Integer district);
	Map<String, String> getGramPanchyatList(Integer block);
	
	List<NodalOfficerBean> getRoutePlanReportData(Integer State, Integer district, Integer block, Integer grampan);
	List<NodalOfficerBean> getNodalOfficerReportData(String lvl, Integer State, Integer district, Integer block);
	List<InaugurationBean> getInaugurationReportData(Integer State, Integer district, Integer block, String userdate, String userdateto);
	List<PreYatraPreparationBean> getPreYatraPreparationReportData(Integer State, Integer district, Integer block, Integer grampan);
	List<WatershedYatraStatusBean> getStateWiseWatershedYatraStatus();
	List<WatershedYatraStatusBean> getDistWiseWatershedYatraStatus(Integer stcd);

}
