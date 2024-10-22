package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.MenuMap;
import app.bean.User;
import app.bean.UserBean;
import app.model.UserReg;

public interface UserSearchDao {
	LinkedHashMap<Integer,List<UserReg>> getUserList(UserReg userReg);
Integer getNextUserNo();
boolean activateUser(UserReg userReg);
boolean inActivateUser(UserReg userReg);
boolean deleteUser(UserReg userReg);
public User getUserByRegIdForUserBean(Integer regId);
}
