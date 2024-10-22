package app;

import java.math.BigDecimal;

public class PfmsStateDistProjExpndtrBean {
	
	private Integer stcode;
	private String stname;
	private Integer dcode;
	private String distname;
	private Integer projid;
	private String projname;
	private BigDecimal project_cost;
	private BigDecimal centralshare;
	private BigDecimal stateshare;
	private BigDecimal stateexpen;
	private BigDecimal distexpen;
	private BigDecimal projexpen;
	public Integer getStcode() {
		return stcode;
	}
	public void setStcode(Integer stcode) {
		this.stcode = stcode;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public Integer getDcode() {
		return dcode;
	}
	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public Integer getProjid() {
		return projid;
	}
	public void setProjid(Integer projid) {
		this.projid = projid;
	}
	public String getProjname() {
		return projname;
	}
	public void setProjname(String projname) {
		this.projname = projname;
	}
	public BigDecimal getProject_cost() {
		return project_cost;
	}
	public void setProject_cost(BigDecimal project_cost) {
		this.project_cost = project_cost;
	}
	
	public BigDecimal getCentralshare() {
		return centralshare;
	}
	public void setCentralshare(BigDecimal centralshare) {
		this.centralshare = centralshare;
	}
	public BigDecimal getStateshare() {
		return stateshare;
	}
	public void setStateshare(BigDecimal stateshare) {
		this.stateshare = stateshare;
	}
	public BigDecimal getStateexpen() {
		return stateexpen;
	}
	public void setStateexpen(BigDecimal stateexpen) {
		this.stateexpen = stateexpen;
	}
	public BigDecimal getDistexpen() {
		return distexpen;
	}
	public void setDistexpen(BigDecimal distexpen) {
		this.distexpen = distexpen;
	}
	public BigDecimal getProjexpen() {
		return projexpen;
	}
	public void setProjexpen(BigDecimal projexpen) {
		this.projexpen = projexpen;
	}

}
