package app.bean;

import java.math.BigDecimal;

public class StateHeadActivityFinBean {

	private Integer stcode;
	private String stname;
	private Integer headcode;
	private String headdesc;
	private Integer hseqno;
	private Integer activitycode;
	private String activitydesc;
	private Integer aseqno;
	private BigDecimal achievement;
	private String unitname;
	private BigDecimal plan;
	private Integer works;
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
	public Integer getHeadcode() {
		return headcode;
	}
	public void setHeadcode(Integer headcode) {
		this.headcode = headcode;
	}
	public String getHeaddesc() {
		return headdesc;
	}
	public void setHeaddesc(String headdesc) {
		this.headdesc = headdesc;
	}
	public Integer getHseqno() {
		return hseqno;
	}
	public void setHseqno(Integer hseqno) {
		this.hseqno = hseqno;
	}
	public Integer getActivitycode() {
		return activitycode;
	}
	public void setActivitycode(Integer activitycode) {
		this.activitycode = activitycode;
	}
	public String getActivitydesc() {
		return activitydesc;
	}
	public void setActivitydesc(String activitydesc) {
		this.activitydesc = activitydesc;
	}
	public Integer getAseqno() {
		return aseqno;
	}
	public void setAseqno(Integer aseqno) {
		this.aseqno = aseqno;
	}
	public BigDecimal getAchievement() {
		return achievement;
	}
	public void setAchievement(BigDecimal achievement) {
		this.achievement = achievement;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public BigDecimal getPlan() {
		return plan;
	}
	public void setPlan(BigDecimal plan) {
		this.plan = plan;
	}
	public Integer getWorks() {
		return works;
	}
	public void setWorks(Integer works) {
		this.works = works;
	}
	@Override
	public String toString() {
		return "StateHeadActivityFinBean [stcode=" + stcode + ", stname=" + stname + ", headcode=" + headcode
				+ ", headdesc=" + headdesc + ", hseqno=" + hseqno + ", activitycode=" + activitycode + ", activitydesc="
				+ activitydesc + ", aseqno=" + aseqno + ", achievement=" + achievement + ", unitname=" + unitname
				+ ", plan=" + plan + ", works=" + works + "]";
	}
	
	
}
