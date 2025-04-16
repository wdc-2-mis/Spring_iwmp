package app.projectevaluation.bean;

import java.math.BigDecimal;

public class FundUtilizationEvalReportBean {
	
	private Integer st_code;
	private String st_name;
	private Integer total_project;
	
	private BigDecimal total_project_area;
	private BigDecimal total_evaluation_central_share;
	private BigDecimal total_evaluation_state_share;
	private BigDecimal total_share_evaluation;
	private BigDecimal total_fund_central_share;
	private BigDecimal total_fund_state_share;
	private BigDecimal total_fund;
	private BigDecimal total_expenditure;
	
	
	public Integer getSt_code() {
		return st_code;
	}
	public void setSt_code(Integer st_code) {
		this.st_code = st_code;
	}
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public Integer getTotal_project() {
		return total_project;
	}
	public void setTotal_project(Integer total_project) {
		this.total_project = total_project;
	}
	public BigDecimal getTotal_project_area() {
		return total_project_area;
	}
	public void setTotal_project_area(BigDecimal total_project_area) {
		this.total_project_area = total_project_area;
	}
	public BigDecimal getTotal_evaluation_central_share() {
		return total_evaluation_central_share;
	}
	public void setTotal_evaluation_central_share(BigDecimal total_evaluation_central_share) {
		this.total_evaluation_central_share = total_evaluation_central_share;
	}
	public BigDecimal getTotal_evaluation_state_share() {
		return total_evaluation_state_share;
	}
	public void setTotal_evaluation_state_share(BigDecimal total_evaluation_state_share) {
		this.total_evaluation_state_share = total_evaluation_state_share;
	}
	public BigDecimal getTotal_share_evaluation() {
		return total_share_evaluation;
	}
	public void setTotal_share_evaluation(BigDecimal total_share_evaluation) {
		this.total_share_evaluation = total_share_evaluation;
	}
	public BigDecimal getTotal_fund_central_share() {
		return total_fund_central_share;
	}
	public void setTotal_fund_central_share(BigDecimal total_fund_central_share) {
		this.total_fund_central_share = total_fund_central_share;
	}
	public BigDecimal getTotal_fund_state_share() {
		return total_fund_state_share;
	}
	public void setTotal_fund_state_share(BigDecimal total_fund_state_share) {
		this.total_fund_state_share = total_fund_state_share;
	}
	public BigDecimal getTotal_fund() {
		return total_fund;
	}
	public void setTotal_fund(BigDecimal total_fund) {
		this.total_fund = total_fund;
	}
	public BigDecimal getTotal_expenditure() {
		return total_expenditure;
	}
	public void setTotal_expenditure(BigDecimal total_expenditure) {
		this.total_expenditure = total_expenditure;
	}
	
	
	
}
