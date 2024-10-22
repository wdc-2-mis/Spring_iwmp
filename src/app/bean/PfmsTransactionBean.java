package app.bean;

import java.math.BigDecimal;

public class PfmsTransactionBean {
	private int eatmisdataId;
	private String tranxId;
	private String date;
	private String componentCode;
	private String componentName;
	private String invoiceNo;
	private BigDecimal totalAmount;
	private String projectName;
	private Integer projectId;
	private char status;
	private int assetid;
	private String workdtl;
	private char wistatus;
	private int wicode;
	private BigDecimal expenditure;
	
	public int getEatmisdataId() {
		return eatmisdataId;
	}
	public void setEatmisdataId(int eatmisdataId) {
		this.eatmisdataId = eatmisdataId;
	}
	public String getTranxId() {
		return tranxId;
	}
	public void setTranxId(String tranxId) {
		this.tranxId = tranxId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getComponentCode() {
		return componentCode;
	}
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public int getAssetid() {
		return assetid;
	}
	public void setAssetid(int assetid) {
		this.assetid = assetid;
	}
	public String getWorkdtl() {
		return workdtl;
	}
	public void setWorkdtl(String workdtl) {
		this.workdtl = workdtl;
	}
	public char getWistatus() {
		return wistatus;
	}
	public void setWistatus(char wistatus) {
		this.wistatus = wistatus;
	}
	public int getWicode() {
		return wicode;
	}
	public void setWicode(int wicode) {
		this.wicode = wicode;
	}
	public BigDecimal getExpenditure() {
		return expenditure;
	}
	public void setExpenditure(BigDecimal expenditure) {
		this.expenditure = expenditure;
	}
	
	
	

}
