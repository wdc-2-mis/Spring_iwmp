package app.mahotsav.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.model.WatershedMahotsavRegistration;

@Service("AddWMSocialMediaService")
public interface AddWMSocialMediaService {

	WatershedMahotsavRegistration findByUserRegNo(String regNo);
	List<SocialMediaReport> getSocialMediaReport(String stateCode, String districtCode);
	List<SocialMediaReport> getWMSocialMediaReport();
	List<SocialMediaReport> getDistWMSocialMediaReport(Integer stcd);

}
