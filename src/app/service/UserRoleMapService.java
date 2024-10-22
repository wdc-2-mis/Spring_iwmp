package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.bean.UserRoleMapBean;
import app.model.IwmpUserProjectMap;

@Service("userRoleMapService")
public interface UserRoleMapService {

	LinkedHashMap<Integer,String> getApplicationList();
	LinkedHashMap<Integer,String> getRoleList(HttpSession session);
	LinkedHashMap<String,String> getUserListUnAssigned(String userType);
	LinkedHashMap<String,String> getUserListAssigned(String userType);
	LinkedHashMap<String,String> getUserListUnAssignedByState(String userType,Integer stateCode);
	LinkedHashMap<String,String> getUserListAssignedByState(String userType,Integer stateCode);
	LinkedHashMap<String,String> getUserListUnAssignedByDistrict(String userType,Integer stateCode,Integer distCode);
	LinkedHashMap<String,String> getUserListAssignedByDistrict(String userType,Integer stateCode,Integer distCode);
	LinkedHashMap<Integer,String> getProjectByDistrict(Integer stateCode,Integer distCode);
	LinkedHashMap<Integer,String> getRoleAssignedForUser(String userId);
	LinkedHashMap<Integer,String> getProjectByUser(Integer regId);
	boolean saveUserRoleMap(UserRoleMapBean userRoleMap,String[] projectArray);
	boolean updateUserRoleMap(UserRoleMapBean userRoleMap,String[] projectArray);
	Long getRegId(String userId);
	LinkedHashMap<Integer,String> getSpecificRoleList();
}
