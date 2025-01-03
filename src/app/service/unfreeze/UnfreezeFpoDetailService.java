package app.service.unfreeze;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.FPOReportBean;

@Service("UnfreezeFpoDetailService")
public interface UnfreezeFpoDetailService {

	List<FPOReportBean> unfreezeFpoDetail(Integer projectId, String group);

	public boolean unfreezeFpoDetailsData(String[] fpo_id, String group);

}
