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



	@Override
	public boolean checkgrampanchayat(Integer gramCode, String preyatraType) {
		
		return dao.checkgrampanchayat(gramCode, preyatraType);
	}



	@Override
	public boolean checkVillageStatus(Integer vCode, String preyatraType) {
		return dao.checkVillageStatus(vCode, preyatraType);
	}



	@Override
	public String deleteWatershedYatraDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.deleteWatershedYatraDetails(  assetid, userid);
	}

	@Override
	public void deletePreYatraPrep(Integer prepids, String photo1, String photo2) {
		dao.deletePreYatraPrep(prepids, photo1, photo2);
		
	}



	@Override
	public String deleteMulPreYatraPrep(List<String> prepid, List<String> photos, String userid) {
		// TODO Auto-generated method stub
		return dao.deleteMulPreYatraPrep(prepid, photos, userid);
	}



	@Override
	public String completeMulPreYatraPrep(List<String> prepids) {
		// TODO Auto-generated method stub
		return dao.completeMulPreYatraPrep(prepids);
	}



	@Override
	public List<PreYatraPreparationBean> getpreyatracompleteRecord(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getpreyatracompleteRecord(stcd);
	}
	
	
	
	@Override
	public String completeWatershedYatraDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.completeWatershedYatraDetails(assetid, userid);
	}



	@Override
	public List<WatershedYatraBean> getWatershedYatraListcomplete(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraListcomplete(stcd);
	}



	@Override
	public List<WatershedYatraBean> getWatershedYatraEditList(Integer wcdid) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraEditList( wcdid);
	}



	@Override
	public String updateWatershedYatraAtVillage(WatershedYatraBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.updateWatershedYatraAtVillage(userfileup, session);
	}



	@Override
	public void updatePreYatraPrep(Integer prepid, Integer noOfParticipant) {
		 dao.updatePreYatraPrep(prepid, noOfParticipant);;
		
	}



	@Override
	public List<WatershedYatraBean> getWatershedYatraPIAList(Integer stcd, String loginId) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraPIAList(stcd, loginId);
	}



	@Override
	public List<WatershedYatraBean> getWatershedYatraPIAListcomplete(Integer stcd, String loginId) {
		// TODO Auto-generated method stub
		return dao.getWatershedYatraPIAListcomplete(stcd, loginId);
	}



	@Override
	public List<PreYatraPreparationBean> getpreyatraPIAsaveRecord(Integer stcd, String loginID) {
		// TODO Auto-generated method stub
		return dao.getpreyatraPIAsaveRecord(stcd, loginID);
	}



	@Override
	public List<PreYatraPreparationBean> getpreyatraPIAcompleteRecord(Integer stcd, String loginID) {
		// TODO Auto-generated method stub
		return dao.getpreyatraPIAcompleteRecord(stcd, loginID);
	}



	@Override
	public List<NodalOfficerBean> getDraftListofPIANodalOfficer(Integer stcd, String loginID) {
		// TODO Auto-generated method stub
		return dao.getDraftListofPIANodalOfficer(stcd, loginID);
	}



	@Override
	public List<NodalOfficerBean> getCompleteListofPIANodalOfficer(Integer stcd, String loginID) {
		return dao.getCompleteListofPIANodalOfficer(stcd, loginID);
	}
}
