package app.mahotsav.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.mahotsav.bean.SocialMediaReport;

@Service("WMSocialMediaAnalysisService")
public interface WMSocialMediaAnalysisService {
	
	Map<String, String> getDistrictList(int stateCode);

	Map<Integer, String> getPlatformList();

	List<SocialMediaReport> getWMSocialMediaAnalysisReport(Integer stcd, Integer dcode, Integer media, String orderBy);
	

}
