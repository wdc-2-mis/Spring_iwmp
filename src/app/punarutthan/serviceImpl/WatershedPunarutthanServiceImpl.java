package app.punarutthan.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.punarutthan.controller.WatershedPunarutthanBean;
import app.punarutthan.dao.WatershedPunarutthanDao;
import app.punarutthan.model.Wdcpmksy1ProjectDetail;
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
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanDraft(HttpSession session, int distcd, int stcd) {
		// TODO Auto-generated method stub
		return dao.getWatershedPunarutthanPlanDraft(session, distcd, stcd);
	}

	@Override
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanComplete(HttpSession sess, int distcd, int stcd) {
		// TODO Auto-generated method stub
		return dao.getWatershedPunarutthanPlanComplete(sess, distcd, stcd);
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
	public List<WatershedPunarutthanBean> getPunarutthanDraftImplementation(HttpSession session, int distcd, int stcd) {
		// TODO Auto-generated method stub
		return dao.getPunarutthanDraftImplementation(session, distcd, stcd);
	}

	@Override
	public List<WatershedPunarutthanBean> getPunarutthanCompleteImplementation(HttpSession sess, int distcd, int stcd) {
		// TODO Auto-generated method stub
		return dao.getPunarutthanCompleteImplementation(sess, distcd, stcd);
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
	
	@Override
	public List<WatershedPunarutthanBean> punarutthanRptProjDetailData(String pcode) {
		return dao.punarutthanRptProjDetailData(pcode);
	}

	@Override
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanCompletetoImpl(HttpSession sess, int distcd, int stcd) {
		// TODO Auto-generated method stub
		return dao.getWatershedPunarutthanPlanCompletetoImpl(sess, distcd, stcd);
	}

	@Override
	public List<String> getImageWatershedPunarutthanPlan(Integer planid) {
		// TODO Auto-generated method stub
		return dao.getImageWatershedPunarutthanPlan(planid);
	}

	@Override
	public Map<String, String> getDistrictList(int stateCode) {
		Map<String, String> districtList=new LinkedHashMap<String, String>();
		for (IwmpDistrict temp : dao.getDistrictList(stateCode)) {
			districtList.put(temp.getDistrictCodelgd()+"", temp.getDistName());
		}
		return districtList ;
	}

	@Override
	public Map<String, String> getProjectList(int stateCode, int distCode) {
		
		Map<String, String> projList=new LinkedHashMap<String, String>();
		for (Wdcpmksy1ProjectDetail temp : dao.getProjectList(stateCode, distCode)) 
		{
			projList.put(temp.getProjectCd()+"", temp.getProjName());
		}
		return projList ;
	}

	@Override
	public List<WatershedPunarutthanBean> getUnfreezePunarutthan(int dist, String projcd, String punatuthan) {
		// TODO Auto-generated method stub
		return dao.getUnfreezePunarutthan(dist, projcd, punatuthan);
	}

	@Override
	public String unfreezeWatershedPunarutthan(List<Integer> assetid, String typeutthan) {
		// TODO Auto-generated method stub
		return dao.unfreezeWatershedPunarutthan(assetid, typeutthan);
	}
	

}
