package app.mahotsav.dao;

import java.util.List;

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.bean.WMMediaReviewBean;
import app.model.IwmpDistrict;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;

public interface WMReportDao {

	List<InaugurationMahotsavBean> getStateWMInaugurationReport();

	List<InaugurationMahotsavBean> getProjLvlWMPrgReport();

	List<SocialMediaReport> getWMSocialMediaReport(Integer stcd, Integer dcode, Integer bcode, Integer vcode);
	
	List<IwmpDistrict> getDistrictList(int stateCode);
	List<IwmpBlock> getBlockList(int stateCode, int dist);
	List<IwmpVillage> getVillageList(int block);

	List<String> getImageMahotsavProjAtStLVL(Integer stCode, String imgType);

	List<InaugurationMahotsavBean> getdistWMProjLvlProgRpt(int stcd);

	List<String> getImageMahotsavProjAtDistLVL(Integer distCode, String imgType);
	
	List<WMMediaReviewBean> getWMSocialMediaComDetails(Integer stcode, Integer dcode, Integer platform, String status);
	List<WMMediaReviewBean> getTotNoOfScrnshtUploaded();
	
}
