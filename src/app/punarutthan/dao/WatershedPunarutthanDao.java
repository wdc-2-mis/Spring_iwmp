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
	

}
