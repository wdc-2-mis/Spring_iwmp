package app.watershedyatra.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.PreYatraPrepBean;
import app.watershedyatra.bean.PreYatraPreparationBean;
import app.watershedyatra.bean.WatershedYatraBean;


@Service("WatershedYatraService")
public interface WatershedYatraService {
	
	LinkedHashMap<Integer,String> getDistrictList(int stcode);
	
	LinkedHashMap<String, Integer> getWatershedYatraBlock(Integer distcd);
	
	LinkedHashMap<String, Integer> getWatershedYatraGPs(Integer blkCode);
	
	LinkedHashMap<String, Integer> getWatershedYatraVillage(Integer gpCode);
	
	String saveNodalOfficerLMS(String level, String state, Integer district, Integer block, String name, 
			String designation, String mob, String email, HttpSession session);
	
	List<NodalOfficerBean> getDraftListofNodalOfficer(Integer stcd);
	List<NodalOfficerBean> getCompleteListofNodalOfficer(Integer stcd);
	String completeApproveNodalOfficer(List<Integer>  assetid, String userid);
	String deleteApproveNodalOfficer(List<Integer>  assetid, String userid);

	public String saveWatershedYatraVillageupload(WatershedYatraBean userfileup, HttpSession session);
	
	LinkedHashMap<Integer, String> getCultActivity();

	List<WatershedYatraBean> getWatershedYatraList(Integer stcd);
	
	String getExistingWatershedYatraVillageCodes(Integer villageCode);

	List<String> getImagesWatershedYatraId(Integer watershedYatraId);

	String savePreYatraPrep(PreYatraPrepBean preYatraPrep, HttpSession session, HttpServletRequest request);

	List<PreYatraPreparationBean> getpreyatrasaveRecord(Integer stcd);
	
	
}
