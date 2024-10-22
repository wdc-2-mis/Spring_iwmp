package app.serviceImpl.reports;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.reports.AssetGeoRefBean;
import app.dao.reports.AssetGeoDao;
import app.model.AssetGeoReference;
import app.service.reports.AssetGeoService;

@Service("AssetGeoService")
public class AssetGeoServiceImpl implements AssetGeoService{

	@Autowired
	AssetGeoDao assetGeoDao;
	
	@Override
	public List<AssetGeoRefBean> getGeoRefData() {
		// TODO Auto-generated method stub
		return assetGeoDao.getGeoRefData();
	}

	@Override
	public List<AssetGeoRefBean> getDistWiseGeoRefData(int stcode) {
		// TODO Auto-generated method stub
		return assetGeoDao.getDistWiseGeoRefData(stcode);
	}

	@Override
	public List<AssetGeoRefBean> getProjWiseGeoRefData(int dcode) {
		// TODO Auto-generated method stub
		return assetGeoDao.getProjWiseGeoRefData(dcode);
	}

	@Override
	public List<AssetGeoRefBean> getProjWiseGeoRefDetails(int projid) {
		// TODO Auto-generated method stub
		return assetGeoDao.getProjWiseGeoRefDetails(projid);
	}

	@Override
	public List<AssetGeoRefBean> getGeoImages(String wrkid, String stage) {
		// TODO Auto-generated method stub
		return assetGeoDao.getGeoImages(wrkid,stage);
	}

}
