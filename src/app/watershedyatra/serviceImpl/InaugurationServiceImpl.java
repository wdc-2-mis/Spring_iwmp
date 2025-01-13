package app.watershedyatra.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.daoImpl.InaugurationDaoImpl;
import app.watershedyatra.service.InaugurationService;

@Service("InaugurationService")
public class InaugurationServiceImpl implements InaugurationService {
	
	@Autowired
	InaugurationDaoImpl dao;
	
	public String saveInauguration(InaugurationBean userfileup, HttpSession session) {
		return dao.saveInauguration(userfileup, session);
	}

}
