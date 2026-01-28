package app.mahotsav.dao;

import java.util.List;

import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.model.WatershedMahotsavMediaMaster;
import app.model.IwmpDistrict;

public interface WMSocialMediaAnalysisDao {
	
	List<IwmpDistrict> getDistrictList(int stateCode);
	List<WatershedMahotsavMediaMaster> getPlatformList();
	List<SocialMediaReport> getWMSocialMediaAnalysisReport(Integer stcd, Integer dcode, Integer media, String orderBy);
}
