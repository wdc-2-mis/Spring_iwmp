package app.serviceImpl.outcomes;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.OtherActivityNameWithAssetBean;
import app.dao.outcomes.OtherActivityNameWithAssetDao;
import app.service.outcome.OtherActivityNameWithAssetService;

@Service("OtherActivityNameWithAssetService")
public class OtherActivityNameWithAssetServiceImpl implements OtherActivityNameWithAssetService{

	@Autowired
	private OtherActivityNameWithAssetDao dao;
	
	@Override
	public List<OtherActivityNameWithAssetBean> getcreateAssetOtherActivityName(String projectId, Integer stcode,
			String year) {
		// TODO Auto-generated method stub
		return dao.getcreateAssetOtherActivityName(projectId, stcode, year);
	}

	@Override
	public String updateOtherActivityName(String projectId, Integer stcode, String year, String[] workid,
			String[] othername) {
		// TODO Auto-generated method stub
		return dao.updateOtherActivityName(projectId, stcode, year, workid, othername);
	}

	@Override
	public List<OtherActivityNameWithAssetBean> getAssetOtherActivityName(String project, Integer stcode, String year) {
		// TODO Auto-generated method stub
		return dao.getAssetOtherActivityName( project, stcode, year);
	}

	@Override
	public LinkedHashMap<Integer, String> getOtherNameMaster(Integer stcode) {
		// TODO Auto-generated method stub
		return dao.getOtherNameMaster(stcode);
	}

}
