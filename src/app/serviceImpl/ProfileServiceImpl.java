package app.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.ProfileBean;
import app.bean.UserBean;
import app.dao.ProfileDao;
import app.model.UserReg;
import app.service.ProfileService;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	private ProfileDao dao ;

	@Override
	//@Transactional
	public List<UserReg> getUserDetail(Integer regid) {
		
		return dao.getUserDetail(regid);
	}

	@Override
	public List<ProfileBean> getMapstate(Integer regid, String usertype) {
		// TODO Auto-generated method stub
		return dao.getMapstate(regid, usertype);
	}
	
	@Override
	//@Transactional
	public Integer saveProfile(UserBean userReg) {
	
		return dao.saveProfile(userReg);
	}

	@Override
	public UserReg getUserDetailasUserReg(Integer regId) {
		// TODO Auto-generated method stub
		return dao.getUserDetailasUserReg(regId);
	}
	
	@Override
	public Integer saveProfileHistory(Integer regid,String moveBy) {
		
		return dao.saveProfileHistory(regid,moveBy);
	}

	@Override
	public Integer deleteInsertDolrState(Integer regid, String[] stateList) {
		
		return dao.deleteInsertDolrState(regid, stateList);
	}

	@Override
	public List<UserReg> getNewUserDetail(Integer regid) {
		return dao.getNewUserDetail( regid);
	}

	@Override
	public List<ProfileBean> getPunarutthanStateDist(Integer regid) {
		// TODO Auto-generated method stub
		return dao.getPunarutthanStateDist(regid);
	}
	

}