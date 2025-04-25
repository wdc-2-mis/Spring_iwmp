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
	
	List<WatershedYatraBean> getWatershedYatraEditList(Integer wcdid);
	
	String getExistingWatershedYatraVillageCodes(Integer villageCode);

	List<String> getImagesWatershedYatraId(Integer watershedYatraId);

	String savePreYatraPrep(PreYatraPrepBean preYatraPrep, HttpSession session, HttpServletRequest request);

	List<PreYatraPreparationBean> getpreyatrasaveRecord(Integer stcd);

	boolean checkgrampanchayat(Integer gramCode, String preyatraType);

	boolean checkVillageStatus(Integer vCode, String preyatraType);
	
	String deleteWatershedYatraDetails(List<Integer>  assetid, String userid);
	
	void deletePreYatraPrep(Integer prepid, String photo1, String photo2);
	String deleteMulPreYatraPrep(List<String> prepid, List<String> photos, String userid);
	String completeMulPreYatraPrep(List<String> prepids);
	List<PreYatraPreparationBean> getpreyatracompleteRecord(Integer stcd);
	
	String completeWatershedYatraDetails(List<Integer>  assetid, String userid);

	List<WatershedYatraBean> getWatershedYatraListcomplete(Integer stcd);
	
	public String updateWatershedYatraAtVillage(WatershedYatraBean userfileup, HttpSession session);

	void updatePreYatraPrep(Integer prepid, Integer noOfParticipant);

	List<WatershedYatraBean> getWatershedYatraPIAList(Integer stcd, String loginId);

	List<WatershedYatraBean> getWatershedYatraPIAListcomplete(Integer stcd, String loginId);

	List<PreYatraPreparationBean> getpreyatraPIAsaveRecord(Integer stcd, String loginID);

	List<PreYatraPreparationBean> getpreyatraPIAcompleteRecord(Integer stcd, String loginID);

	List<NodalOfficerBean> getDraftListofPIANodalOfficer(Integer stcd, String loginID);

	List<NodalOfficerBean> getCompleteListofPIANodalOfficer(Integer stcd, String loginID);
	
	String getExistingWatershedYatraVillageLoction(Integer villageCode, String loc);

	LinkedHashMap<Integer,String> getJanBWorkList();

	
}
