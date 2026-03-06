package app.plan.update;

import java.util.List;

import app.bean.PhysicalActionPlanBean;

public interface PhysicalActionPlanDecreaseDao {
	
	List<PhysicalActionPlanBean> getListofPhysicalActionPlanWithAchiev(Integer projectcd, Integer yearcd);
	String updatePlanAcordingAchievement(String plan,String activity,Integer projectcd,Integer yearcd,String loginid);

}
