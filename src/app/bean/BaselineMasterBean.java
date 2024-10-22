package app.bean;

import java.math.BigDecimal;

public class BaselineMasterBean {

	private String typecode;
	private String bdescription;
	private BigDecimal seqno;
	private String description;
	private int bldescription;
	
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getBdescription() {
		return bdescription;
	}
	public void setBdescription(String bdescription) {
		this.bdescription = bdescription;
	}
	public BigDecimal getSeqno() {
		return seqno;
	}
	public void setSeqno(BigDecimal seqno) {
		this.seqno = seqno;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getBldescription() {
		return bldescription;
	}
	public void setBldescription(Integer bldescription) {
		this.bldescription = bldescription;
	}
	
}
