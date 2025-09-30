package app.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.bean.LastActionLogBean;
import app.bean.Login;
import app.bean.StateToVillageBean;
import app.bean.User;
import app.model.UserMap;
import app.model.UserReg;

@Service("userService")
public interface UserService {
	void register(User user);
	public void saveUserReg(UserReg userReg);
	List<User> validateUser(Login login);
	Map<String, String>  getStateList();
	Map<String, String> getDistrictList(int stateCode);
	Map<String, String>  getProjectList(int stateCode, int distCode);
	Map<String, String>  getUserProjectList(String stateCode, String distCode);
	Map<String, String> getUseridList(String st_code, String distCode);
	Map<Integer, String> getCurrentFinYear();
	Map<Integer, String> getnotcompletedmonth();
	Map<String, String> getMidTermProjList(int district);
	
	
	//public void saveProfile(UserReg userReg);
	
}
