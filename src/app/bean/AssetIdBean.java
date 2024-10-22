package app.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import app.model.master.IwmpBlock;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;

public class AssetIdBean {
	private BigInteger tempassetid;
	private BigInteger assetid;
	private Integer aapid;
	private Integer projid;
	private Integer finyrcd;
	private Integer activitycd;
	private Integer vcode;
	private Integer bcode;
	private String projdesc;
	private String finyrdesc;
	private String activitydesc;
	private String headdesc;
	private String vname;
	private String bname;
	private List<IwmpBlock> blocklist;
	private Boolean distApprovalReq;
	private Character currentLevel;
	private String forwardedTo;
	private String rejectedBy;
	private String remarks;
	private String usertype;
	private Character status;
	private String reason;
	private String sdate;
	private String cdate;
	private Long statusid;
	private Character convergence;
	private List<String> assetParameter;
	private List<Long> assetParameterId;
	private LinkedHashMap<String,BigDecimal> assetParameterAchievement;
	private String unit;
	private Integer subactivitycode;
	private String subactivitydesc;
	private List<IwmpMPhySubactivity> subactivitylist;
	private String stname;
	private String distname;
	private BigDecimal assetach;
	private Integer asseteid;
	private String nearby;
	private String areacovered;
	private String statuskd;
	private String statusdate;
	public Integer getAsseteid() {
		return asseteid;
	}
	public void setAsseteid(Integer asseteid) {
		this.asseteid = asseteid;
	}
	public BigDecimal getAssetach() {
		return assetach;
	}
	public void setAssetach(BigDecimal assetach) {
		this.assetach = assetach;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public Integer getSubactivitycode() {
		return subactivitycode;
	}
	public void setSubactivitycode(Integer subactivitycode) {
		this.subactivitycode = subactivitycode;
	}
	public String getSubactivitydesc() {
		return subactivitydesc;
	}
	public void setSubactivitydesc(String subactivitydesc) {
		this.subactivitydesc = subactivitydesc;
	}
	public BigInteger getAssetid() {
		return assetid;
	}
	public void setAssetid(BigInteger assetid) {
		this.assetid = assetid;
	}
	public BigInteger getTempassetid() {
		return tempassetid;
	}
	public void setTempassetid(BigInteger tempassetid) {
		this.tempassetid = tempassetid;
	}
	public Integer getAapid() {
		return aapid;
	}
	public void setAapid(Integer aapid) {
		this.aapid = aapid;
	}
	
	
	
	public Long getStatusid() {
		return statusid;
	}
	public void setStatusid(Long statusid) {
		this.statusid = statusid;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getProjid() {
		return projid;
	}
	public void setProjid(Integer projid) {
		this.projid = projid;
	}
	public Integer getFinyrcd() {
		return finyrcd;
	}
	public void setFinyrcd(Integer finyrcd) {
		this.finyrcd = finyrcd;
	}
	
	public Character getConvergence() {
		return convergence;
	}
	public void setConvergence(Character convergence) {
		this.convergence = convergence;
	}
	public Integer getActivitycd() {
		return activitycd;
	}
	public void setActivitycd(Integer activitycd) {
		this.activitycd = activitycd;
	}
	
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public Integer getVcode() {
		return vcode;
	}
	public void setVcode(Integer vcode) {
		this.vcode = vcode;
	}
	public Integer getBcode() {
		return bcode;
	}
	public void setBcode(Integer bcode) {
		this.bcode = bcode;
	}
	public String getProjdesc() {
		return projdesc;
	}
	public void setProjdesc(String projdesc) {
		this.projdesc = projdesc;
	}
	public String getFinyrdesc() {
		return finyrdesc;
	}
	public void setFinyrdesc(String finyrdesc) {
		this.finyrdesc = finyrdesc;
	}
	public String getActivitydesc() {
		return activitydesc;
	}
	public void setActivitydesc(String activitydesc) {
		this.activitydesc = activitydesc;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public List<IwmpBlock> getBlocklist() {
		return blocklist;
	}
	public void setBlocklist(List<IwmpBlock> blocklist) {
		this.blocklist = blocklist;
	}
	public String getHeaddesc() {
		return headdesc;
	}
	public void setHeaddesc(String headdesc) {
		this.headdesc = headdesc;
	}
	public Boolean getDistApprovalReq() {
		return distApprovalReq;
	}
	public void setDistApprovalReq(Boolean distApprovalReq) {
		this.distApprovalReq = distApprovalReq;
	}
	public Character getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(Character currentLevel) {
		this.currentLevel = currentLevel;
	}
	public String getForwardedTo() {
		return forwardedTo;
	}
	public void setForwardedTo(String forwardedTo) {
		this.forwardedTo = forwardedTo;
	}
	public String getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public List<String> getAssetParameter() {
		return assetParameter;
	}
	public void setAssetParameter(List<String> assetParameter) {
		this.assetParameter = assetParameter;
	}
	public List<Long> getAssetParameterId() {
		return assetParameterId;
	}
	public void setAssetParameterId(List<Long> assetParameterId) {
		this.assetParameterId = assetParameterId;
	}
	public LinkedHashMap<String,BigDecimal> getAssetParameterAchievement() {
		return assetParameterAchievement;
	}
	public void setAssetParameterAchievement(LinkedHashMap<String,BigDecimal> assetParameterAchievement) {
		this.assetParameterAchievement = assetParameterAchievement;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public List<IwmpMPhySubactivity> getSubactivitylist() {
		return subactivitylist;
	}
	public void setSubactivitylist(List<IwmpMPhySubactivity> subactivitylist) {
		this.subactivitylist = subactivitylist;
	}
	public String getNearby() {
		return nearby;
	}
	public void setNearby(String nearby) {
		this.nearby = nearby;
	}
	/*
	 * public BigDecimal getAreacovered() { return areacovered; } public void
	 * setAreacovered(BigDecimal areacovered) { this.areacovered = areacovered; }
	 */
	public String getAreacovered() {
		return areacovered;
	}
	public void setAreacovered(String areacovered) {
		this.areacovered = areacovered;
	}
	public String getStatuskd() {
		return statuskd;
	}
	public void setStatuskd(String statuskd) {
		this.statuskd = statuskd;
	}
	public String getStatusdate() {
		return statusdate;
	}
	public void setStatusdate(String statusdate) {
		this.statusdate = statusdate;
	}
	
	
	

}
