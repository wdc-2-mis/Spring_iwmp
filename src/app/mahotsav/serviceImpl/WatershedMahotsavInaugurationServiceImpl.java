package app.mahotsav.serviceImpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.dao.WatershedMahotsavInaugurationDao;
import app.mahotsav.service.WatershedMahotsavInaugurationService;

@Service("WatershedMahotsavInaugurationService")
public class WatershedMahotsavInaugurationServiceImpl implements WatershedMahotsavInaugurationService{
	
	@Autowired
	WatershedMahotsavInaugurationDao dao;

	@Override
	public String saveMahotsavInaugurationDetails(InaugurationMahotsavBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveMahotsavInaugurationDetails(userfileup, session);
	}
	
	
	
	

}
