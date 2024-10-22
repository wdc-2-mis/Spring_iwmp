package app.service.reports;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.reports.PhysicalHeadActivityListBean;
@Service("PhysicalHeadActivityListServices")
public interface PhysicalHeadActivityListServices {
	
	List<PhysicalHeadActivityListBean> getPhysicalHeadActivityList();

}
