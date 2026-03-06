package app.plan.update;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.PhysicalActionPlanBean;

@Service("PhysicalActionPlanDecreaseService")
public interface PhysicalActionPlanDecreaseService {
	
	List<PhysicalActionPlanBean> getListofPhysicalActionPlanWithAchiev(Integer projectcd, Integer yearcd);
	List<PhysicalActionPlanBean> getListofPhysicalActionPlanAchiev(Integer projectcd, Integer yearcd);
	String updatePlanAcordingAchievement(String plan,String activity,Integer projectcd,Integer yearcd,String loginid);

}
