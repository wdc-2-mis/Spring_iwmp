package app.serviceImpl.outcomes;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;
import app.dao.outcomes.BaseLineOutcomeReportDao;
import app.service.outcome.BaseLineOutcomeReportService;

@Service("BaseLineOutcomeReportService")
public class BaseLineOutcomeReportServiceImpl implements BaseLineOutcomeReportService{
	
	@Autowired
	BaseLineOutcomeReportDao baseLineOutcomeReportDao;

	@Override
	public List<BaseLineOutcomeBean> getBlsOutReportStateLevel() {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsOutReportStateLevel();
	}

	@Override
	public List<BaseLineOutcomeBean> getBlsOutReportDistrictLevel(Integer stCode) {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsOutReportDistrictLevel(stCode);
	}

	@Override
	public List<BaseLineOutcomeBean> getBlsOutReportProjectLevel(Integer dCode) {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsOutReportProjectLevel(dCode);
	}

	@Override
	public List<BaseLineOutcomeBean> getBlsOutReportStateLevelForStCode(Integer stCode) {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsOutReportStateLevelForStCode(stCode);
	}

	@Override
	public List<NewBaseLineSurveyBean> getBlsOutReportDetailLevel(Integer projectId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsOutReportDetailLevel(projectId);
	}

}
