package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.LastActionLogBean;
import app.model.IwmpUserActionLog;
import app.model.IwmpUserProjectMap;
import app.model.UserMap;
import app.model.UserReg;

@Service("ActiveUserDetailService")
public interface ActiveUserDetailService {
	
//	List<UserReg> getUserDetail(long state, String utype);
	List<UserMap> getUserMap(long state, String status);
	List<IwmpUserProjectMap> getUserMap(long state, String utype, String status);
	List<IwmpUserActionLog> getUserLogMap(long state, String sl, String di);
	List<LastActionLogBean> getUserDetailLog(long state, String sl, String di);
}
