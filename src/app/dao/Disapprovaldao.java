package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.DisApprovalBean;

public interface Disapprovaldao {

	List<DisApprovalBean> checkstatus(Integer stcode);
	public Boolean updateslnadisapp(String val, Integer stcode);
	LinkedHashMap<Integer, String> getAllState();
}
