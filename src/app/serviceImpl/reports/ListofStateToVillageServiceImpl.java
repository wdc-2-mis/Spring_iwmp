package app.serviceImpl.reports;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.StateToVillageBean;
import app.dao.UserDao;
import app.dao.reports.ListofStateToVillageDao;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.service.reports.ListofStateToVillageService;

@Service("ListofStateToVillageService")
public class ListofStateToVillageServiceImpl implements ListofStateToVillageService{

	@Autowired
	private ListofStateToVillageDao stvDao;	
	
	
	@Override
	public Map<String, String> getBlockList(int stateCode, int distCode) {
		Map<String, String> blockList=new LinkedHashMap<String, String>();
		for (IwmpBlock temp : stvDao.getBlockList(stateCode, distCode)) {
			blockList.put(temp.getBcode()+"", temp.getBlockName());
		}
		return blockList ;
	}

	@Override
	public Map<String, String> getGPList(int block) {
		Map<String, String> gpList=new LinkedHashMap<String, String>();
		for (IwmpGramPanchayat temp : stvDao.getGPList(block)) {
			gpList.put(temp.getGcode()+"", temp.getGramPanchayatName());
		}
		return gpList ;
	}

	@Override
	public List<StateToVillageBean> getListofStateToVill(int state, int district, int block, int gp, String unviewlgd, String userType) {
		
		return stvDao.getListofStateToVill(state, district, block, gp, unviewlgd, userType);
	}

	@Override
	public LinkedHashMap<String, Integer> getGPListWithLgdCode(int block) {
		LinkedHashMap<String, Integer> gpList=new LinkedHashMap<>();
		for (IwmpGramPanchayat temp : stvDao.getGPList(block)) {
			gpList.put(temp.getGramPanchayatName()+" ("+temp.getGramPanchayatLgdCode()+")", temp.getGcode());
		}
		return gpList ;
	}
}
