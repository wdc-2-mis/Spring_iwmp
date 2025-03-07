package app.dao;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import app.bean.InagrtnAndWtrShdDashBoardBean;
import app.bean.TargetAchDashboardBean;
import app.bean.WatershedYatraDashboardChartBean;
import app.bean.WatrshdInagrtnPreYtraDashBean;
import app.bean.reports.DolrDashboardBean;

public interface DashBoardDao {

	LinkedHashMap<String, Integer> getAllProject();
	
	LinkedHashMap<String, Integer> getAllProject(Integer central,Integer state);

	LinkedHashMap<String, Integer> getAllProjectline();
	
	LinkedHashMap<String, BigDecimal> getTopTenStateBlsCompleted();
	
	LinkedHashMap<String, BigDecimal> getTopTenProjLocCmpltnPrcntg();
	
	List<DolrDashboardBean> getTopTenClassLandPrcntg();
	
	List<DolrDashboardBean> getCompClassLandAchPrcntg();
	
	List<DolrDashboardBean> getCompIrriStatusPrcntg();
	
	List<DolrDashboardBean> getCompIrriStatusAchPrcntg();
	
	List<DolrDashboardBean> getCompNoOfCropPrcntg();
	
	List<DolrDashboardBean> getCompNoOfCropAchPrcntg();
 
	List<DolrDashboardBean> getStwiseTotalExpPrcntg();
	
	List<DolrDashboardBean> getTopTenStNonCompLandPrcntg();
	
	List<DolrDashboardBean> getTopTenStNonCompLandAchPrcntg();
	
	List<DolrDashboardBean> getTopTenStNonCompIrriPrcntg();
	
	List<DolrDashboardBean> getTopTenStNonCompIrriAchPrcntg();
	
	List<DolrDashboardBean> getTopTenStNonCompCropPrcntg();
	
	List<DolrDashboardBean> getTopTenStNonCompCropAchPrcntg();
	
	List<DolrDashboardBean> getStwiseSancExpndtrRecpt();

	List<DolrDashboardBean> getdashboardtarget(String id);

	List<DolrDashboardBean> getHomePagetarget(String id);

	List<DolrDashboardBean> getCircleDistricttarget(String id, String activity);

	LinkedHashMap<Integer, List<TargetAchDashboardBean>> gettarachcompdata();

	LinkedHashMap<Integer, List<TargetAchDashboardBean>> getheadDescFinYearState(String finyear, String state);

	List<TargetAchDashboardBean> getMonthWiseAch(Integer finYear, Integer state);

	List<TargetAchDashboardBean> getMonthWiseAch2(Integer finYear, Integer state);
	
	List<TargetAchDashboardBean> getMonthWiseAch3(Integer finYear, Integer state);
	
	List<TargetAchDashboardBean> getMonthWiseAch4(Integer finYear, Integer state);
	
	List<TargetAchDashboardBean> getMonthWiseAch5(Integer finYear, Integer state);

	List<TargetAchDashboardBean> getMonthWiseAch6(Integer finYear, Integer state);
	
	List<TargetAchDashboardBean> getMonthWiseAch7(Integer finYear, Integer state);

	List<TargetAchDashboardBean> gettarachdata(Integer finYear, Integer state);

	List<TargetAchDashboardBean> getTopStates(Integer finYear);
	
	List<TargetAchDashboardBean> getTopStates2(Integer finYear);
	
	List<TargetAchDashboardBean> getTopStates3(Integer finYear);
	
	List<TargetAchDashboardBean> getTopStates4(Integer finYear);
	
	List<TargetAchDashboardBean> getTopStates5(Integer finYear);
	
	List<TargetAchDashboardBean> getTopStates6(Integer finYear);
	
	List<TargetAchDashboardBean> getTopStates7(Integer finYear);

	List<TargetAchDashboardBean> getClassificationBase();

	List<TargetAchDashboardBean> getClassificationDate();

	List<TargetAchDashboardBean> getIrrigationBase();

	List<TargetAchDashboardBean> getIrrigationonDate();

	List<TargetAchDashboardBean> getBelowStates(Integer finYear);

	List<TargetAchDashboardBean> getBelowStates2(Integer finYear);
	
	List<TargetAchDashboardBean> getBelowStates3(Integer finYear);
	
	List<TargetAchDashboardBean> getBelowStates4(Integer finYear);
	
	List<TargetAchDashboardBean> getBelowStates5(Integer finYear);
	
	List<TargetAchDashboardBean> getBelowStates6(Integer finYear);
	
	List<TargetAchDashboardBean> getBelowStates7(Integer finYear);

	List<TargetAchDashboardBean> getProgressiveData(Integer headCode, Integer stCode);

	List<TargetAchDashboardBean> getstatewiseProgressive(Integer headCode);

	List<TargetAchDashboardBean> getdistrictwiseProgressive(Integer headCode, Integer stCode);

	List<TargetAchDashboardBean> getDistrictProgressiveData(Integer headcode, Integer dcode);

	List<TargetAchDashboardBean> getheadname(Integer headCode);

	List<TargetAchDashboardBean> getdistrictfirstRecords(Integer headCode, Integer stCode);

	List<TargetAchDashboardBean> getDistrictNameData(Integer dcode);

	List<TargetAchDashboardBean> getOwnershipBase();

	List<TargetAchDashboardBean> getOwnershipDate();

	List<TargetAchDashboardBean> getNoofCropBase();

	List<TargetAchDashboardBean> getNoofCropDate();

	List<TargetAchDashboardBean> getStateNameData(Integer scode);

	List<TargetAchDashboardBean> getStateProgressiveData(Integer headcode, Integer scode);
	
	Map<String, List<WatrshdInagrtnPreYtraDashBean>> getWatrshdInagrtnPreYtraData();
	
	List<WatershedYatraDashboardChartBean> getWtrshdYtraChartData();
	
	List<InagrtnAndWtrShdDashBoardBean> getInagrtnAndWtrShdDashBoardData();
}
