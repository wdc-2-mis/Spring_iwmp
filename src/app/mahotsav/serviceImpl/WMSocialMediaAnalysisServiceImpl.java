package app.mahotsav.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.dao.WMSocialMediaAnalysisDao;
import app.mahotsav.model.WatershedMahotsavMediaMaster;
import app.mahotsav.service.WMSocialMediaAnalysisService;
import app.model.IwmpDistrict;

@Service("WMSocialMediaAnalysisService")
public class WMSocialMediaAnalysisServiceImpl implements WMSocialMediaAnalysisService {
	@Autowired
	WMSocialMediaAnalysisDao wmDao;

	@Override
	public Map<String, String> getDistrictList(int stateCode) {
		Map<String, String> districtList=new LinkedHashMap<String, String>();
		for (IwmpDistrict temp : wmDao.getDistrictList(stateCode)) {
			districtList.put(temp.getDistName(), temp.getDcode()+"");
		}
		return districtList ;
	}
	
	 @Override
	    public Map<Integer, String> getPlatformList() {
	        Map<Integer, String> platformMap = new LinkedHashMap<>();
	        List<WatershedMahotsavMediaMaster> mediaList = wmDao.getPlatformList(); 
	        if (mediaList != null) {
	            for (WatershedMahotsavMediaMaster media : mediaList) {
	                platformMap.put(media.getMediaId(), media.getMediaName());
	            }
	        }
	        return platformMap;
	    }


	@Override
	public List<SocialMediaReport> getWMSocialMediaAnalysisReport(Integer stcd, Integer dcode, Integer media,
			String orderBy) {
		return wmDao.getWMSocialMediaAnalysisReport(stcd, dcode, media, orderBy);
	}

}
