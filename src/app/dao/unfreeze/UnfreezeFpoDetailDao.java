package app.dao.unfreeze;

import java.util.List;

import app.bean.FPOReportBean;

public interface UnfreezeFpoDetailDao {
	
	List<FPOReportBean> unfreezeFpoDetail(Integer projectId, String group);
	
	public boolean unfreezeFpoDetailsData(String[] fpo_id, String group);

	
}
