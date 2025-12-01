package app.mahotsav.dao;

import java.util.List;

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.SocialMediaReport;

public interface WMReportDao {

	List<InaugurationMahotsavBean> getStateWMInaugurationReport();

	List<InaugurationMahotsavBean> getProjLvlWMPrgReport();

	List<SocialMediaReport> getWMSocailMediaReport(Integer stcd, Integer dcode, Integer bcode, Integer vcode);
	
}
