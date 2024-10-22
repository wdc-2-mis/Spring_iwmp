package app.bean;

import java.math.BigInteger;

public class ConvergenceStatusBean {
	
	private BigInteger workcode;
	private String blockname;
	private String gpname;
	private String villagename;
	private Character cnvrgncestatus;
	private BigInteger statusid;
	
	
	public BigInteger getWorkcode() {
		return workcode;
	}
	public void setWorkcode(BigInteger workcode) {
		this.workcode = workcode;
	}
	public String getBlockname() {
		return blockname;
	}
	public void setBlockname(String blockname) {
		this.blockname = blockname;
	}
	public String getGpname() {
		return gpname;
	}
	public void setGpname(String gpname) {
		this.gpname = gpname;
	}
	public String getVillagename() {
		return villagename;
	}
	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}
	public Character getCnvrgncestatus() {
		return cnvrgncestatus;
	}
	public void setCnvrgncestatus(Character cnvrgncestatus) {
		this.cnvrgncestatus = cnvrgncestatus;
	}
	public BigInteger getStatusid() {
		return statusid;
	}
	public void setStatusid(BigInteger statusid) {
		this.statusid = statusid;
	}
	
	

}
