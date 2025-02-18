package app.dao.reports;

import java.util.List;

import app.bean.BaselineStateWiseAreaDetailBean;
import app.bean.BaselineStatewiseCropDetailBean;

public interface BaselineAreawiseDtlDao {

	List<BaselineStateWiseAreaDetailBean> getStwiseAreaDetail();
	List<BaselineStateWiseAreaDetailBean> getStwiseAreaAchievDetail();
	List<BaselineStatewiseCropDetailBean> getStwiseCropTypeSurveyDetail();
	List<BaselineStatewiseCropDetailBean> getStwiseCropTypeOutcomeDetail();
	List<BaselineStatewiseCropDetailBean> getDistblsCrpareaSrvyDetail(int id);
	List<BaselineStatewiseCropDetailBean> getProjWiseblsCrpareaSrvyDetail(int id);
	List<BaselineStateWiseAreaDetailBean> getDistWiseAreaDetail(int stcode);
	List<BaselineStateWiseAreaDetailBean> getProjWiseAreaDetail(int distcode);
	List<BaselineStateWiseAreaDetailBean> getDistwiseAreaAchieveDetail(int stcode);
	List<BaselineStateWiseAreaDetailBean> getProjwiseAreaAchieveDetail(int dcode);
	List<BaselineStatewiseCropDetailBean> getDistwiseAreaCrpDetail(int stcode);
}
