package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.User;
import app.bean.UserBean;
import app.dao.UserSearchDao;
import app.daoImpl.UserDaoImpl;
import app.model.UserReg;
import app.service.UserSearchService;

@Service("userSearchService")
public class UserSearchServiceImpl implements UserSearchService{
	
	@Autowired(required = true)
	UserSearchDao dao;

	@Override
	//@Transactional
	public LinkedHashMap<Integer,List<UserReg>> getUserList(UserReg userReg) {
		// TODO Auto-generated method stub
		return dao.getUserList(userReg);
	}

	@Override
	//@Transactional
	public Integer getNextUserNo() {
		// TODO Auto-generated method stub
		return dao.getNextUserNo();
	}

	@Override
	public boolean activateUser(UserReg userReg) {
		// TODO Auto-generated method stub
		return dao.activateUser(userReg);
	}
	
	@Override
	//@Transactional
	public boolean inActivateUser(UserReg userReg) {
		// TODO Auto-generated method stub
		return dao.inActivateUser(userReg);
	}
	
	@Override
	//@Transactional
	public boolean deleteUser(UserReg userReg) {
		// TODO Auto-generated method stub
		return dao.deleteUser(userReg);
	}
	
	@Override
	//@Transactional
	public User getUserByRegIdForUserBean(Integer regId) {
		// TODO Auto-generated method stub
		return dao.getUserByRegIdForUserBean(regId);
	}
	

}
