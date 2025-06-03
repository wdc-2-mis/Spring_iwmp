package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.PfmsTreasureBean;
import app.TargetAchievementQuarterBean;
import app.bean.PrayasAchievementBean;
import app.bean.reports.QuarterlyTargetBean;
import app.dao.reports.TargetAchievementQuarterDao;
import app.model.IwmpMFinYear;
import app.service.reports.TargetAchievementQuarterService;

@Service("TargetAchievementQuarterService")
public class TargetAchievementQuarterServiceImpl implements TargetAchievementQuarterService{

	
	@Autowired
	TargetAchievementQuarterDao dao;
	
	@Override
	public List<IwmpMFinYear> getFinancialYearonward21() {
		// TODO Auto-generated method stub
		return dao.getFinancialYearonward21();
	}
	
	@Override
	public List<TargetAchievementQuarterBean> getQuarterReport(Integer state, Integer year, Integer qtr) {
		// TODO Auto-generated method stub
		return dao.getQuarterReport(state, year, qtr);
	}

	@Override
	public List<PfmsTreasureBean> getStateExpenditureReport(Integer year) {
		// TODO Auto-generated method stub
		return dao.getStateExpenditureReport(year);
	}

	@Override
	public List<PfmsTreasureBean> getStateWisePFMSComponent(Integer year) {
		// TODO Auto-generated method stub
		return dao.getStateWisePFMSComponent(year);
	}

	@Override
	public List<PfmsTreasureBean> getStateWisePFMSNotComponent(Integer year) {
		// TODO Auto-generated method stub
		return dao.getStateWisePFMSNotComponent( year);
	}

	@Override
	public List<PrayasAchievementBean> finddistmonthachdata(Integer stcode, Integer finCode,Integer month) {
		return dao.finddistmonthachdata(stcode, finCode,  month);
	}

	@Override
	public List<IwmpMFinYear> getYearonward22() {
		return dao.getYearonward22();
	}


	@Override
	public List<PrayasAchievementBean> finddistWisemonthachdata(Integer stCode, Integer finCode, Integer month) {
		// TODO Auto-generated method stub
		return dao.finddistWisemonthachdata(stCode, finCode, month);
	}

	@Override
	public List<TargetAchievementQuarterBean> getDistWiseQuarterReport(Integer state, Integer year, Integer qtr) {
		// TODO Auto-generated method stub
		return dao.getDistWiseQuarterReport(state, year, qtr);
	}

	@Override
	public Integer getnoofStateProj(Integer state) {
		// TODO Auto-generated method stub
		return dao.getnoofStateProj(state);
	}
	@Override
	public List<QuarterlyTargetBean> fetchquartargetrpt(Integer userState, Integer year) {
		return dao.fetchquartargetrpt(userState, year);
	}

	@Override
	public List<TargetAchievementQuarterBean> getProjectWiseQuarterReport(Integer dist, Integer year, Integer qtr) {
		// TODO Auto-generated method stub
		return dao.getProjectWiseQuarterReport(dist, year, qtr);
	}

	@Override
	public Integer getnoofDistrictProj(Integer dcode) {
		// TODO Auto-generated method stub
		return dao.getnoofDistrictProj(dcode);
	}

	@Override
	public List<PrayasAchievementBean> findprojWisemonthachdata(Integer dcode, Integer finCode, Integer month) {
		// TODO Auto-generated method stub
		return dao.findprojWisemonthachdata(dcode, finCode, month);
	}



	

}
