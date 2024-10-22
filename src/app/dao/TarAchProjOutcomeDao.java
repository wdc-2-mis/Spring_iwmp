package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.TarAchProjOutcomeBean;

public interface TarAchProjOutcomeDao {

	LinkedHashMap<Integer, String> getFromYearFortarAchProjOutcome(Integer pCode);
    LinkedHashMap<Integer, String> getToYearFortarAchProjOutcome(Integer fromYear, String projId);
    List<TarAchProjOutcomeBean> getToYearForPhysicalActionPlanReport(Integer fromYear, Integer toYear,Integer project, Integer state, Integer district);
	List<TarAchProjOutcomeBean> getfYear();
	List<TarAchProjOutcomeBean> getyList();
	

}
