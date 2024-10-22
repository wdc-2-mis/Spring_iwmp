package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.User;
import app.bean.UserBean;
import app.model.UserReg;

@Service("userSearchService")
public interface UserSearchService {
	LinkedHashMap<Integer,List<UserReg>> getUserList(UserReg userReg);
	Integer getNextUserNo();
	boolean activateUser(UserReg userReg);
	boolean inActivateUser(UserReg userReg);
	boolean deleteUser(UserReg userReg);
	public User getUserByRegIdForUserBean(Integer regId);
}
