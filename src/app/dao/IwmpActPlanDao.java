package app.dao;


import java.util.List;
import app.bean.RevisedActPlanBean;

public interface IwmpActPlanDao {
//	public void addActPlan(List<IwmpActPlan> iwmpActPlan);
	String saveActPlan(Integer dcode,Integer projid,Integer finyear, Character dprstatus,String authname, Integer sentFrom);
	List<RevisedActPlanBean> getAlreadyDistActPlanData(Integer dCode);
	String getFinalStatus(Integer actPlanId);
	String deleteActPlan(Integer actPlanId);

}
