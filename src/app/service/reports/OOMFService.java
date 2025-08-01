package app.service.reports;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.reports.OOMFCurrentStatusBean;

@Service("OOMFService")
public interface OOMFService {
	
	
	List<OOMFCurrentStatusBean> getOOMFCurrentStatusReport();
	String getOOMFFinYear();
	String getOOMFFinyearMonth();
	
	List<OOMFCurrentStatusBean> getOOMFBeforePrayashData();

}
