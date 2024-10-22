package app.service.reports;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.bean.StateToVillageBean;

@Service("ListofStateToVillageService")
public interface ListofStateToVillageService {
	
	Map<String, String>  getBlockList(int stateCode, int distCode);
	Map<String, String>  getGPList( int block);
	List<StateToVillageBean> getListofStateToVill(int state, int district,  int block,  int gp, String unviewlgd, String userType);
	LinkedHashMap<String, Integer>  getGPListWithLgdCode( int block);
	

}
