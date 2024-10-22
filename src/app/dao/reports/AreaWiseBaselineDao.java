package app.dao.reports;

import java.util.List;

import app.bean.BaselineStateWiseAreaDetailBean;

public interface AreaWiseBaselineDao {
	
	List<BaselineStateWiseAreaDetailBean> getStatewiseAreaDetail();
	List<BaselineStateWiseAreaDetailBean> getStateWiseAreaDetail2();
	List<BaselineStateWiseAreaDetailBean> getDistWiseAreaDetails(Integer stcd);

	List<BaselineStateWiseAreaDetailBean> getDistAreaWiseblservey(int stcd, String stname);
}
