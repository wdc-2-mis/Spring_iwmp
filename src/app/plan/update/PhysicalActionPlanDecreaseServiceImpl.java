package app.plan.update;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.PhysicalActionPlanBean;

@Service("PhysicalActionPlanDecreaseService")
public class PhysicalActionPlanDecreaseServiceImpl implements PhysicalActionPlanDecreaseService{

	
	@Autowired
	PhysicalActionPlanDecreaseDao dao;
	
	@Override
	public List<PhysicalActionPlanBean> getListofPhysicalActionPlanWithAchiev(Integer projectcd, Integer yearcd) {
		// TODO Auto-generated method stub
		return dao.getListofPhysicalActionPlanWithAchiev(projectcd, yearcd);
	}

	@Override
	public List<PhysicalActionPlanBean> getListofPhysicalActionPlanAchiev(Integer projectcd, Integer yearcd) {
		// TODO Auto-generated method stub
		return dao.getListofPhysicalActionPlanWithAchiev(projectcd, yearcd);
	}

	@Override
	public String updatePlanAcordingAchievement(String plan, String activity, Integer projectcd, Integer yearcd,
			String loginid) {
		// TODO Auto-generated method stub
		return dao.updatePlanAcordingAchievement(plan, activity, projectcd, yearcd, loginid);
	}

}
