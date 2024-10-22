package app.dao;

import java.util.List;

import app.bean.OOMFAchvDetailsBean;

public interface OOMFAchvDetailsDao {

	List<OOMFAchvDetailsBean> getOOMFAchvDetails(Integer finyr);
}
