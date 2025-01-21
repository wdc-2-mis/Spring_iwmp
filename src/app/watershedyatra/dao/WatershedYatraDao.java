package app.watershedyatra.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.WatershedYatraBean;

public interface WatershedYatraDao {
	
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
}
