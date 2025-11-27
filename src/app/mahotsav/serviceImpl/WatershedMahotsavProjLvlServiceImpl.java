package app.mahotsav.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.bean.WatershedMahotsavProjectLevelBean;
import app.mahotsav.dao.WatershedMahotsavProjLvlDao;
import app.mahotsav.model.WatershedMahotsavProjectLevel;
import app.mahotsav.service.WatershedMahotsavProjLvlService;

@Service
public class WatershedMahotsavProjLvlServiceImpl implements WatershedMahotsavProjLvlService {

	@Autowired
	WatershedMahotsavProjLvlDao dao;
	
	@Override
	public String saveMahotsavProjLvlDetails(WatershedMahotsavProjectLevelBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveMahotsavProjLvlDetails(userfileup, session);
	}

	@Override
	public List<WatershedMahotsavProjectLevelBean> getWatershedMahotsavAtProjLvl(Integer stcode, String loginId) {
		// TODO Auto-generated method stub
		return dao.getWatershedMahotsavAtProjLvl(stcode,loginId);
	}

	@Override
	public List<WatershedMahotsavProjectLevelBean> getComWatershedMahotsavAtProjLvl(Integer stcode, String loginId) {
		// TODO Auto-generated method stub
		return dao.getComWatershedMahotsavAtProjLvl(stcode, loginId);
	}

	@Override
	public String completeMahotsavProjLvlDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.completeMahotsavProjLvlDetails(assetid, userid);
	}

	@Override
	public List<WatershedMahotsavProjectLevelBean> getBlksWiseWatershedMahotsavAtProjLvl(List<Integer> dcode,
			String loginId) {
		// TODO Auto-generated method stub
		return dao.getBlksWiseWatershedMahotsavAtProjLvl(dcode, loginId);
	}

	@Override
	public String deleteMahotsavProjLvlDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.deleteMahotsavProjLvlDetails(assetid, userid);
	}

	@Override
	public List<WatershedMahotsavProjectLevelBean> getWatershedMahotsavProjLvlDtlForEdit(Integer id) {
		// TODO Auto-generated method stub
		return dao.getWatershedMahotsavProjLvlDtlForEdit(id);
	}

	@Override
	public String updateMahotsavProjLvlDetails(WatershedMahotsavProjectLevelBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.updateMahotsavProjLvlDetails(userfileup, session);
	}

}
