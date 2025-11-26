package app.mahotsav.serviceImpl;

import java.util.List;

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

	@Override
	public List<InaugurationMahotsavBean> getregisterInaugurationDetails(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getregisterInaugurationDetails(stcd);
	}

	@Override
	public List<InaugurationMahotsavBean> getregisterInaugurationDetailsComp(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getregisterInaugurationDetailsComp(stcd);
	}

	@Override
	public String deleteMahotsavInaugurationDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.deleteMahotsavInaugurationDetails(assetid, userid);
	}

	@Override
	public String completeMahotsavInaugurationDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.completeMahotsavInaugurationDetails(assetid, userid);
	}

	@Override
	public boolean checkMahotsavInaugurationExits(Integer stCode) {
		// TODO Auto-generated method stub
		return dao.checkMahotsavInaugurationExits(stCode);
	}

	@Override
	public List<InaugurationMahotsavBean> getMahotsavInaugurationEditList(Integer inauguaration_id) {
		// TODO Auto-generated method stub
		return dao.getMahotsavInaugurationEditList(inauguaration_id);
	}

	@Override
	public String updateMahotsavInaugurationDetails(InaugurationMahotsavBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.updateMahotsavInaugurationDetails(userfileup, session);
	}

	@Override
	public List<String> getImageMahotsavInaugurationId(Integer inaugId) {
		// TODO Auto-generated method stub
		return dao.getImageMahotsavInaugurationId(inaugId);
	}
	
	
	
	

}
