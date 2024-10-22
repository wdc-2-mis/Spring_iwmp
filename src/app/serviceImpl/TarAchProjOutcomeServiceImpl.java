package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.TarAchProjOutcomeBean;
import app.dao.TarAchProjOutcomeDao;
import app.service.TarAchProjOutcomeService;

@Service("TarAchProjOutcomeService")
public class TarAchProjOutcomeServiceImpl implements TarAchProjOutcomeService{

	@Autowired
	TarAchProjOutcomeDao dao;
	@Override
	public LinkedHashMap<Integer, String> getFromYearFortarAchProjOutcome(Integer pCode) {
		return dao.getFromYearFortarAchProjOutcome(pCode);
	}
	@Override
	public LinkedHashMap<Integer, String> getToYearFortarAchProjOutcome(Integer fromYear, String projId) {
		return dao.getToYearFortarAchProjOutcome(fromYear, projId);
	}
	@Override
	public List<TarAchProjOutcomeBean> getToYearForPhysicalActionPlanReport(Integer fromYear, Integer toYear, Integer project, Integer state, Integer district) {
	    return dao.getToYearForPhysicalActionPlanReport(fromYear, toYear, project, state, district);
	}
	@Override
	public List<TarAchProjOutcomeBean> getfYear(){
		return dao.getfYear();
	}
	@Override
	public List<TarAchProjOutcomeBean> getyList(){
		return dao.getyList();
	}
	
}
