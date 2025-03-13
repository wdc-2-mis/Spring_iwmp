package app.watershedyatra.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;
import app.watershedyatra.bean.AdminVlgBean;
import app.watershedyatra.dao.AdminVlgDao;
import app.watershedyatra.service.AdminVlgService;

@Service("AdminVlgService")
public class AdminVlgServiceImpl implements AdminVlgService {
	
	@Autowired
	AdminVlgDao dao;
	
	@Override
	public Map<String, String> getBlockList(int stateCode, int distCode) {
		Map<String, String> blockList=new LinkedHashMap<String, String>();
		for (IwmpBlock temp : dao.getBlockList(stateCode, distCode)) {
			blockList.put(temp.getBcode()+"", temp.getBlockName());
		}
		return blockList ;
	}

	@Override
	public Map<String, String> getGPList(int block) {
		Map<String, String> gpList=new LinkedHashMap<String, String>();
		for (IwmpGramPanchayat temp : dao.getGPList(block)) {
			gpList.put(temp.getGcode()+"", temp.getGramPanchayatName());
		}
		return gpList ;
	}
	
	@Override
	public Map<String, String> getVlgList(int gp) {
		Map<String, String> vlgList=new LinkedHashMap<String, String>();
		for (IwmpVillage temp : dao.getVlgList(gp)) {
			vlgList.put(temp.getVcode()+"", temp.getVillageName());
		}
		return vlgList ;
	}

	@Override
	public String saveDupWatershedYatraVlgData(String village, String villageName, HttpSession session) {
		return dao.saveDupWatershedYatraVlgData(village, villageName, session);
	}

	@Override
	public List<AdminVlgBean> getDupWatershedYatraVlgData() {
		return dao.getDupWatershedYatraVlgData();
	}

	@Override
	public String deleteVlgData(Integer watershed_id) {
		return dao.deleteVlgData(watershed_id);
	}

}
