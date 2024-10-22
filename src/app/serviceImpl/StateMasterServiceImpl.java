package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.StateMasterDao;
import app.model.IwmpState;
import app.service.StateMasterService;

@Service("stateMasterService")
public class StateMasterServiceImpl implements StateMasterService{

	@Autowired
	StateMasterDao stateMasterDao;
	
	@Override
	//@Transactional
	public LinkedHashMap<Integer, String> getAllState() {
		// TODO Auto-generated method stub
		return stateMasterDao.getAllState();
	}

	@Override
	//@Transactional
	public LinkedHashMap<Integer, String> getStateByStateCode(Integer stateCode) {
		// TODO Auto-generated method stub
		return stateMasterDao.getStateByStateCode(stateCode);
	}

	@Override
	public LinkedHashMap<Integer, String> getUserState(String loginid) {
		// TODO Auto-generated method stub
		return stateMasterDao.getUserState(loginid);
	}

	@Override
	public List<IwmpState> getStateList() {
		// TODO Auto-generated method stub
		return stateMasterDao.getStateList();
	}

	@Override
	public LinkedHashMap<Integer, String> getStateByDistCode(Integer distCode) {
		// TODO Auto-generated method stub
		return stateMasterDao.getStateByDistCode(distCode);
	}

}
