package app.dao.reports;

import java.util.List;

import app.bean.StateToVillageBean;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;

public interface ListofStateToVillageDao {
	
	List<IwmpBlock> getBlockList(int stateCode, int distCode);
	List<IwmpGramPanchayat> getGPList(int block);
	List<StateToVillageBean> getListofStateToVill(int state, int district,  int block,  int gp, String unviewlgd, String userType);

}
