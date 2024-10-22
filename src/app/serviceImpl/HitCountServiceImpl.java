package app.serviceImpl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.HitCountDao;
import app.service.HitCountService;

@Service("HitCountService")
public class HitCountServiceImpl implements HitCountService{
	
	@Autowired
	HitCountDao dao;

	@Override
	public BigInteger getHitCount(String sessionId) {
		// TODO Auto-generated method stub
		return dao.getHitCount(sessionId);
	}

}
