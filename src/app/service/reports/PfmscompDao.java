package app.service.reports;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.PfmsComponentBean;
import app.bean.PfmsTransactionBean;

public interface PfmscompDao {
	
	LinkedHashMap<String, String> getAllComponent();
	
	List<PfmsComponentBean> getPfmsComponentReport(String stcode,String comp[], String finyr);
	
	List<PfmsComponentBean> getdistPfmsComponentReport(String stcode, String comp, String finyr);


}
