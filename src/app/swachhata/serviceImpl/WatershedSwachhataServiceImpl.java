package app.swachhata.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

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

	@Override
	public List<WatershedSwachhataBean> getWatershedSwachhataAtProj(String loginId) {
		// TODO Auto-generated method stub
		return dao.getWatershedSwachhataAtProj(loginId);
	}

	@Override
	public String completeWatershedSwachhataDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.completeWatershedSwachhataDetails(assetid, userid);
	}

	@Override
	public String deleteWatershedSwachhataDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.deleteWatershedSwachhataDetails(assetid, userid);
	}

	@Override
	public List<String> getImageSwachhataProjLvlId(Integer swachhataid) {
		// TODO Auto-generated method stub
		return dao.getImageSwachhataProjLvlId(swachhataid);
	}

}
