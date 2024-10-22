package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.UserRoleMapBean;
import app.dao.UserRoleMapDao;
import app.model.IwmpUserProjectMap;
import app.service.UserRoleMapService;

@Service("userRoleMapService")
public class UserRoleMapServiceImpl implements UserRoleMapService{

	@Autowired(required = true)
	UserRoleMapDao dao;
	
	@Override
	//@Transactional
	public LinkedHashMap<Integer,String> getApplicationList() {
		// TODO Auto-generated method stub
		return dao.getApplicationList();
	}
	
	@Override
	//@Transactional
	public LinkedHashMap<Integer,String> getRoleList(HttpSession session) {
		// TODO Auto-generated method stub
		return dao.getRoleList(session);
	}

	@Override
	//@Transactional
	public LinkedHashMap<String, String> getUserListUnAssigned(String userType) {
		// TODO Auto-generated method stub
		return dao.getUserListUnAssigned(userType);
	}
	
	@Override
	//@Transactional
	public LinkedHashMap<String, String> getUserListAssigned(String userType) {
		// TODO Auto-generated method stub
		return dao.getUserListAssigned(userType);
	}
	
	@Override
	//@Transactional
	public LinkedHashMap<String, String> getUserListUnAssignedByState(String userType,Integer stateCode) {
		// TODO Auto-generated method stub
		return dao.getUserListUnAssignedByState(userType,stateCode);
	}
	
	@Override
	//@Transactional
	public LinkedHashMap<String, String> getUserListAssignedByState(String userType,Integer stateCode) {
		// TODO Auto-generated method stub
		return dao.getUserListAssignedByState(userType,stateCode);
	}

	@Override
	//@Transactional
	public boolean saveUserRoleMap(UserRoleMapBean userRoleMap,String[] projectArray) {
		// TODO Auto-generated method stub
		return dao.saveUserRoleMap(userRoleMap,projectArray);
	}
	
	@Override
	//@Transactional
	public boolean updateUserRoleMap(UserRoleMapBean userRoleMap,String[] projectArray) {
		// TODO Auto-generated method stub
		return dao.updateUserRoleMap(userRoleMap,projectArray);
	}

	@Override
	//@Transactional
	public LinkedHashMap<String, String> getUserListUnAssignedByDistrict(String userType, Integer stateCode,
			Integer distCode) {
		// TODO Auto-generated method stub
		return dao.getUserListUnAssignedByDistrict(userType,stateCode,distCode);
	}
	
	@Override
	//@Transactional
	public LinkedHashMap<String, String> getUserListAssignedByDistrict(String userType, Integer stateCode,
			Integer distCode) {
		// TODO Auto-generated method stub
		return dao.getUserListAssignedByDistrict(userType,stateCode,distCode);
	}

	@Override
	//@Transactional
	public LinkedHashMap<Integer, String> getProjectByDistrict(Integer stateCode, Integer distCode) {
		// TODO Auto-generated method stub
		return dao.getProjectByDistrict(stateCode,distCode);
	}
	
	@Override
	//@Transactional
	public LinkedHashMap<Integer, String> getRoleAssignedForUser(String userId) {
		// TODO Auto-generated method stub
		return dao.getRoleAssignedForUser(userId);
	}
	
	@Override
	//@Transactional
	public LinkedHashMap<Integer, String> getProjectByUser(Integer regId) {
		// TODO Auto-generated method stub
		return dao.getProjectByUser(regId);
	}

	@Override
	//@Transactional
	public Long getRegId(String userId) {
		// TODO Auto-generated method stub
		return dao.getRegId(userId);
	}

	@Override
	//@Transactional
	public LinkedHashMap<Integer,String> getSpecificRoleList() {
		// TODO Auto-generated method stub
		return dao.getSpecificRoleList();
	}
}
