package app.service;

import java.util.List;

import app.bean.OOMFAchvDetailsBean;

public interface OOMFAchvDetailsService {
	
	List<OOMFAchvDetailsBean> getOOMFAchvDetails(Integer finyr);
}
