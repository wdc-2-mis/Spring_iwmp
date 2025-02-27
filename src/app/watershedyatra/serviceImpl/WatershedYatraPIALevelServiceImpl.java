package app.watershedyatra.serviceImpl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.watershedyatra.dao.WatershedYatraPIALevelDao;
import app.watershedyatra.service.WatershedYatraPIALevelService;

@Service("WatershedYatraPIALevelService")
public class WatershedYatraPIALevelServiceImpl implements WatershedYatraPIALevelService{

	@Autowired
	WatershedYatraPIALevelDao dao;
	
	
	@Override
	public LinkedHashMap<Integer, String> getBlockListpia(String userid) {
		// TODO Auto-generated method stub
		return dao.getBlockListpia(userid);
	}


	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraAtPiaGPs(Integer blkCode, String userid) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraAtPiaGPs(blkCode, userid);
	}


	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraAtPiaVillage(Integer gpCode, String userid) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraAtPiaVillage(gpCode, userid);
	}

}
