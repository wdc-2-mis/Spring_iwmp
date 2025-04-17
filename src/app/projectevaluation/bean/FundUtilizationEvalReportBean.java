package app.projectevaluation.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

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
	
	private Integer dcode;
	private String dist_name;
	
	private BigDecimal pre_farmer_income;
	private BigDecimal mid_farmer_income;
	private BigDecimal control_farmer_income;
	private BigInteger farmer_benefited;
	private BigInteger control_farmer_benefited;
	private BigInteger mandays_generated;
	private BigInteger control_mandays_generated;
	
	
	public BigDecimal getPre_farmer_income() {
		return pre_farmer_income;
	}
	public void setPre_farmer_income(BigDecimal pre_farmer_income) {
		this.pre_farmer_income = pre_farmer_income;
	}
	public BigDecimal getMid_farmer_income() {
		return mid_farmer_income;
	}
	public void setMid_farmer_income(BigDecimal mid_farmer_income) {
		this.mid_farmer_income = mid_farmer_income;
	}
	public BigDecimal getControl_farmer_income() {
		return control_farmer_income;
	}
	public void setControl_farmer_income(BigDecimal control_farmer_income) {
		this.control_farmer_income = control_farmer_income;
	}
	public BigInteger getFarmer_benefited() {
		return farmer_benefited;
	}
	public void setFarmer_benefited(BigInteger farmer_benefited) {
		this.farmer_benefited = farmer_benefited;
	}
	public BigInteger getControl_farmer_benefited() {
		return control_farmer_benefited;
	}
	public void setControl_farmer_benefited(BigInteger control_farmer_benefited) {
		this.control_farmer_benefited = control_farmer_benefited;
	}
	public BigInteger getMandays_generated() {
		return mandays_generated;
	}
	public void setMandays_generated(BigInteger mandays_generated) {
		this.mandays_generated = mandays_generated;
	}
	public BigInteger getControl_mandays_generated() {
		return control_mandays_generated;
	}
	public void setControl_mandays_generated(BigInteger control_mandays_generated) {
		this.control_mandays_generated = control_mandays_generated;
	}
	public Integer getDcode() {
		return dcode;
	}
	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}
	public String getDist_name() {
		return dist_name;
	}
	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}
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
