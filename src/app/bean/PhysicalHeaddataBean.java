package app.bean;

import java.math.BigDecimal;

public class PhysicalHeaddataBean {

	private int headcode;
	private String headdesc;
	private char status;
	private BigDecimal seqno;
	private Boolean bls_required;
	public int getHeadcode() {
		return headcode;
	}
	public void setHeadcode(int headcode) {
		this.headcode = headcode;
	}
	public String getHeaddesc() {
		return headdesc;
	}
	public void setHeaddesc(String headdesc) {
		this.headdesc = headdesc;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public BigDecimal getSeqno() {
		return seqno;
	}
	public void setSeqno(BigDecimal seqno) {
		this.seqno = seqno;
	}
	public Boolean getBls_required() {
		return bls_required;
	}
	public void setBls_required(Boolean bls_required) {
		this.bls_required = bls_required;
	}
	
	
}
