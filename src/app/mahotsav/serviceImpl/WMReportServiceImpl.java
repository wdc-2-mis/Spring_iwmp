package app.mahotsav.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;
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
	

}
