package app.projectevaluation.dao;

import java.util.List;

import app.projectevaluation.bean.FundUtilizationEvalReportBean;

public interface FundUtilizationEvalReportDao {
	
	List<FundUtilizationEvalReportBean> getFundUtilizationEvalReport();
	
	List<FundUtilizationEvalReportBean> getDistFundUtilizationEvalReport(Integer stcd);
	
	List<FundUtilizationEvalReportBean> getMandaysDetailsReport();

}
