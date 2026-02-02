package app.punarutthan.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.punarutthan.controller.WatershedPunarutthanBean;

public interface WatershedPunarutthanDao {
	
	LinkedHashMap<String,String> getProjectListMis( int distCodelgd);
	LinkedHashMap<String, Integer> getPunarutthanVillage(String pjcd);
	LinkedHashMap<Integer, String> getStructureListMis();
	String saveWatershedPunarutthanPlan(WatershedPunarutthanBean userfileup, HttpSession session);
	String getExistingPunarutthanPlanVillageCodes(Integer vCode);
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanDraft(String userid);
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanComplete(String userid);
	String deletePunarutthanPlanDetails(List<Integer> assetid, String userid);
	String completePunarutthanPlanDetails(List<Integer> assetid, String userid);
	List<WatershedPunarutthanBean> getWatershedPunarutthanPlanImpl(Integer plan_id);
	String saveWatershedPunarutthanImplementation(WatershedPunarutthanBean userfileup, HttpSession session);
	List<WatershedPunarutthanBean> getPunarutthanDraftImplementation(String userid);
	List<WatershedPunarutthanBean> getPunarutthanCompleteImplementation(String userid);
	
	String deletePunarutthanImplementation(List<Integer> assetid, String userid);
	String completePunarutthanImplementation(List<Integer> assetid, String userid);
	
	List<WatershedPunarutthanBean> punarutthanRptStData();
	
	List<WatershedPunarutthanBean> punarutthanRptDistData(Integer stcd);
	
	List<WatershedPunarutthanBean> punarutthanRptProjData(Integer dcode);

}
