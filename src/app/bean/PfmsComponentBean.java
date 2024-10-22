package app.bean;

import java.math.BigDecimal;

public class PfmsComponentBean {
	private int st_code;
	private String stname;
	private String distname;
	private int dcode;
	private String component_code;
	private String Component_name;
	private int financial_year;
	private BigDecimal expenditure;
	
	
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public int getDcode() {
		return dcode;
	}
	public void setDcode(int dcode) {
		this.dcode = dcode;
	}
	public int getSt_code() {
		return st_code;
	}
	public void setSt_code(int st_code) {
		this.st_code = st_code;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public String getComponent_code() {
		return component_code;
	}
	public void setComponent_code(String component_code) {
		this.component_code = component_code;
	}
	public String getComponent_name() {
		return Component_name;
	}
	public void setComponent_name(String component_name) {
		Component_name = component_name;
	}
	public int getFinancial_year() {
		return financial_year;
	}
	public void setFinancial_year(int financial_year) {
		this.financial_year = financial_year;
	}
	public BigDecimal getExpenditure() {
		return expenditure;
	}
	public void setExpenditure(BigDecimal expenditure) {
		this.expenditure = expenditure;
	}
	
	
	

}
