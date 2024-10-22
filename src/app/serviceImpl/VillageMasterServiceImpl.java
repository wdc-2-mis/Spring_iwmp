package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.bean.ProjectLocationBean;
import app.dao.VillageMasterDao;
import app.model.master.IwmpVillage;
import app.service.VillageMasterService;

@Service("villageMasterService")
public class VillageMasterServiceImpl implements VillageMasterService{
	
	@Autowired
	VillageMasterDao dao;

	@Override
	public LinkedHashMap<Integer, String> getVillageBlockWise(Integer bcode) {
		// TODO Auto-generated method stub
		return dao.getVillageBlockWise(bcode);
	}

	
	@Override
	public List<ProjectLocationBean> getVillageByVillageCode(List<Integer> vcode) {
		// TODO Auto-generated method stub 
		return dao.getVillageByVillageCode(vcode);
	}


	@Override
	public List<IwmpVillage> getVillageList(int stateCode, int districtCode, int blockCode, int gpCode) {
		// TODO Auto-generated method stub
		return dao.getVillageList(stateCode, districtCode, blockCode, gpCode);
	}


	@Override
	public void updateVillageList(List<IwmpVillage> village) {
		// TODO Auto-generated method stub
		dao.updateVillageList(village);
	}


	@Override
	public List<IwmpVillage> getActiveVillageList(int stateCode, int districtCode, int blockCode, int gpCode) {
		// TODO Auto-generated method stub
		return dao.getActiveVillageList(stateCode, districtCode, blockCode, gpCode);
	}


	@Override
	public IwmpVillage findVillageVcode(Integer vcode) {
		// TODO Auto-generated method stub
		return dao.findVillageVcode(vcode);
	}


	@Override
	public Boolean updateVillageList(IwmpVillage village) {
		// TODO Auto-generated method stub
		return dao.updateVillageList(village);
	}


	@Override
	public List<IwmpVillage> getVillageList() {
		// TODO Auto-generated method stub
		return dao.getVillageList();
	}


	@Override
	public LinkedHashMap<Integer, String> getVillageOfProject(Integer projId) {
		// TODO Auto-generated method stub
		return dao.getVillageOfProject(projId);
	}




}
