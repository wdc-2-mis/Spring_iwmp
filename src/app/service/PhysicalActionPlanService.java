package app.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import app.bean.HeadActivityUnitBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalTranx;

@Service("PhysicalActionPlanService")
public interface PhysicalActionPlanService {
	LinkedHashMap<Integer,String> getFinYearProjectWise(Integer projId);
	LinkedHashMap<Integer, String> getHead();
	LinkedHashMap<Integer, String> getActivity(Integer headId);
	LinkedHashMap<Integer, String> getUnit(Integer activityId);
	String saveAsDraft(String plan,String activity,Integer projectcd,Integer yearcd,String loginid);
	List<PhysicalActionPlanBean> getListofPhysicalActionPlan(Integer projectcd,Integer yearcd);
	String deleteActivityFromAnnualActionPlan(Integer aapid);
	List<PhysicalActionPlanBean> getUserToForward(Integer regId);
	String forward(Integer sentto,Integer yearcd,Integer projectcd,Integer setfrom,String remarks);
	String forwardByWCDC(Integer sentto,Integer sentfrom,Integer planid,Character action,String remarks,String userType);
	Integer getSentToByRegId(Integer regId,Character action,Integer planid,String userType);
	List<IwmpProjectPhysicalTranx> checkForAlreadyForwardedPlan(Integer yearcd,Integer projectcd,Integer regId);
	List<IwmpProjectPhysicalTranx> checkForAlreadyForwardedPlan(Integer planid,Integer regId);
	LinkedHashMap<Integer,String> getProjectByRegId(Integer regId);
	List<PhysicalActionPlanTranxBean> viewMovement(Integer regId,String userType);
	List<PhysicalActionPlanTranxBean> getPlanMovementDetails(Integer planid);
	List<IwmpProjectPhysicalAap> getPlanDetails(Integer planid);
	List<IwmpProjectPhysicalTranx> getRemarks(Integer projectcd,Integer yearcd,Integer regId);
	LinkedHashMap<Integer, String> getProjectPhysicalActionPlan(Integer dCode);
	LinkedHashMap<Integer, String> getFromYearForPhysicalActionPlanReport(Integer pCode);
	LinkedHashMap<Integer, String> getToYearForPhysicalActionPlanReport(Integer fromYear,Integer projId);
	LinkedHashMap<String, List<PhysicalActionPlanBean>> getPhysicalActionPlanReport(Integer stCode,Integer distCode,Integer projId,Integer fromYear,Integer toYear);
	List<PhysicalActionPlanBean> getPhysicalActionReport(Integer stCode,Integer distCode,Integer projId,Integer fromYear,Integer toYear);
	LinkedHashMap<String,List<String>> viewHeadActivity();
	LinkedHashMap<Integer, String> getprojectfordistrict(Integer dCode, Integer stcode);
	LinkedHashMap<Integer,String> getProjectBystate(Integer regId);
	String saveAsExisting(String plan,String activity,Integer projectcd,Integer yearcd,String loginid);
	String updateAsExisting(String plan,String activity,Integer projectcd,Integer yearcd,String loginid);
	LinkedHashMap<Integer,String> getFinYearExistPlan(Integer projId);
	List<PhysicalActionPlanTranxBean> viewCMovement(Integer regId, String userType);
	List<PhysicalActionPlanTranxBean> viewARPlan(Integer regId, String userType);
	
	LinkedHashMap<Integer,String> getFinYearMonth();
	List<PhysicalActionPlanBean> getListofHeadAndActivityDesc();
	
	List<PhysicalActionPlanBean> getListofPhysicalActionAchiev(Integer projectcd,Integer yearcd);

}
