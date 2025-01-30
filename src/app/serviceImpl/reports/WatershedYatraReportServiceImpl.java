package app.serviceImpl.reports;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.reports.WatershedYatraReportDao;
import app.model.IwmpDistrict;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.bean.NodalOfficerBean;

@Service("WatershedYatraReportService")
public class WatershedYatraReportServiceImpl implements WatershedYatraReportService{

	@Autowired
	WatershedYatraReportDao dao;
	
	
	
	@Override
	public Map<String, String> getDistrictList(int stateCode) {
		// TODO Auto-generated method stub
		Map<String, String> districtList=new LinkedHashMap<String, String>();
		for (IwmpDistrict temp : dao.getDistrictList(stateCode)) {
			districtList.put(temp.getDcode()+"", temp.getDistName());
		}
		return districtList ;
	}



	@Override
	public Map<String, String> getblockList(Integer userState, Integer district) {
		
		Map<String, String> blockList=new LinkedHashMap<String, String>();
		for(IwmpBlock temp: dao.getBlockList(userState, district)) {
			blockList.put(temp.getBcode()+"", temp.getBlockName());
		}
		return blockList;
	}



	@Override
	public Map<String, String> getGramPanchyatList(Integer block) {
		
		Map<String, String> gpList=new LinkedHashMap<String, String>();
		for(IwmpGramPanchayat temp: dao.getGramPanchyatList(block)) {
			gpList.put(temp.getGcode()+"", temp.getGramPanchayatName());
		}
		return gpList;
	}



	@Override
	public List<NodalOfficerBean> getRoutePlanReportData(Integer State, Integer district, Integer block,
			Integer grampan) {
		// TODO Auto-generated method stub
		return dao.getRoutePlanReportData(State, district, block, grampan);
	}



	@Override
	public List<NodalOfficerBean> getNodalOfficerReportData(String lvl, Integer State, Integer district,
			Integer block) {
		// TODO Auto-generated method stub
		return dao.getNodalOfficerReportData(lvl, State, district, block);
	}



	@Override
	public List<InaugurationBean> getInaugurationReportData(Integer State, Integer district, Integer block) {
		return dao.getInaugurationReportData(State, district, block);
	}

}
