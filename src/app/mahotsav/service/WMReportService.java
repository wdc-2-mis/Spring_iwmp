package app.mahotsav.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.bean.WMMediaReviewBean;


@Service("WMReportService")
public interface WMReportService {
	
	List<InaugurationMahotsavBean> getStateWMInaugurationReport();

	List<InaugurationMahotsavBean> getProjLvlWMPrgReport();

	List<SocialMediaReport> getWMSocialMediaReport(Integer stcd, Integer dcode, Integer bcode, Integer vcode);
	
	Map<String, String> getDistrictList(int stateCode);
	Map<String, String> getblockList(Integer userState, Integer district);
	Map<String, String> getmahotsavvillageList(int block);

	List<String> getImageMahotsavProjAtStLVL(Integer stCode, String imgType);

	List<InaugurationMahotsavBean> getdistWMProjLvlProgRpt(int stcd);

	List<String> getImageMahotsavProjAtDistLVL(Integer distCode, String imgType);
	
	List<WMMediaReviewBean> getWMSocialMediaComDetails(Integer stcode, Integer dcode, Integer platform, String status);

}
