package app.dao.reports;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.reports.StateWiseCurrentStatusBean;

public interface StateWiseCurrentStatusDao {
	
	LinkedHashMap<Integer,List<StateWiseCurrentStatusBean>> getStateWiseCurrentStatus();
	LinkedHashMap<Integer,List<StateWiseCurrentStatusBean>> getStateWiseCurrentStatusOther();
	LinkedHashMap<Integer,List<StateWiseCurrentStatusBean>> getStateWiseStatusOtherActivity();
	LinkedHashMap<Integer,List<StateWiseCurrentStatusBean>> getstateWiseStatusBaselNetTreatArea();
	LinkedHashMap<Integer,List<StateWiseCurrentStatusBean>> getdistWiseStatusBaselNetTreatArea(int id);
	LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getDistrictWiseCurrentStatusOther(int id);
	LinkedHashMap<Integer,List<StateWiseCurrentStatusBean>> getdistrictWiseStatusBaselNetTreatArea(int id);
	LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistrictWiseStatusOtherActivity(int id);
	LinkedHashMap<Integer,List<StateWiseCurrentStatusBean>> getStateWiseCurrentStatusPlanAchieve();
	LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistrictWiseCurrentStatusPlanAchieve(int id);
	List<StateWiseCurrentStatusBean> projWiseStatusBaselNetTreatArea(int dcode);
	List<StateWiseCurrentStatusBean> projWiseCurrentStatusOther(int dcode);
	List<StateWiseCurrentStatusBean> projWiseStatusBaselArea(int dcode);
	List<StateWiseCurrentStatusBean> projWiseStatusofOtherActivity(int dcode);
	List<StateWiseCurrentStatusBean> getprojWiseCurrentStatusPlanAchieve(int dcode);
}
