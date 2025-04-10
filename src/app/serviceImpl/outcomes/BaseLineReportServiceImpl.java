package app.serviceImpl.outcomes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;
import app.dao.outcomes.BaseLineOutcomeReportDao;
import app.dao.outcomes.BaseLineReportDao;
import app.service.outcome.BaseLineReportService;

@Service("BaseLineReportService")
public class BaseLineReportServiceImpl  implements BaseLineReportService{

	@Autowired
	BaseLineReportDao baseLineOutcomeReportDao;

	@Override
	public List<BaseLineOutcomeBean> getBlsReportStateLevel() {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsReportStateLevel();
	}

	@Override
	public List<BaseLineOutcomeBean> getBlsReportDistrictLevel(Integer stCode) {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsReportDistrictLevel(stCode);
	}

	@Override
	public List<BaseLineOutcomeBean> getBlsReportProjectLevel(Integer dCode) {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsReportProjectLevel(dCode);
	}

	@Override
	public List<BaseLineOutcomeBean> getBlsReportStateLevelForStCode(Integer stCode) {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsReportStateLevelForStCode(stCode);
	}

	@Override
	public List<NewBaseLineSurveyBean> getBlsReportDetailLevel(Integer projectId) {
		// TODO Auto-generated method stub
		return baseLineOutcomeReportDao.getBlsReportDetailLevel(projectId);
	}
}
