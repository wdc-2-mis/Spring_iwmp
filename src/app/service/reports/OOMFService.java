package app.service.reports;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.reports.OOMFCurrentStatusBean;

@Service("OOMFService")
public interface OOMFService {
	
	
	List<OOMFCurrentStatusBean> getOOMFCurrentStatusReport();
	List<OOMFCurrentStatusBean> getDistOOMFCurrentStatusReport(Integer stcd);
	
	String getOOMFFinYear();
	String getOOMFFinyearMonth();
	
	List<OOMFCurrentStatusBean> getOOMFBeforePrayashData();
	List<OOMFCurrentStatusBean> getDistOOMFBeforePrayashData(Integer stcd);
	List<OOMFCurrentStatusBean> getProjOOMFBeforePrayashData(Integer dcode);

}
