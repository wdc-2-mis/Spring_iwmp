package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.WcdcPiaUserBean;
import app.dao.reports.WcdcPiaUserDao;
import app.service.reports.WcdcPiaUserService;

@Service("WcdcPiaUserService")
public class WcdcPiaUserServiceImpl implements WcdcPiaUserService{

	@Autowired
	private WcdcPiaUserDao dao;	
	
	@Override
	public List<WcdcPiaUserBean> getWcdcPiaUserList(String state, String district, String userType) {
		
		return dao.getWcdcPiaUserList(state, district, userType);
	}

}
