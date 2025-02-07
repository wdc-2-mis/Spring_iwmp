package app.watershedyatra.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.PreYatraPrepBean;
import app.watershedyatra.bean.PreYatraPreparationBean;
import app.watershedyatra.bean.WatershedYatraBean;
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

	@Override
	public String saveWatershedYatraVillageupload(WatershedYatraBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveWatershedYatraVillageupload(userfileup, session);
	}



	@Override
	public List<NodalOfficerBean> getCompleteListofNodalOfficer(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getCompleteListofNodalOfficer(stcd);
	}
	
	@Override
	public LinkedHashMap<Integer, String> getCultActivity() {
  		return dao.getCultActivity();
	}



	@Override
	public List<WatershedYatraBean> getWatershedYatraList(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraList(stcd);
	}



	@Override
	public String getExistingWatershedYatraVillageCodes(Integer villageCode) {
		// TODO Auto-generated method stub
		return dao.getExistingWatershedYatraVillageCodes(villageCode);
	}



	@Override
	public List<String> getImagesWatershedYatraId(Integer watershedYatraId) {
		
		return dao.getImagesWatershedYatraId(watershedYatraId);
	}



	@Override
	public String savePreYatraPrep(PreYatraPrepBean preYatraPrep, HttpSession session, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.savePreYatraPrep(preYatraPrep, session, request);
	}



	@Override
	public List<PreYatraPreparationBean> getpreyatrasaveRecord(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getpreyatrasaveRecord(stcd);
	}

}
