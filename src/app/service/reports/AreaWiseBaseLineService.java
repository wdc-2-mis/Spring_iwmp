package app.service.reports;

import java.util.List;

import app.bean.BaselineStateWiseAreaDetailBean;
import app.bean.BaselineStatewiseCropDetailBean;

public interface AreaWiseBaseLineService {
	

	List<BaselineStateWiseAreaDetailBean> getStatewiseAreaDetail();
	List<BaselineStateWiseAreaDetailBean> getStateWiseAreaDetail2();
	List<BaselineStateWiseAreaDetailBean> getDistWiseAreaDetails(Integer stcd);

//	List<BaselineStateWiseAreaDetailBean> getStatewiseAreaAchievDetail();
//	List<BaselineStatewiseCropDetailBean> getStatewiseCropTypeSurveyDetail();
//	List<BaselineStatewiseCropDetailBean> getStatewiseCropTypeOutcomeDetail();

	List<BaselineStateWiseAreaDetailBean> getDistAreaWiseblservey(int stcd, String stname);

}
