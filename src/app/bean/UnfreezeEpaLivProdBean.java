package app.bean;

import java.math.BigDecimal;

public class UnfreezeEpaLivProdBean {
	
	private Integer elp_id;
	private String elp_desc;
	private String crop_desc;
	private Integer no_activities;
	private Integer sc;
	private Integer st;
	private Integer other;
	private Integer total;
	private Integer women;
	private BigDecimal pre_avg_income;
	private BigDecimal post_avg_income;
	
	public Integer getElp_id() {
		return elp_id;
	}
	public void setElp_id(Integer elp_id) {
		this.elp_id = elp_id;
	}
	public String getElp_desc() {
		return elp_desc;
	}
	public void setElp_desc(String elp_desc) {
		this.elp_desc = elp_desc;
	}
	public String getCrop_desc() {
		return crop_desc;
	}
	public void setCrop_desc(String crop_desc) {
		this.crop_desc = crop_desc;
	}
	public Integer getNo_activities() {
		return no_activities;
	}
	public void setNo_activities(Integer no_activities) {
		this.no_activities = no_activities;
	}
	public Integer getSc() {
		return sc;
	}
	public void setSc(Integer sc) {
		this.sc = sc;
	}
	public Integer getSt() {
		return st;
	}
	public void setSt(Integer st) {
		this.st = st;
	}
	public Integer getOther() {
		return other;
	}
	public void setOther(Integer other) {
		this.other = other;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getWomen() {
		return women;
	}
	public void setWomen(Integer women) {
		this.women = women;
	}
	public BigDecimal getPre_avg_income() {
		return pre_avg_income;
	}
	public void setPre_avg_income(BigDecimal pre_avg_income) {
		this.pre_avg_income = pre_avg_income;
	}
	public BigDecimal getPost_avg_income() {
		return post_avg_income;
	}
	public void setPost_avg_income(BigDecimal post_avg_income) {
		this.post_avg_income = post_avg_income;
	}
	
}
