package app.mahotsav.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.dao.WMReportDao;
import app.mahotsav.service.WMReportService;


@Service("WMReportService")
public class WMReportServiceImpl implements WMReportService{
	
	@Autowired
	WMReportDao WMDao;

	@Override
	public List<InaugurationMahotsavBean> getStateWMInaugurationReport() {
		return WMDao.getStateWMInaugurationReport();
	}

	@Override
	public List<InaugurationMahotsavBean> getProjLvlWMPrgReport() {
		// TODO Auto-generated method stub
		return WMDao.getProjLvlWMPrgReport();
	}

	@Override
	public List<SocialMediaReport> getWMSocailMediaReport(Integer stcd, Integer dcode, Integer bcode, Integer vcode) {
		return WMDao.getWMSocailMediaReport(stcd, dcode, bcode, vcode);
	}
	

}
