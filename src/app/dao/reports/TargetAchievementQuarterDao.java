package app.dao.reports;

import java.util.List;

import app.PfmsTreasureBean;
import app.TargetAchievementQuarterBean;
import app.bean.PrayasAchievementBean;
import app.bean.reports.QuarterlyTargetBean;
import app.model.IwmpMFinYear;

public interface TargetAchievementQuarterDao {

	public List<IwmpMFinYear> getFinancialYearonward21();
	List<TargetAchievementQuarterBean> getQuarterReport(Integer state, Integer year,  Integer qtr);
	List<TargetAchievementQuarterBean> getDistWiseQuarterReport(Integer state, Integer year,  Integer qtr);
	List<TargetAchievementQuarterBean> getProjectWiseQuarterReport(Integer dist, Integer year, Integer qtr);
	List<PfmsTreasureBean> getStateExpenditureReport( Integer year);
	List<PfmsTreasureBean> getStateWisePFMSComponent(Integer year);
	List<PfmsTreasureBean> getStateWisePFMSNotComponent(Integer year);
	public List<PrayasAchievementBean> finddistmonthachdata(Integer stcode, Integer finCode, Integer month);
	public List<IwmpMFinYear> getYearonward22();
	public List<PrayasAchievementBean> finddistWisemonthachdata(Integer stCode, Integer finCode, Integer month);
	public List<PrayasAchievementBean> findprojWisemonthachdata(Integer dcode, Integer finCode, Integer month);
	
	Integer getnoofStateProj(Integer state);
	Integer getnoofDistrictProj(Integer dcode);
	public List<QuarterlyTargetBean> fetchquartargetrpt(Integer userState, Integer year);
}
