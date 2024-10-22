package app.serviceImpl.master;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.master.ActivityAssetParameterDao;
import app.service.master.ActivityAssetParameterService;

@Service("ActivityAssetParameterService")
public class ActivityAssetParameterServiceImpl implements ActivityAssetParameterService{

	@Autowired
	ActivityAssetParameterDao dao;
	
	@Override
	public LinkedHashMap<Integer, String> getHead() {
		
		return dao.getHead();
	}

	@Override
	public LinkedHashMap<Integer, String> getUnitMasters() {
		// TODO Auto-generated method stub
		return dao.getUnitMasters();
	}

	@Override
	public String saveAssetParameter(Integer ahead, Integer aActivity, String parameterDesc, Integer aUnit,
			String loginid) {
		// TODO Auto-generated method stub
		return dao.saveAssetParameter(ahead, aActivity, parameterDesc, aUnit, loginid);
	}

}
