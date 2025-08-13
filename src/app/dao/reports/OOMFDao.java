package app.dao.reports;

import java.util.List;

import app.bean.reports.OOMFCurrentStatusBean;

public interface OOMFDao {
	
	List<OOMFCurrentStatusBean> getOOMFCurrentStatusReport();
	List<OOMFCurrentStatusBean> getDistOOMFCurrentStatusReport(Integer stcd);
	String getOOMFFinYear();
	String getOOMFFinyearMonth();
	List<OOMFCurrentStatusBean> getOOMFBeforePrayashData();
	List<OOMFCurrentStatusBean> getDistOOMFBeforePrayashData(Integer stcd);
	List<OOMFCurrentStatusBean> getProjOOMFBeforePrayashData(Integer dcode);
	List<OOMFCurrentStatusBean> getprojOOMFCurrentStatusReport(Integer dcode);
}
