package app.service.UnfreezeImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.FPOReportBean;
import app.dao.unfreeze.UnfreezeFpoDetailDao;
import app.service.unfreeze.UnfreezeFpoDetailService;

@Service("UnfreezeFpoDetailService")
public class UnfreezeFpoDetailServiceImpl implements UnfreezeFpoDetailService{

	@Autowired(required = true)
	UnfreezeFpoDetailDao unfreezeFpoDetailDao;

	@Override
	public List<FPOReportBean> unfreezeFpoDetail(Integer projectId, String group) {
		
		return unfreezeFpoDetailDao.unfreezeFpoDetail(projectId, group);
	}

	@Override
	public boolean unfreezeFpoDetailsData(String[] fpo_id, String group) {

		return unfreezeFpoDetailDao.unfreezeFpoDetailsData(fpo_id, group);
	}
	

}
