package app.service.unfreeze;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.bean.UnfreezeBaselineSurveyDataBean;


@Service("UnfreezeBaselineServices")
public interface UnfreezeBaselineServices {
	
	Map<String, String>  getBaselineVillageList(int project);
	List<UnfreezeBaselineSurveyDataBean> getUnfreezeBaselineSurveyData(Integer vill, Integer project);
	boolean unfreezeBaselineSurveyDataComplete(String villcode[]);
}
