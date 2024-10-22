package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.PiaUserActivationDao;
import app.model.UserReg;
import app.service.PiaUserActivationService;

@Service("piaUserActivationService")
public class PiaUserActivationServiceImpl implements PiaUserActivationService{
	
	@Autowired
	PiaUserActivationDao dao;
	
	@Override
	//@Transactional
	public LinkedHashMap<Integer,List<UserReg>> getUserList(UserReg userReg,String stateCode) {
		// TODO Auto-generated method stub
		return dao.getUserList(userReg,stateCode);
	}
}
