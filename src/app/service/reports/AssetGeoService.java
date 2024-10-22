package app.service.reports;

import java.math.BigInteger;
import java.util.List;

import app.bean.reports.AssetGeoRefBean;
import app.model.AssetGeoReference;

public interface AssetGeoService {
	
	public List<AssetGeoRefBean> getGeoRefData();
	public List<AssetGeoRefBean> getDistWiseGeoRefData(int stcode);
	public List<AssetGeoRefBean> getProjWiseGeoRefData(int dcode);
	public List<AssetGeoRefBean> getProjWiseGeoRefDetails(int projid);
	public List<AssetGeoRefBean> getGeoImages(String wrkid, String stage);

}
