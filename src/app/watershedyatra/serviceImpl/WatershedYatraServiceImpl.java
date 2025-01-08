package app.watershedyatra.serviceImpl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.watershedyatra.dao.WatershedYatraDao;
import app.watershedyatra.service.WatershedYatraService;

@Service("WatershedYatraService")
public class WatershedYatraServiceImpl implements WatershedYatraService{
	
	
	@Autowired
	WatershedYatraDao dao;
	
	

	@Override
	public LinkedHashMap<Integer, String> getDistrictList(int stcode) {
		// TODO Auto-generated method stub
		return dao.getDistrictList(stcode);
	}



	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraBlock(Integer distcd) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraBlock(distcd);
	}



	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraGPs(Integer blkCode) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraGPs(blkCode);
	}



	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraVillage(Integer gpCode) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraVillage( gpCode);
	}

}
