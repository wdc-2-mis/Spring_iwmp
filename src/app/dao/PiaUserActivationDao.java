package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.model.UserReg;

public interface PiaUserActivationDao {
	LinkedHashMap<Integer,List<UserReg>> getUserList(UserReg userReg,String stateCode);
}
