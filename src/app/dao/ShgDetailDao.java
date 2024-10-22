package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.ShgDetailBean;

public interface ShgDetailDao {
	
	List<ShgDetailBean> getListShgDetails(Integer projId, Integer grp);
	
	String saveListShgDetails(List<String> shg_detail_id, List<String> account_detail, List<String> ifsc_code);
	
	String deleteSHGDetails(Integer shg_detail_id);
	
	String completeShgDetails(String[] shg_detail_id1);
	
	List<ShgDetailBean> getShgAccount();
	
	List<ShgDetailBean> distShgAccountReport(int stcode);
	
	List<ShgDetailBean> projShgAccountReport(int dcode);
	
	List<ShgDetailBean> getStatusSHG(Integer stcd);
	
	List<ShgDetailBean> getRemainingSHG(Integer dcode, String type);

}
