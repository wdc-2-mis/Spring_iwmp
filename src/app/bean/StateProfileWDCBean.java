package app.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StateProfileWDCBean {

	private int st_code;
	private String st_name;
	private BigInteger no_of_district;
	private BigInteger noofproject;
	private BigDecimal totarea;
	private BigDecimal prjcost;
	public int getSt_code() {
		return st_code;
	}
	public void setSt_code(int st_code) {
		this.st_code = st_code;
	}
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public BigInteger getNo_of_district() {
		return no_of_district;
	}
	public void setNo_of_district(BigInteger no_of_district) {
		this.no_of_district = no_of_district;
	}
	public BigInteger getNoofproject() {
		return noofproject;
	}
	public void setNoofproject(BigInteger noofproject) {
		this.noofproject = noofproject;
	}
	public BigDecimal getTotarea() {
		return totarea;
	}
	public void setTotarea(BigDecimal totarea) {
		this.totarea = totarea;
	}
	public BigDecimal getPrjcost() {
		return prjcost;
	}
	public void setPrjcost(BigDecimal prjcost) {
		this.prjcost = prjcost;
	}
	
}
