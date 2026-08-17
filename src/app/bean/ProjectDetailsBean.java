package app.bean;

import java.math.BigDecimal;

public class ProjectDetailsBean {
	
	private Integer dcode;
	private String distname;
	private Integer projid;
	private String projname;
	private BigDecimal sanctionedamount;
	private Integer finyear;
	private String projectstatus;
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
	public BigDecimal getSanctionedamount() {
		return sanctionedamount;
	}
	public void setSanctionedamount(BigDecimal sanctionedamount) {
		this.sanctionedamount = sanctionedamount;
	}
	public Integer getFinyear() {
		return finyear;
	}
	public void setFinyear(Integer finyear) {
		this.finyear = finyear;
	}
	public String getProjectstatus() {
		return projectstatus;
	}
	public void setProjectstatus(String projectstatus) {
		this.projectstatus = projectstatus;
	}

}
