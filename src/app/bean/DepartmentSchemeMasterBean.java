package app.bean;

import java.math.BigDecimal;

public class DepartmentSchemeMasterBean {
	
	private BigDecimal seqno;
	private String schemeDescription;
	private int departmentSchemeIdPk;
	private Boolean status;
	
	public BigDecimal getSeqno() {
		return seqno;
	}
	public void setSeqno(BigDecimal seqno) {
		this.seqno = seqno;
	}
	public String getSchemeDescription() {
		return schemeDescription;
	}
	public void setSchemeDescription(String schemeDescription) {
		this.schemeDescription = schemeDescription;
	}
	public int getDepartmentSchemeIdPk() {
		return departmentSchemeIdPk;
	}
	public void setDepartmentSchemeIdPk(int departmentSchemeIdPk) {
		this.departmentSchemeIdPk = departmentSchemeIdPk;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	

}
