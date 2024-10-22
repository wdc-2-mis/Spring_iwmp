package app.service.reports;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.WcdcPiaUserBean;

@Service("WcdcPiaUserService")
public interface WcdcPiaUserService {
	List<WcdcPiaUserBean> getWcdcPiaUserList(String state, String district,  String userType);

}
