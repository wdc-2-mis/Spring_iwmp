package app.bean.reports;

import java.math.BigDecimal;

public class QuarterlyTargetBean {

	private Integer st_code;
	private String st_name;
	private String indicators_group;
	private BigDecimal quad1;
	private BigDecimal quad2;
	private BigDecimal quad3;
	private BigDecimal quad4;
	private BigDecimal total_quad;
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
	public String getIndicators_group() {
		return indicators_group;
	}
	public void setIndicators_group(String indicators_group) {
		this.indicators_group = indicators_group;
	}
	public BigDecimal getQuad1() {
		return quad1;
	}
	public void setQuad1(BigDecimal quad1) {
		this.quad1 = quad1;
	}
	public BigDecimal getQuad2() {
		return quad2;
	}
	public void setQuad2(BigDecimal quad2) {
		this.quad2 = quad2;
	}
	public BigDecimal getQuad3() {
		return quad3;
	}
	public void setQuad3(BigDecimal quad3) {
		this.quad3 = quad3;
	}
	public BigDecimal getQuad4() {
		return quad4;
	}
	public void setQuad4(BigDecimal quad4) {
		this.quad4 = quad4;
	}
	public BigDecimal getTotal_quad() {
		return total_quad;
	}
	public void setTotal_quad(BigDecimal total_quad) {
		this.total_quad = total_quad;
	}
	
	
}
