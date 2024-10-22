package app.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.model.IwmpDistrict;
import app.model.master.IwmpGramPanchayat;

@Service("districtMasterService")
public interface DistrictMasterService {
	LinkedHashMap<Integer, String> getDistrictByStateCode(Integer stateCode);

	LinkedHashMap<Integer, String> getDistrictByDistCode(Integer distCode);

	LinkedHashMap<Integer, String> getDistrictByStateCodeWithDcode(Integer stateCode);

	public IwmpDistrict findDistrictDcode(int dCode);

	Boolean updateDistrict(IwmpDistrict district);

	public List<IwmpDistrict> getDistrictListState(int stateCode);
	
	public List<IwmpDistrict> getDistrictListDistCode(int distCode);
	
	LinkedHashMap<String, Integer> getDistrictDataNew(Integer stateCode);
}
