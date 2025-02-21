package app.dao.reports;

import java.util.LinkedHashMap;
import java.util.List;

import app.model.IwmpDistrict;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.PreYatraPreparationBean;
import app.watershedyatra.bean.WatershedYatraStatusBean;

public interface WatershedYatraReportDao {
	
	List<IwmpDistrict> getDistrictList(int stateCode);
	List<IwmpBlock> getBlockList(int stateCode, int dist);
	List<IwmpGramPanchayat> getGramPanchyatList(Integer block);
	
	List<NodalOfficerBean> getRoutePlanReportData(Integer State, Integer district, Integer block, Integer grampan);
	List<NodalOfficerBean> getNodalOfficerReportData(String lvl, Integer State, Integer district, Integer block);
	List<InaugurationBean> getInaugurationReportData(Integer State, Integer district, Integer block, String userdate, String userdateto);
	List<PreYatraPreparationBean> getPreYatraPreparationReportData(Integer State, Integer district, Integer block, Integer grampan);
	List<WatershedYatraStatusBean> getStateWiseWatershedYatraStatus();
}
