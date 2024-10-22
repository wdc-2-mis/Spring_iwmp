package app.serviceImpl;


import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.TargetAchDashboardBean;
import app.bean.reports.DolrDashboardBean;
import app.dao.DashBoardDao;
import app.service.DashBoardService;

@Service("dashBoardService")
public class DashBoardServiceImpl implements DashBoardService{

	@Autowired
	DashBoardDao dashBoardDao;
	
	@Override
	public LinkedHashMap<String,Integer> getAllProject() {
		return dashBoardDao.getAllProject();
	}
	
	public LinkedHashMap<String,Integer> getAllProject(Integer central,Integer state) {
		return dashBoardDao.getAllProject(central,state);
	}
	
	@Override
	public LinkedHashMap<String,Integer> getAllProjectline() {
		return dashBoardDao.getAllProjectline();
	}

	@Override
	public LinkedHashMap<String, BigDecimal> getTopTenStateBlsCompleted() {
		return dashBoardDao.getTopTenStateBlsCompleted();
	}

	@Override
	public LinkedHashMap<String, BigDecimal> getTopTenProjLocCmpltnPrcntg() {
		return dashBoardDao.getTopTenProjLocCmpltnPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getTopTenClassLandPrcntg() {
		return dashBoardDao.getTopTenClassLandPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getCompClassLandAchPrcntg() {
		return dashBoardDao.getCompClassLandAchPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getCompIrriStatusPrcntg() {
		return dashBoardDao.getCompIrriStatusPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getCompIrriStatusAchPrcntg() {
		return dashBoardDao.getCompIrriStatusAchPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getCompNoOfCropPrcntg() {
		return dashBoardDao.getCompNoOfCropPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getCompNoOfCropAchPrcntg() {
		return dashBoardDao.getCompNoOfCropAchPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getStwiseTotalExpPrcntg() {
		return dashBoardDao.getStwiseTotalExpPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompLandPrcntg() {
		return dashBoardDao.getTopTenStNonCompLandPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompLandAchPrcntg() {
		return dashBoardDao.getTopTenStNonCompLandAchPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompIrriPrcntg() {
		return dashBoardDao.getTopTenStNonCompIrriPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompIrriAchPrcntg() {
		return dashBoardDao.getTopTenStNonCompIrriAchPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompCropPrcntg() {
		return dashBoardDao.getTopTenStNonCompCropPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompCropAchPrcntg() {
		return dashBoardDao.getTopTenStNonCompCropAchPrcntg();
	}

	@Override
	public List<DolrDashboardBean> getStwiseSancExpndtrRecpt() {
		return dashBoardDao.getStwiseSancExpndtrRecpt();
	}

	@Override
	public List<DolrDashboardBean> getdashboardtarget(String id) {
		// TODO Auto-generated method stub
		return dashBoardDao.getdashboardtarget(id);
	}

	@Override
	public List<DolrDashboardBean> getHomePagetarget(String id) {
		
		return dashBoardDao.getHomePagetarget(id);
	}

	@Override
	public List<DolrDashboardBean> getCircleDistricttarget(String id, String activity) {
		return dashBoardDao.getCircleDistricttarget(id, activity);
	}

	@Override
	public LinkedHashMap<Integer, List<TargetAchDashboardBean>> gettarachcompdata() {
		return dashBoardDao.gettarachcompdata();
	}

	@Override
	public LinkedHashMap<Integer, List<TargetAchDashboardBean>> getheadDescFinYearState(String finyear, String state) {
		return dashBoardDao.getheadDescFinYearState(finyear, state);
	}

	@Override
	public  List<TargetAchDashboardBean> getMonthWiseAch(Integer finYear, Integer state) {
		return dashBoardDao.getMonthWiseAch(finYear, state);
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch2(Integer finYear, Integer state) {
		return dashBoardDao.getMonthWiseAch2(finYear, state);
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch3(Integer finYear, Integer state) {
		return  dashBoardDao.getMonthWiseAch3(finYear, state);
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch4(Integer finYear, Integer state) {
		return  dashBoardDao.getMonthWiseAch4(finYear, state);
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch5(Integer finYear, Integer state) {
		return  dashBoardDao.getMonthWiseAch5(finYear, state);
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch6(Integer finYear, Integer state) {
		return  dashBoardDao.getMonthWiseAch6(finYear, state);
	}
	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch7(Integer finYear, Integer state) {
		return  dashBoardDao.getMonthWiseAch7(finYear, state);
	}

	@Override
	public List<TargetAchDashboardBean> gettarachdata(Integer finYear, Integer state) {
		// TODO Auto-generated method stub
		return dashBoardDao.gettarachdata(finYear, state);
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getTopStates(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates2(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getTopStates2(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates3(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getTopStates3(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates4(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getTopStates4(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates5(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getTopStates5(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates6(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getTopStates6(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates7(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getTopStates7(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getClassificationBase() {
		// TODO Auto-generated method stub
		return dashBoardDao.getClassificationBase();
	}

	@Override
	public List<TargetAchDashboardBean> getClassificationDate() {
		// TODO Auto-generated method stub
		return dashBoardDao.getClassificationDate();
	}

	@Override
	public List<TargetAchDashboardBean> getIrrigationBase() {
		// TODO Auto-generated method stub
		return dashBoardDao.getIrrigationBase();
	}

	@Override
	public List<TargetAchDashboardBean> getIrrigationonDate() {
		// TODO Auto-generated method stub
		return dashBoardDao.getIrrigationonDate();
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getBelowStates(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates2(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getBelowStates2(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates3(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getBelowStates3(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates4(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getBelowStates4(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates5(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getBelowStates5(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates6(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getBelowStates6(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates7(Integer finYear) {
		// TODO Auto-generated method stub
		return dashBoardDao.getBelowStates7(finYear);
	}

	@Override
	public List<TargetAchDashboardBean> getProgressiveData(Integer headCode, Integer stCode) {
		// TODO Auto-generated method stub
		return dashBoardDao.getProgressiveData(headCode, stCode);
	}

	@Override
	public List<TargetAchDashboardBean> getstatewiseProgressive(Integer headCode) {
		// TODO Auto-generated method stub
		return dashBoardDao.getstatewiseProgressive(headCode);
	}

	@Override
	public List<TargetAchDashboardBean> getdistrictwiseProgressive(Integer headCode, Integer stCode) {
		// TODO Auto-generated method stub
		return dashBoardDao.getdistrictwiseProgressive(headCode, stCode);
	}

	@Override
	public List<TargetAchDashboardBean> getDistrictProgressiveData(Integer headcode, Integer dcode) {
		// TODO Auto-generated method stub
		return dashBoardDao.getDistrictProgressiveData(headcode, dcode);
	}

	@Override
	public List<TargetAchDashboardBean> getheadname(Integer headCode) {
		// TODO Auto-generated method stub
		return dashBoardDao.getheadname(headCode);
	}

	@Override
	public List<TargetAchDashboardBean> getdistrictfirstRecords(Integer headCode, Integer stCode) {
		// TODO Auto-generated method stub
		return dashBoardDao.getdistrictfirstRecords(headCode, stCode);
	}

	@Override
	public List<TargetAchDashboardBean> getDistrictNameData(Integer dcode) {
		// TODO Auto-generated method stub
		return dashBoardDao.getDistrictNameData(dcode);
	}

	@Override
	public List<TargetAchDashboardBean> getOwnershipBase() {
		// TODO Auto-generated method stub
		return dashBoardDao.getOwnershipBase();
	}

	@Override
	public List<TargetAchDashboardBean> getOwnershipDate() {
		// TODO Auto-generated method stub
		return dashBoardDao.getOwnershipDate();
	}

	@Override
	public List<TargetAchDashboardBean> getNoofCropBase() {
		// TODO Auto-generated method stub
		return dashBoardDao.getNoofCropBase();
	}

	@Override
	public List<TargetAchDashboardBean> getNoofCropDate() {
		// TODO Auto-generated method stub
		return dashBoardDao.getNoofCropDate();
	}

	@Override
	public List<TargetAchDashboardBean> getStateNameData(Integer scode) {
		// TODO Auto-generated method stub
		return dashBoardDao.getStateNameData(scode);
	}

	@Override
	public List<TargetAchDashboardBean> getStateProgressiveData(Integer headcode, Integer scode) {
		// TODO Auto-generated method stub
		return dashBoardDao.getStateProgressiveData(headcode, scode);
	}
	
	
}
