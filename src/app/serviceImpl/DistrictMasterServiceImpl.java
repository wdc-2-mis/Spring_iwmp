package app.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.DistrictMasterDao;
import app.model.IwmpDistrict;
import app.service.DistrictMasterService;

@Service("districtMasterService")
public class DistrictMasterServiceImpl implements DistrictMasterService{
	
	@Autowired
	DistrictMasterDao districtMasterDao;

	@Override
	//@Transactional
	public LinkedHashMap<Integer, String> getDistrictByStateCode(Integer stateCode) {
		// TODO Auto-generated method stub
		return districtMasterDao.getDistrictByStateCode(stateCode);
	}
	
	@Override
	//@Transactional
	public LinkedHashMap<Integer, String> getDistrictByDistCode(Integer distCode) {
		// TODO Auto-generated method stub
		return districtMasterDao.getDistrictByDistCode(distCode);
	}

	@Override
	//@Transactional
	public LinkedHashMap<Integer, String> getDistrictByStateCodeWithDcode(Integer stateCode) {
		// TODO Auto-generated method stub
		return districtMasterDao.getDistrictByStateCodeWithDcode(stateCode);
	}

	@Override
	public app.model.IwmpDistrict findDistrictDcode(int dCode) {
		// TODO Auto-generated method stub
		return districtMasterDao.findDistrictDcodecode(dCode);
	}

	@Override
	public Boolean updateDistrict(app.model.IwmpDistrict district) {
		// TODO Auto-generated method stub
		return districtMasterDao.updateDistrict(district);
	}

	@Override
	public List<IwmpDistrict> getDistrictListState(int stateCode) {
		// TODO Auto-generated method stub getDistrictListDistCode
		return districtMasterDao.getDistrictListState(stateCode);
	}

	@Override
	public List<IwmpDistrict> getDistrictListDistCode(int distCode) {
		// TODO Auto-generated method stub getDistrictListDistCode
		return districtMasterDao.getDistrictListDistCode(distCode);
	}

	@Override
	public LinkedHashMap<String, Integer> getDistrictDataNew(Integer stateCode) {
		// TODO Auto-generated method stub
		return districtMasterDao.getDistrictDataNew(stateCode);
	}

}
