package app.projectevaluation.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.projectevaluation.bean.FundUtilizationEvalReportBean;
import app.projectevaluation.dao.FundUtilizationEvalReportDao;
import app.projectevaluation.service.FundUtilizationEvalReportService;



@Service("FundUtilizationEvalReportService")
public class FundUtilizationEvalReportServiceImpl implements FundUtilizationEvalReportService{
	
	@Autowired
	FundUtilizationEvalReportDao fundEvalDao;

	@Override
	public List<FundUtilizationEvalReportBean> getFundUtilizationEvalReport() {
		return fundEvalDao.getFundUtilizationEvalReport();
	}

	@Override
	public List<FundUtilizationEvalReportBean> getDistFundUtilizationEvalReport(Integer stcd) {
		return fundEvalDao.getDistFundUtilizationEvalReport(stcd);
	}

	@Override
	public List<FundUtilizationEvalReportBean> getMandaysDetailsReport() {
		return fundEvalDao.getMandaysDetailsReport();
	}
	
	
}