package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.HeadActivityUnitBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.dao.PhysicalActionPlanDao;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalTranx;
import app.service.PhysicalActionPlanService;
@Service("PhysicalActionPlanService")
public class PhysicalActionPlanServiceImpl implements PhysicalActionPlanService{
	
	@Autowired
	PhysicalActionPlanDao dao;

	@Override
	public LinkedHashMap<Integer, String> getFinYearProjectWise(Integer projId) {
		// TODO Auto-generated method stub
		return dao.getFinYearProjectWise(projId);
	}

	@Override
	public LinkedHashMap<Integer, String> getHead() {
		// TODO Auto-generated method stub
		return dao.getHead();
	}

	@Override
	public LinkedHashMap<Integer, String> getActivity(Integer headId) {
		// TODO Auto-generated method stub
		return dao.getActivity(headId);
	}

	@Override
	public LinkedHashMap<Integer, String> getUnit(Integer activityId) {
		// TODO Auto-generated method stub
		return dao.getUnit(activityId);
	}

	@Override
	public String saveAsDraft(String plan,String activity,Integer projectcd,Integer yearcd,String loginid) {
		// TODO Auto-generated method stub
		return dao.saveAsDraft(plan,activity,projectcd,yearcd,loginid);
	}

	@Override
	public List<PhysicalActionPlanBean> getListofPhysicalActionPlan(Integer projectcd, Integer yearcd) {
		// TODO Auto-generated method stub
		return dao.getListofPhysicalActionPlan(projectcd, yearcd);
	}
	
	@Override
	public String deleteActivityFromAnnualActionPlan(Integer aapid) {
		// TODO Auto-generated method stub
		return dao.deleteActivityFromAnnualActionPlan(aapid);
	}
	
	@Override
	public List<PhysicalActionPlanBean> getUserToForward(Integer regId) {
		// TODO Auto-generated method stub
		return dao.getUserToForward(regId);
	}

	@Override
	public String forward(Integer sentto, Integer yearcd, Integer projectcd,Integer sentfrom,String remarks) {
		// TODO Auto-generated method stub
		return dao.forward(sentto,yearcd,projectcd,sentfrom,remarks);
	}

	@Override
	public List<IwmpProjectPhysicalTranx> checkForAlreadyForwardedPlan(Integer yearcd, Integer projectcd,Integer regId) {
		// TODO Auto-generated method stub
		return dao.checkForAlreadyForwardedPlan(yearcd,projectcd,regId);
	}
	
	@Override
	public List<IwmpProjectPhysicalTranx> checkForAlreadyForwardedPlan(Integer planid,Integer regId) {
		// TODO Auto-generated method stub
		return dao.checkForAlreadyForwardedPlan(planid,regId);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectByRegId(Integer regId) {
		// TODO Auto-generated method stub
		return dao.getProjectByRegId(regId);
	}

	@Override
	public List<PhysicalActionPlanTranxBean> viewMovement(Integer regId,String userType) {
		// TODO Auto-generated method stub
		return dao.viewMovement(regId,userType);
	}
	
	@Override
	public List<PhysicalActionPlanTranxBean> getPlanMovementDetails(Integer planid) {
		// TODO Auto-generated method stub
		return dao.getPlanMovementDetails(planid);
	}

	@Override
	public List<IwmpProjectPhysicalAap> getPlanDetails(Integer planid) {
		// TODO Auto-generated method stub
		return dao.getPlanDetails(planid);
	}

	@Override
	public String forwardByWCDC(Integer sentto, Integer sentfrom, Integer planid, Character action, String remarks,String userType) {
		// TODO Auto-generated method stub
		return dao.forwardByWCDC(sentto,sentfrom,planid,action,remarks,userType);
	}

	@Override
	public Integer getSentToByRegId(Integer regId, Character action,Integer planid,String userType) {
		// TODO Auto-generated method stub
		return dao.getSentToByRegId(regId,action,planid,userType);
	}

	@Override
	public List<IwmpProjectPhysicalTranx> getRemarks(Integer projectcd, Integer yearcd,Integer regId) {
		// TODO Auto-generated method stub
		return dao.getRemarks(projectcd,yearcd,regId);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectPhysicalActionPlan(Integer dCode) {
		// TODO Auto-generated method stub
		return dao.getProjectPhysicalActionPlan(dCode);
	}

	@Override
	public LinkedHashMap<Integer, String> getFromYearForPhysicalActionPlanReport(Integer pCode) {
		// TODO Auto-generated method stub
		return dao.getFromYearForPhysicalActionPlanReport(pCode);
	}

	@Override
	public LinkedHashMap<Integer, String> getToYearForPhysicalActionPlanReport(Integer fromYear,Integer projId) {
		// TODO Auto-generated method stub
		return dao.getToYearForPhysicalActionPlanReport(fromYear,projId);
	}

	@Override
	public LinkedHashMap<String, List<PhysicalActionPlanBean>> getPhysicalActionPlanReport(Integer stCode, Integer distCode, Integer projId,
			Integer fromYear, Integer toYear) {
		// TODO Auto-generated method stub
		return dao.getPhysicalActionPlanReport(stCode, distCode, projId, fromYear, toYear);
	}

	@Override
	public List<PhysicalActionPlanBean> getPhysicalActionReport(Integer stCode, Integer distCode, Integer projId,
			Integer fromYear, Integer toYear) {
		// TODO Auto-generated method stub
		return dao.getPhysicalActionReport(stCode, distCode, projId, fromYear, toYear);
	}

	@Override
	public LinkedHashMap<String, List<String>> viewHeadActivity() {
		// TODO Auto-generated method stub
		return dao.viewHeadActivity();
	}

	@Override
	public LinkedHashMap<Integer, String> getprojectfordistrict(Integer dCode, Integer stcode) {
		return dao.getprojectfordistrict(dCode, stcode);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectBystate(Integer stcode) {
		// TODO Auto-generated method stub
		return dao.getProjectBystate(stcode);
	}

	@Override
	public String saveAsExisting(String plan, String activity, Integer projectcd, Integer yearcd, String loginid) {
		// TODO Auto-generated method stub
		return dao.saveAsExisting( plan, activity, projectcd, yearcd, loginid);
	}

	@Override
	public LinkedHashMap<Integer, String> getFinYearExistPlan(Integer projId) {
		// TODO Auto-generated method stub
		return dao.getFinYearExistPlan(projId);
	}

	@Override
	public String updateAsExisting(String plan, String activity, Integer projectcd, Integer yearcd, String loginid) {
		// TODO Auto-generated method stub
		return dao.updateAsExisting(plan, activity, projectcd, yearcd, loginid);
	}

	@Override
	public List<PhysicalActionPlanTranxBean> viewCMovement(Integer regId, String userType) {
		return dao.viewCMovement(regId, userType);
	}

	@Override
	public List<PhysicalActionPlanTranxBean> viewARPlan(Integer regId, String userType) {
		// TODO Auto-generated method stub
		return dao.viewARPlan(regId, userType);
	}

	@Override
	public LinkedHashMap<Integer, String> getFinYearMonth() {
		// TODO Auto-generated method stub
		return dao.getFinYearMonth();
	}

	@Override
	public List<PhysicalActionPlanBean> getListofHeadAndActivityDesc() {
		// TODO Auto-generated method stub
		return dao.getListofHeadAndActivityDesc();
	}
	
	@Override
	public List<PhysicalActionPlanBean> getListofPhysicalActionAchiev(Integer projectcd, Integer yearcd) {
		// TODO Auto-generated method stub
		return dao.getListofPhysicalActionAchiev(projectcd, yearcd);
	}

}
