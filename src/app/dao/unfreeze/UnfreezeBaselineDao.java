package app.dao.unfreeze;

import java.util.List;

import app.bean.UnfreezeBaselineSurveyDataBean;
import app.model.BlsOutMain;

public interface UnfreezeBaselineDao {
	
	List<BlsOutMain>   getBaselineVillageList(int project);
	List<UnfreezeBaselineSurveyDataBean> getUnfreezeBaselineSurveyData(Integer vill, Integer project);
	boolean unfreezeBaselineSurveyDataComplete(String villcode[]);
}
