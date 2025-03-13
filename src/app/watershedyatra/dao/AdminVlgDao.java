package app.watershedyatra.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;
import app.watershedyatra.bean.AdminVlgBean;

public interface AdminVlgDao {
	
	List<IwmpBlock> getBlockList(int stateCode, int distCode);
	
	List<IwmpGramPanchayat> getGPList(int block);
	
	List<IwmpVillage>  getVlgList(int gp);
	
	String saveDupWatershedYatraVlgData(String village, String villageName, HttpSession session);
	
	List<AdminVlgBean> getDupWatershedYatraVlgData();
	
	String deleteVlgData(Integer watershed_id);
	
}
