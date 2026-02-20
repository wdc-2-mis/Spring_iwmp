package app.punarutthan.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.punarutthan.controller.WatershedPunarutthanBean;

@Service("WatershedPunarutthanService")
public interface WatershedPunarutthanService {

	LinkedHashMap<String, String> getProjectListMis( int distCodelgd);
	
	LinkedHashMap<String, Integer> getPunarutthanVillage(String pjcd);
	
	LinkedHashMap<Integer, String> getStructureListMis();
	
	String saveWatershedPunarutthanPlan(WatershedPunarutthanBean userfileup, HttpSession session);
	
	String getExistingPunarutthanPlanVillageCodes(Integer vCode);
	
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanDraft(HttpSession session, int distcd, int stcd);
	
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanComplete(HttpSession sess, int distcd, int stcd);
	
	String deletePunarutthanPlanDetails(List<Integer> assetid, String userid);
	
	String completePunarutthanPlanDetails(List<Integer> assetid, String userid);
	
	List<WatershedPunarutthanBean> getWatershedPunarutthanPlanImpl(Integer plan_id);
	
	String saveWatershedPunarutthanImplementation(WatershedPunarutthanBean userfileup, HttpSession session);
	
	List<WatershedPunarutthanBean> getPunarutthanDraftImplementation(HttpSession sess, int distcd, int stcd);
	
	List<WatershedPunarutthanBean> getPunarutthanCompleteImplementation(HttpSession sess, int distcd, int stcd);
	
	String deletePunarutthanImplementation(List<Integer> assetid, String userid);
	
	String completePunarutthanImplementation(List<Integer> assetid, String userid);
	
	List<WatershedPunarutthanBean> punarutthanRptStData();
	
	List<WatershedPunarutthanBean> punarutthanRptDistData(Integer stcd);
	
	List<WatershedPunarutthanBean> punarutthanRptProjData(Integer dcode);
	
	List<WatershedPunarutthanBean> punarutthanRptProjDetailData(String pcode);
	
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanCompletetoImpl(HttpSession sess, int distcd, int stcd);
	
	List<String> getImageWatershedPunarutthanPlan(Integer planid);
	
}
