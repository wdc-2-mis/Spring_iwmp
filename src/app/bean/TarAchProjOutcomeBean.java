package app.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TarAchProjOutcomeBean {

	private Integer outcomeid;
	private String outcomedesc;
	private BigDecimal target;
	private String finyear;
	private BigDecimal achievement;
	private Integer outcome_detail_id;
	private Integer finyr;
	private BigInteger listsize;
	private Integer firstyear;
	private Integer lastyear;
	private Integer fyear;
	private String yeardesc;
	public BigDecimal getTarget() {
		return target;
	}
	public void setTarget(BigDecimal target) {
		this.target = target;
	}
	public String getFinyear() {
		return finyear;
	}
	public void setFinyear(String finyear) {
		this.finyear = finyear;
	}
	public Integer getOutcomeid() {
		return outcomeid;
	}
	public void setOutcomeid(Integer outcomeid) {
		this.outcomeid = outcomeid;
	}
	public String getOutcomedesc() {
		return outcomedesc;
	}
	public void setOutcomedesc(String outcomedesc) {
		this.outcomedesc = outcomedesc;
	}
	public BigDecimal getAchievement() {
		return achievement;
	}
	public void setAchievement(BigDecimal achievement) {
		this.achievement = achievement;
	}
	public Integer getOutcome_detail_id() {
		return outcome_detail_id;
	}
	public void setOutcome_detail_id(Integer outcome_detail_id) {
		this.outcome_detail_id = outcome_detail_id;
	}
	public Integer getFinyr() {
		return finyr;
	}
	public void setFinyr(Integer finyr) {
		this.finyr = finyr;
	}
	public Integer getfyear() {
		return fyear;
	}
	public void setfYear(Integer fyear) {
		this.fyear = fyear;
	}
	public Integer getLastyear() {
		return lastyear;
	}
	public void setLastyear(Integer lastyear) {
		this.lastyear = lastyear;
	}
	public Integer getFyear() {
		return fyear;
	}
	public void setFyear(Integer fyear) {
		this.fyear = fyear;
	}
	public BigInteger getListsize() {
		return listsize;
	}
	public void setListsize(BigInteger listsize) {
		this.listsize = listsize;
	}
	public String getYeardesc() {
		return yeardesc;
	}
	public void setYeardesc(String yeardesc) {
		this.yeardesc = yeardesc;
	}
	
	
	
}
