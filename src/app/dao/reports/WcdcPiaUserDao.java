package app.dao.reports;

import java.util.List;

import app.bean.WcdcPiaUserBean;

public interface WcdcPiaUserDao {

	List<WcdcPiaUserBean> getWcdcPiaUserList(String state, String district,  String userType);
}
