package app.mahotsav.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.SocialMediaReport;


@Service("WMReportService")
public interface WMReportService {
	
	List<InaugurationMahotsavBean> getStateWMInaugurationReport();

	List<InaugurationMahotsavBean> getProjLvlWMPrgReport();

	List<SocialMediaReport> getWMSocailMediaReport(Integer stcd, Integer dcode, Integer bcode, Integer vcode);

}
