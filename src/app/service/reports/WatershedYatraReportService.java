package app.service.reports;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.watershedyatra.bean.NodalOfficerBean;

@Service("WatershedYatraReportService")
public interface WatershedYatraReportService {
	
	Map<String, String> getDistrictList(int stateCode);
	Map<String, String> getblockList(Integer userState, Integer district);
	Map<String, String> getGramPanchyatList(Integer block);
	
	List<NodalOfficerBean> getRoutePlanReportData(Integer State, Integer district, Integer block, Integer grampan);
	List<NodalOfficerBean> getNodalOfficerReportData(String lvl, Integer State, Integer district, Integer block);

}
