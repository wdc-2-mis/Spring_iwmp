package app.dao;

import java.util.List;

import app.bean.ProfileBean;
import app.bean.UserBean;
import app.model.UserReg;

public interface ProfileDao {
	
	List<UserReg> getUserDetail(Integer regid);
	List<ProfileBean> getMapstate(Integer regid, String usertype);
    Integer saveProfile(UserBean userReg);
    UserReg getUserDetailasUserReg(Integer regId);
    Integer saveProfileHistory(Integer regid,String moveBy);
    Integer deleteInsertDolrState(Integer regid, String[] stateList);
    List<UserReg> getNewUserDetail(Integer regid);
    
    List<ProfileBean> getPunarutthanStateDist(Integer regid);
}
