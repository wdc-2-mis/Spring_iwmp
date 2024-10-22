package app.bean;

import java.math.BigDecimal;

public class PhysicalActBean {

	private int activitycode;
	private int otherActivityCode;
	private String otherActivityDesc;
	private String headdesc;
	   private Integer headcode;
	   private String actdesc;
	   private char status;
	   private int unitcode;
	   private String unitdesc;
	   private Integer id;
	   private String unithdesc;
       private BigDecimal seqno;
       private String asset;
       private Integer asset1;
       private String subactivitydesc;
       private Boolean isactive;
public String getUnithdesc() {
		return unithdesc;
	}
	public void setUnithdesc(String unithdesc) {
		this.unithdesc = unithdesc;
	}
public Integer getHeadcode() {
	return headcode;
}
public void setHeadcode(Integer headcode) {
	this.headcode = headcode;
}
public int getActivitycode() {
	return activitycode;
}
public void setActivitycode(int activitycode) {
	this.activitycode = activitycode;
}
public int getOtherActivityCode() {
	return otherActivityCode;
}
public void setOtherActivityCode(int otherActivityCode) {
	this.otherActivityCode = otherActivityCode;
}
public String getOtherActivityDesc() {
	return otherActivityDesc;
}
public void setOtherActivityDesc(String otherActivityDesc) {
	this.otherActivityDesc = otherActivityDesc;
}
public String getHeaddesc() {
	return headdesc;
}
public void setHeaddesc(String headdesc) {
	this.headdesc = headdesc;
}
public String getActdesc() {
	return actdesc;
}
public void setActdesc(String actdesc) {
	this.actdesc = actdesc;
}
public char getStatus() {
	return status;
}
public void setStatus(char status) {
	this.status = status;
}
public int getUnitcode() {
	return unitcode;
}
public void setUnitcode(int unitcode) {
	this.unitcode = unitcode;
}
public String getUnitdesc() {
	return unitdesc;
}
public void setUnitdesc(String unitdesc) {
	this.unitdesc = unitdesc;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public BigDecimal getSeqno() {
	return seqno;
}
public void setSeqno(BigDecimal seqno) {
	this.seqno = seqno;
}
public String getAsset() {
	return asset;
}
public void setAsset(String asset) {
	if(asset==null)
	this.asset = "";
	else
	if(asset.equals("0"))
		this.asset = "NO";
	else
		if(asset.equals("1"))
			this.asset = "Single";

		else
			if(asset.equals("2"))
			this.asset = "Multiple";
}
public Integer getAsset1() {
	return asset1;
}
public void setAsset1(Integer asset1) {
	this.asset1 = asset1;
}
public String getSubactivitydesc() {
	return subactivitydesc;
}
public void setSubactivitydesc(String subactivitydesc) {
	this.subactivitydesc = subactivitydesc;
}
public Boolean getIsactive() {
	return isactive;
}
public void setIsactive(Boolean isactive) {
	this.isactive = isactive;
}
   
   
	
}
