package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.BaselineStateWiseAreaDetailBean;
import app.dao.reports.AreaWiseBaselineDao;
import app.service.reports.AreaWiseBaseLineService;

@Service
public class AreaWiseBaselineServiceImpl implements AreaWiseBaseLineService{

	@Autowired
	AreaWiseBaselineDao areaWiseBaselineDao;

	@Override
	public List<BaselineStateWiseAreaDetailBean> getStatewiseAreaDetail() {
		return areaWiseBaselineDao.getStatewiseAreaDetail();
	}

	@Override
	public List<BaselineStateWiseAreaDetailBean> getStateWiseAreaDetail2() {
		return areaWiseBaselineDao.getStateWiseAreaDetail2();
	}
	
	@Override
	public List<BaselineStateWiseAreaDetailBean> getDistWiseAreaDetails(Integer stcd) {
		return areaWiseBaselineDao.getDistWiseAreaDetails(stcd);
	}


	@Override
	public List<BaselineStateWiseAreaDetailBean> getDistAreaWiseblservey(int stcd, String stname) {
		// TODO Auto-generated method stub
		return areaWiseBaselineDao.getDistAreaWiseblservey(stcd, stname);
	}


} 

	
	

