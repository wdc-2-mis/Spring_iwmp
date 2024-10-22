package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import app.bean.Login;
import app.bean.StateToVillageBean;
import app.bean.User;
import app.dao.ProfileDao;
import app.dao.UserDao;
import app.daoImpl.UserDaoImpl;
import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.IwmpState;

import app.model.UserMap;
import app.model.UserReg;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired(required = true)
	UserDaoImpl userDaoimp;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProfileDao profieDao;


	//@Transactional
	public List<User> validateUser(Login login) {
		return userDaoimp.validateUser(login);
	}
	
	@Override
	//@Transactional
	public Map<String, String> getStateList() {
		
		Map<String, String> stateList=new LinkedHashMap<String, String>();
		for (IwmpState temp : userDao.getStateList()) {
			stateList.put(temp.getStCode()+"", temp.getStName());
		}
		return stateList ;
	}

	@Override
	//@Transactional
	public Map<String, String> getDistrictList(int stateCode) {
		Map<String, String> districtList=new LinkedHashMap<String, String>();
		for (IwmpDistrict temp : userDao.getDistrictList(stateCode)) {
			districtList.put(temp.getDcode()+"", temp.getDistName());
		}
		return districtList ;
	}

	
	@Override
	//@Transactional  
	public void saveUserReg(UserReg userReg) 
	{
		userDao.saveUserReg(userReg);
	}

	@Override
	//@Transactional  
	public void register(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//@Transactional  
	public Map<String, String> getProjectList(int stateCode, int distCode) {
		
		Map<String, String> projList=new LinkedHashMap<String, String>();
		for (IwmpMProject temp : userDao.getProjectList(stateCode, distCode)) 
		{
			projList.put(temp.getProjectId()+"", temp.getProjName());
		}
		return projList ;
	}

	/*
	 * @Override
	 * 
	 * @Transactional public void saveProfile(UserReg userReg) {
	 * 
	 * profieDao.saveProfile(userReg); }
	 */
	
	@Override
	//@Transactional
	public Map<String, String> getUserProjectList(String stateCode, String dist) {
		Map<String, String> projList=new LinkedHashMap<String, String>();
		for (IwmpMProject temp : userDao.getUserProjectList(stateCode, dist)) 
		{
			projList.put(temp.getProjectId()+"", temp.getProjName());
		}
		return projList ;
	}

	@Override
	public Map<String, String> getUseridList(String st_code, String distCode) {
		Map<String, String> userList=new LinkedHashMap<String, String>();
		for (UserReg temp : userDao.getUseridList(st_code, distCode)) {
			userList.put(temp.getRegId()+"", temp.getUserId());
		}
		return userList;
	}

	

	
	
	
}
