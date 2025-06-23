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
	
	private BigDecimal pre_milch_cattle;
	private BigDecimal mid_milch_cattle;
	private BigDecimal control_milch_cattle;
	private BigDecimal pre_fodder_production;
	private BigDecimal mid_fodder_production;
	private BigDecimal control_fodder_production;
	
	private BigInteger pre_rural_urban;
	private BigInteger mid_rural_urban;
	private BigInteger control_rural_urban;
	private BigInteger pre_spring_rejuvenated;
	private BigInteger mid_spring_rejuvenated;
	private BigInteger control_spring_rejuvenated;
	private BigInteger pre_person_benefitte;
	private BigInteger mid_person_benefitte;
	private BigInteger control_person_benefitte;
	
	
	public BigInteger getPre_spring_rejuvenated() {
		return pre_spring_rejuvenated;
	}
	public void setPre_spring_rejuvenated(BigInteger pre_spring_rejuvenated) {
		this.pre_spring_rejuvenated = pre_spring_rejuvenated;
	}
	public BigInteger getMid_spring_rejuvenated() {
		return mid_spring_rejuvenated;
	}
	public void setMid_spring_rejuvenated(BigInteger mid_spring_rejuvenated) {
		this.mid_spring_rejuvenated = mid_spring_rejuvenated;
	}
	public BigInteger getPre_person_benefitte() {
		return pre_person_benefitte;
	}
	public void setPre_person_benefitte(BigInteger pre_person_benefitte) {
		this.pre_person_benefitte = pre_person_benefitte;
	}
	public BigInteger getMid_person_benefitte() {
		return mid_person_benefitte;
	}
	public void setMid_person_benefitte(BigInteger mid_person_benefitte) {
		this.mid_person_benefitte = mid_person_benefitte;
	}
	public BigDecimal getPre_milch_cattle() {
		return pre_milch_cattle;
	}
	public void setPre_milch_cattle(BigDecimal pre_milch_cattle) {
		this.pre_milch_cattle = pre_milch_cattle;
	}
	public BigDecimal getMid_milch_cattle() {
		return mid_milch_cattle;
	}
	public void setMid_milch_cattle(BigDecimal mid_milch_cattle) {
		this.mid_milch_cattle = mid_milch_cattle;
	}
	public BigDecimal getControl_milch_cattle() {
		return control_milch_cattle;
	}
	public void setControl_milch_cattle(BigDecimal control_milch_cattle) {
		this.control_milch_cattle = control_milch_cattle;
	}
	public BigDecimal getPre_fodder_production() {
		return pre_fodder_production;
	}
	public void setPre_fodder_production(BigDecimal pre_fodder_production) {
		this.pre_fodder_production = pre_fodder_production;
	}
	public BigDecimal getMid_fodder_production() {
		return mid_fodder_production;
	}
	public void setMid_fodder_production(BigDecimal mid_fodder_production) {
		this.mid_fodder_production = mid_fodder_production;
	}
	public BigDecimal getControl_fodder_production() {
		return control_fodder_production;
	}
	public void setControl_fodder_production(BigDecimal control_fodder_production) {
		this.control_fodder_production = control_fodder_production;
	}
	public BigInteger getPre_rural_urban() {
		return pre_rural_urban;
	}
	public void setPre_rural_urban(BigInteger pre_rural_urban) {
		this.pre_rural_urban = pre_rural_urban;
	}
	public BigInteger getMid_rural_urban() {
		return mid_rural_urban;
	}
	public void setMid_rural_urban(BigInteger mid_rural_urban) {
		this.mid_rural_urban = mid_rural_urban;
	}
	public BigInteger getControl_rural_urban() {
		return control_rural_urban;
	}
	public void setControl_rural_urban(BigInteger control_rural_urban) {
		this.control_rural_urban = control_rural_urban;
	}
	public BigInteger getControl_spring_rejuvenated() {
		return control_spring_rejuvenated;
	}
	public void setControl_spring_rejuvenated(BigInteger control_spring_rejuvenated) {
		this.control_spring_rejuvenated = control_spring_rejuvenated;
	}
	public BigInteger getControl_person_benefitte() {
		return control_person_benefitte;
	}
	public void setControl_person_benefitte(BigInteger control_person_benefitte) {
		this.control_person_benefitte = control_person_benefitte;
	}
	
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
