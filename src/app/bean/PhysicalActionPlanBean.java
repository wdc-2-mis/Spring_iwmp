package app.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PhysicalActionPlanBean {
	private Integer headcode;
	private String headname;
	private Integer activitycode;
	private String activityname;
	private Integer unitcode;
	private String unitname;
	private BigDecimal plan;
	private Integer aapid;
	private String distname;
	private String userid;
	private Integer regid;
	private String projectdesc;
	private String yrdesc;
	private String status;
	private BigDecimal headsequence;
	private BigDecimal activitysequence;
	private Integer asset;
	private Integer assetcreated;
	
	
	
	public BigDecimal getHeadsequence() {
		return headsequence;
	}
	public void setHeadsequence(BigDecimal headsequence) {
		this.headsequence = headsequence;
	}
	public BigDecimal getActivitysequence() {
		return activitysequence;
	}
	public void setActivitysequence(BigDecimal activitysequence) {
		this.activitysequence = activitysequence;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProjectdesc() {
		return projectdesc;
	}
	public void setProjectdesc(String projectdesc) {
		this.projectdesc = projectdesc;
	}
	public String getYrdesc() {
		return yrdesc;
	}
	public void setYrdesc(String yrdesc) {
		this.yrdesc = yrdesc;
	}
	public Integer getRegid() {
		return regid;
	}
	public void setRegid(Integer regid) {
		this.regid = regid;
	}
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getAapid() {
		return aapid;
	}
	public void setAapid(Integer aapid) {
		this.aapid = aapid;
	}
	public Integer getHeadcode() {
		return headcode;
	}
	public void setHeadcode(Integer headcode) {
		this.headcode = headcode;
	}
	public String getHeadname() {
		return headname;
	}
	public void setHeadname(String headname) {
		this.headname = headname;
	}
	public Integer getActivitycode() {
		return activitycode;
	}
	public void setActivitycode(Integer activitycode) {
		this.activitycode = activitycode;
	}
	public String getActivityname() {
		return activityname;
	}
	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}
	public Integer getUnitcode() {
		return unitcode;
	}
	public void setUnitcode(Integer unitcode) {
		this.unitcode = unitcode;
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
	public Integer getAsset() {
		return asset;
	}
	public void setAsset(Integer asset) {
		this.asset = asset;
	}
	public Integer getAssetcreated() {
		return assetcreated;
	}
	public void setAssetcreated(Integer assetcreated) {
		this.assetcreated = assetcreated;
	}
	
	

}
