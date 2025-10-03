package app.projectevaluation.dao;

import java.util.List;

import app.projectevaluation.bean.FundUtilizationEvalReportBean;

public interface FundUtilizationEvalReportDao {
	
	List<FundUtilizationEvalReportBean> getFundUtilizationEvalReport();
	
	List<FundUtilizationEvalReportBean> getDistFundUtilizationEvalReport(Integer stcd);
	
	List<FundUtilizationEvalReportBean> getMandaysDetailsReport();

	List<FundUtilizationEvalReportBean> getProdDetailsReport();
	
	List<FundUtilizationEvalReportBean> getDistProdDetailsReport(Integer stcd);
	
	List<FundUtilizationEvalReportBean> getDistMandaysDetailsReport(Integer stcd);
	
	List<FundUtilizationEvalReportBean> getGradeEvaluationReport();

	List<FundUtilizationEvalReportBean> getProjFundUtilizationEvalReport(Integer dcode);
}
