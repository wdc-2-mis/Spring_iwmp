package app.swachhata.serviceImpl;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.swachhata.controller.WatershedSwachhataBean;
import app.swachhata.dao.WatershedSwachhataDao;
import app.swachhata.service.WatershedSwachhataService;

@Service
public class WatershedSwachhataServiceImpl implements WatershedSwachhataService{

	@Autowired
	WatershedSwachhataDao dao;
	
	@Override
	public LinkedHashMap<Integer, String> getVillagebyProjIdBlock(Integer projid, Integer block) {
		
		return dao.getVillagebyProjIdBlock(projid, block);
	}

	@Override
	public String saveWatershedSwachhataDetails(WatershedSwachhataBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveWatershedSwachhataDetails(userfileup, session);
	}

}
