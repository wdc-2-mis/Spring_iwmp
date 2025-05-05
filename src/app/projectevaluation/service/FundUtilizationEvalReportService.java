package app.projectevaluation.service;

import java.util.List;

import app.projectevaluation.bean.FundUtilizationEvalReportBean;

public interface FundUtilizationEvalReportService {

	List<FundUtilizationEvalReportBean> getFundUtilizationEvalReport();

	List<FundUtilizationEvalReportBean> getDistFundUtilizationEvalReport(Integer stcd);

	List<FundUtilizationEvalReportBean> getMandaysDetailsReport();

	List<FundUtilizationEvalReportBean> getProdDetailsReport();

	List<FundUtilizationEvalReportBean> getDistProdDetailsReport(Integer stcd);

	List<FundUtilizationEvalReportBean> getDistMandaysDetailsReport(Integer stcd);

}
