package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.HeadActivityUnitBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalTranx;

public interface PhysicalActionPlanDao {
	LinkedHashMap<Integer,String> getFinYearProjectWise(Integer projId);
	LinkedHashMap<Integer, String> getHead();
	LinkedHashMap<Integer, String> getActivity(Integer headId);
	LinkedHashMap<Integer, String> getUnit(Integer activityId);
	String saveAsDraft(String plan,String activity,Integer projectcd,Integer yearcd,String loginid);
	List<PhysicalActionPlanBean> getListofPhysicalActionPlan(Integer projectcd,Integer yearcd);
	String deleteActivityFromAnnualActionPlan(Integer aapid);
	List<PhysicalActionPlanBean> getUserToForward(Integer regId);
	String forward(Integer sentto,Integer yearcd,Integer projectcd,Integer sentfrom,String remarks);
	List<IwmpProjectPhysicalTranx> checkForAlreadyForwardedPlan(Integer yearcd,Integer projectcd,Integer regId);
	List<IwmpProjectPhysicalTranx> checkForAlreadyForwardedPlan(Integer planid,Integer regId);
	LinkedHashMap<Integer,String> getProjectByRegId(Integer regId);
	List<PhysicalActionPlanTranxBean> viewMovement(Integer regId,String userType);
	List<PhysicalActionPlanTranxBean> getPlanMovementDetails(Integer planid);
	List<IwmpProjectPhysicalAap> getPlanDetails(Integer planid);
	String forwardByWCDC(Integer sentto,Integer sentfrom,Integer planid,Character action,String remarks,String userType);
	Integer getSentToByRegId(Integer regId,Character action,Integer planid,String userType);
	List<IwmpProjectPhysicalTranx> getRemarks(Integer projectcd,Integer yearcd,Integer regId);
	LinkedHashMap<Integer, String> getProjectPhysicalActionPlan(Integer dCode);
	LinkedHashMap<Integer, String> getFromYearForPhysicalActionPlanReport(Integer pCode);
	LinkedHashMap<Integer, String> getToYearForPhysicalActionPlanReport(Integer fromYear,Integer projId);
	LinkedHashMap<String, List<PhysicalActionPlanBean>> getPhysicalActionPlanReport(Integer stCode,Integer distCode,Integer projId,Integer fromYear,Integer toYear);
	List<PhysicalActionPlanBean> getPhysicalActionReport(Integer stCode,Integer distCode,Integer projId,Integer fromYear,Integer toYear);
	LinkedHashMap<String,List<String>> viewHeadActivity();
	LinkedHashMap<Integer, String> getprojectfordistrict(Integer dCode, Integer stcode);
	LinkedHashMap<Integer,String> getProjectBystate(Integer stcode);
	String saveAsExisting(String plan,String activity,Integer projectcd,Integer yearcd,String loginid);
	LinkedHashMap<Integer,String> getFinYearExistPlan(Integer projId);
	String updateAsExisting(String plan,String activity,Integer projectcd,Integer yearcd,String loginid);
	List<PhysicalActionPlanTranxBean> viewCMovement(Integer regId, String userType);
	List<PhysicalActionPlanTranxBean> viewARPlan(Integer regId, String userType);
	LinkedHashMap<Integer,String> getFinYearMonth();
	List<PhysicalActionPlanBean> getListofHeadAndActivityDesc();
	List<PhysicalActionPlanBean> getListofPhysicalActionAchiev(Integer projectcd,Integer yearcd);

}
