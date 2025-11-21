package app.mahotsav.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;


@Service("WMReportService")
public interface WMReportService {
	
	List<InaugurationMahotsavBean> getStateWMInaugurationReport();

}
