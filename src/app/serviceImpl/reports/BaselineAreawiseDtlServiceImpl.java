package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.BaselineStateWiseAreaDetailBean;
import app.bean.BaselineStatewiseCropDetailBean;
import app.dao.reports.BaselineAreawiseDtlDao;
import app.service.reports.BaselineAreawiseDtlService;

@Service
public class BaselineAreawiseDtlServiceImpl implements BaselineAreawiseDtlService{

	@Autowired
	BaselineAreawiseDtlDao blsAreawiseDtlDao;
	
	@Override
	public List<BaselineStateWiseAreaDetailBean> getStwiseAreaDetail() {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getStwiseAreaDetail();
	}

	@Override
	public List<BaselineStateWiseAreaDetailBean> getStwiseAreaAchievDetail() {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getStwiseAreaAchievDetail();
	}

	@Override
	public List<BaselineStatewiseCropDetailBean> getStwiseCropTypeSurveyDetail() {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getStwiseCropTypeSurveyDetail();
	}

	@Override
	public List<BaselineStatewiseCropDetailBean> getStwiseCropTypeOutcomeDetail() {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getStwiseCropTypeOutcomeDetail();
	}

	@Override
	public List<BaselineStatewiseCropDetailBean> getDistblsCrpareaSrvyDetail(int id) {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getDistblsCrpareaSrvyDetail(id);
	}

	@Override
	public List<BaselineStatewiseCropDetailBean> getProjWiseblsCrpareaSrvyDetail(int id) {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getProjWiseblsCrpareaSrvyDetail(id);
	}

	

	@Override
	public List<BaselineStateWiseAreaDetailBean> getDistWiseAreaDetail(int stcode) {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getDistWiseAreaDetail(stcode);
	}

	@Override
	public List<BaselineStateWiseAreaDetailBean> getProjWiseAreaDetail(int distcode) {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getProjWiseAreaDetail( distcode);
	}

	@Override
	public List<BaselineStateWiseAreaDetailBean> getDistwiseAreaAchieveDetail(int stcode) {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getDistwiseAreaAchieveDetail(stcode);
	}

	@Override
	public List<BaselineStateWiseAreaDetailBean> getProjwiseAreaAchieveDetail(int dcode) {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getProjwiseAreaAchieveDetail(dcode);
	}

	@Override
	public List<BaselineStatewiseCropDetailBean> getDistwiseAreaCrpDetail(int stcode) {
		// TODO Auto-generated method stub
		return blsAreawiseDtlDao.getDistwiseAreaCrpDetail(stcode);
	}

}
