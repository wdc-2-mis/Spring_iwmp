package app.mahotsav.dao;

import java.util.List;

import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.model.WatershedMahotsavRegistration;

public interface AddWMSocialMediaDao {
	
	WatershedMahotsavRegistration findByUserRegNo(String regNo);
	List<SocialMediaReport> getSocialMediaReport(String stateCode, String districtCode);
	List<SocialMediaReport> getWMSocialMediaReport();
	List<SocialMediaReport> getDistWMSocialMediaReport(Integer stcd);
}
