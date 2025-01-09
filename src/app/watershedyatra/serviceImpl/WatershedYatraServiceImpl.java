package app.watershedyatra.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.dao.WatershedYatraDao;
import app.watershedyatra.service.WatershedYatraService;

@Service("WatershedYatraService")
public class WatershedYatraServiceImpl implements WatershedYatraService{
	
	
	@Autowired
	WatershedYatraDao dao;
	
	

	@Override
	public LinkedHashMap<Integer, String> getDistrictList(int stcode) {
		// TODO Auto-generated method stub
		return dao.getDistrictList(stcode);
	}



	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraBlock(Integer distcd) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraBlock(distcd);
	}



	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraGPs(Integer blkCode) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraGPs(blkCode);
	}



	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraVillage(Integer gpCode) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraVillage( gpCode);
	}



	@Override
	public String saveNodalOfficerLMS(String level, String state, Integer district, Integer block, String name,
			String designation, String mob, String email, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveNodalOfficerLMS(level, state, district, block, name, designation, mob, email, session);
	}



	@Override
	public List<NodalOfficerBean> getDraftListofNodalOfficer(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getDraftListofNodalOfficer(stcd);
	}



	@Override
	public String completeApproveNodalOfficer(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.completeApproveNodalOfficer(assetid, userid);
	}



	@Override
	public String deleteApproveNodalOfficer(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.deleteApproveNodalOfficer(assetid, userid);
	}

}
