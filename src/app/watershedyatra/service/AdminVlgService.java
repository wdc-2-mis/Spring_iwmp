package app.watershedyatra.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.watershedyatra.bean.AdminVlgBean;

@Service("AdminVlgService")
public interface AdminVlgService {
	
	Map<String, String>  getBlockList(int stateCode, int distCode);
	
	Map<String, String>  getGPList(int block);
	
	Map<String, String>  getVlgList(int gp);

	String saveDupWatershedYatraVlgData(String village, String villageName, HttpSession session);

	List<AdminVlgBean> getDupWatershedYatraVlgData();

	String deleteVlgData(Integer watershed_id);
	
}
