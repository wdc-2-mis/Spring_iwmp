package app.dao;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import app.model.IwmpDistrict;
import app.model.master.IwmpGramPanchayat;

public interface DistrictMasterDao {
	LinkedHashMap<Integer, String> getDistrictByStateCode(Integer stateCode);

	LinkedHashMap<Integer, String> getDistrictByDistCode(Integer distCode);

	LinkedHashMap<Integer, String> getDistrictByStateCodeWithDcode(Integer stateCode);

	public IwmpDistrict findDistrictDcodecode(int gCode);

	Boolean updateDistrict(IwmpDistrict dist);
	
	public List<IwmpDistrict> getDistrictListState(int stateCode);
	
	public List<IwmpDistrict> getDistrictListDistCode(int distCode);
	
	public List<IwmpDistrict> getDistrictList();
	
	LinkedHashMap<String, Integer> getDistrictDataNew(Integer stateCode);
	
}
