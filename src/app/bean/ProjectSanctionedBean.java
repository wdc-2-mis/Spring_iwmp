package app.bean;

import java.math.BigDecimal;

public class ProjectSanctionedBean {

	private String st_name;
	private Integer st_code;
	private Integer district;
	private Integer project;
	private BigDecimal area;
	private BigDecimal cost;
	private BigDecimal central;
	private BigDecimal state;

	public String getSt_name() {
		return st_name;
	}

	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}

	public Integer getSt_code() {
		return st_code;
	}

	public void setSt_code(Integer st_code) {
		this.st_code = st_code;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getCentral() {
		return central;
	}

	public void setCentral(BigDecimal central) {
		this.central = central;
	}

	public BigDecimal getState() {
		return state;
	}

	public void setState(BigDecimal state) {
		this.state = state;
	}

}
