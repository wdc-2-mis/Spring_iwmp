package app.dao.unfreeze;

import java.util.List;

import app.bean.UnfreezeProjectLoactionBean;

public interface UnfreezeLocationDao {
	
	List<UnfreezeProjectLoactionBean> getUnfreezeProjectLocation(Integer district, Integer project);
	boolean deletePhysicalWorkIdTemp(Integer projid);
	boolean unfreezeBaselineSurveyPlotWise(Integer projid);
	boolean unfreezeProjectLocatin(Integer projid);
}
