package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.reports.PhysicalHeadActivityListBean;
import app.dao.reports.PhysicalHeadActivityListDao;
import app.service.reports.PhysicalHeadActivityListServices;

@Service("PhysicalHeadActivityListServices")
public class PhysicalHeadActivityListServicesImpl implements PhysicalHeadActivityListServices{

	@Autowired
	PhysicalHeadActivityListDao  dao;
	
	
	@Override
	public List<PhysicalHeadActivityListBean> getPhysicalHeadActivityList() {
		// TODO Auto-generated method stub
		return dao.getPhysicalHeadActivityList();
	}

}
