package app.punarutthan.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.punarutthan.controller.WatershedPunarutthanBean;
import app.punarutthan.dao.WatershedPunarutthanDao;
import app.punarutthan.service.WatershedPunarutthanService;

@Service("WatershedPunarutthanService")
public class WatershedPunarutthanServiceImpl implements WatershedPunarutthanService{
	
	@Autowired
	WatershedPunarutthanDao dao;

	@Override
	public LinkedHashMap<String, String> getProjectListMis(int distCodelgd) {
		// TODO Auto-generated method stub
		return dao.getProjectListMis(distCodelgd);
	}

	@Override
	public LinkedHashMap<String, Integer> getPunarutthanVillage(String pjcd) {
		// TODO Auto-generated method stub
		return dao.getPunarutthanVillage(pjcd);
	}

	@Override
	public LinkedHashMap<Integer, String> getStructureListMis() {
		// TODO Auto-generated method stub
		return dao.getStructureListMis();
	}

	@Override
	public String saveWatershedPunarutthanPlan(WatershedPunarutthanBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveWatershedPunarutthanPlan(userfileup, session);
	}

	@Override
	public String getExistingPunarutthanPlanVillageCodes(Integer vCode) {
		// TODO Auto-generated method stub
		return dao.getExistingPunarutthanPlanVillageCodes(vCode);
	}

	@Override
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanDraft(String userid) {
		// TODO Auto-generated method stub
		return dao.getWatershedPunarutthanPlanDraft(userid);
	}

	@Override
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanComplete(String userid) {
		// TODO Auto-generated method stub
		return dao.getWatershedPunarutthanPlanComplete(userid);
	}

	@Override
	public String deletePunarutthanPlanDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.deletePunarutthanPlanDetails(assetid, userid);
	}

	@Override
	public String completePunarutthanPlanDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.completePunarutthanPlanDetails(assetid, userid);
	}

	@Override
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanImpl(Integer plan_id) {
		// TODO Auto-generated method stub
		return dao.getWatershedPunarutthanPlanImpl(plan_id);
	}

	@Override
	public String saveWatershedPunarutthanImplementation(WatershedPunarutthanBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveWatershedPunarutthanImplementation(userfileup, session);
	}

	@Override
	public List<WatershedPunarutthanBean> getPunarutthanDraftImplementation(String userid) {
		// TODO Auto-generated method stub
		return dao.getPunarutthanDraftImplementation(userid);
	}

	@Override
	public List<WatershedPunarutthanBean> getPunarutthanCompleteImplementation(String userid) {
		// TODO Auto-generated method stub
		return dao.getPunarutthanCompleteImplementation(userid);
	}

	@Override
	public String deletePunarutthanImplementation(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.deletePunarutthanImplementation(assetid, userid);
	}

	@Override
	public String completePunarutthanImplementation(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.completePunarutthanImplementation(assetid, userid);
	}
	
	@Override
	public List<WatershedPunarutthanBean> punarutthanRptStData() {
		return dao.punarutthanRptStData();
	}
	
	@Override
	public List<WatershedPunarutthanBean> punarutthanRptDistData(Integer stcd) {
		return dao.punarutthanRptDistData(stcd);
	}
	
	@Override
	public List<WatershedPunarutthanBean> punarutthanRptProjData(Integer dcode) {
		return dao.punarutthanRptProjData(dcode);
	}
	

}
