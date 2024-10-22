package app.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.bean.TarAchProjOutcomeBean;

@Service("TarAchProjOutcomeService")
public interface TarAchProjOutcomeService {

	
	LinkedHashMap<Integer, String> getFromYearFortarAchProjOutcome(Integer pCode);
    LinkedHashMap<Integer, String> getToYearFortarAchProjOutcome(Integer fromYear, String projId);
    List<TarAchProjOutcomeBean> getToYearForPhysicalActionPlanReport(Integer fromYear, Integer toYear,Integer project, Integer state, Integer district);
	List<TarAchProjOutcomeBean> getfYear();
	List<TarAchProjOutcomeBean> getyList();
	
	
}
