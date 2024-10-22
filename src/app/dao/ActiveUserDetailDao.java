package app.dao;

import java.util.List;

import app.bean.LastActionLogBean;
import app.model.IwmpUserActionLog;
import app.model.IwmpUserProjectMap;
import app.model.UserMap;
import app.model.UserReg;

public interface ActiveUserDetailDao {
	
	//List<UserReg> getUserDetail(long state, String utype);
	List<UserMap> getUserMap(long state, String status);
	List<IwmpUserProjectMap> getUserMap(long state, String utype, String status);
	List<IwmpUserActionLog> getUserLogMap(long state, String sl, String di);
	List<LastActionLogBean> getUserDetailLog(long state, String sl, String di);
}
