package app.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.LastActionLogBean;
import app.dao.ActiveUserDetailDao;
import app.model.IwmpUserActionLog;
import app.model.IwmpUserProjectMap;
import app.model.UserMap;
import app.model.UserReg;
import app.service.ActiveUserDetailService;

@Service("ActiveUserDetailService")
public class ActiveUserDetailServiceImpl implements ActiveUserDetailService{

	
	@Autowired
	private ActiveUserDetailDao audDao;
	
	
	/*
	 * @Override
	 * 
	 * @Transactional public List<UserReg> getUserDetail(long state, String utype) {
	 * 
	 * return audDao.getUserDetail(state, utype); }
	 */


	@Override
	//@Transactional
	public List<UserMap> getUserMap(long state, String status) {
		return audDao.getUserMap(state, status);
	}


	@Override
	//@Transactional
	public List<IwmpUserProjectMap> getUserMap(long state, String utype, String status) {
		return audDao.getUserMap(state,utype,status);
	}


	@Override
	public List<IwmpUserActionLog> getUserLogMap(long state, String sl, String di) {
		return audDao.getUserLogMap(state,sl, di);
	}


	@Override
	public List<LastActionLogBean> getUserDetailLog(long state, String sl, String di) {
		
		return audDao.getUserDetailLog(state, sl, di);
	}
	
	
	
	
	
}
