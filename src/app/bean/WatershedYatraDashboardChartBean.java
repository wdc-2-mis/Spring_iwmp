package app.bean;

import java.math.BigInteger;

public class WatershedYatraDashboardChartBean {
	
	BigInteger totplannedloc;
	BigInteger completedyatraloc;
	BigInteger totplannedact;
	BigInteger totcompletedact;
	public Integer statecode;
	public String st_name;
	public String yatradate;
    public BigInteger total_participants;
	public Integer coveredlocations;
    public Integer sumtotal_participants;
	
	public BigInteger getTotplannedloc() {
		return totplannedloc;
	}
	public void setTotplannedloc(BigInteger totplannedloc) {
		this.totplannedloc = totplannedloc;
	}
	public BigInteger getCompletedyatraloc() {
		return completedyatraloc;
	}
	public void setCompletedyatraloc(BigInteger completedyatraloc) {
		this.completedyatraloc = completedyatraloc;
	}
	public BigInteger getTotplannedact() {
		return totplannedact;
	}
	public void setTotplannedact(BigInteger totplannedact) {
		this.totplannedact = totplannedact;
	}
	public BigInteger getTotcompletedact() {
		return totcompletedact;
	}
	public void setTotcompletedact(BigInteger totcompletedact) {
		this.totcompletedact = totcompletedact;
	}
	public Integer getStatecode() {
		return statecode;
	}
	public void setStatecode(Integer statecode) {
		this.statecode = statecode;
	}
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public String getYatradate() {
		return yatradate;
	}
	public void setYatradate(String yatradate) {
		this.yatradate = yatradate;
	}
	public BigInteger getTotal_participants() {
		return total_participants;
	}
	public void setTotal_participants(BigInteger total_participants) {
		this.total_participants = total_participants;
	}
	public Integer getCoveredlocations() {
		return coveredlocations;
	}
	public void setCoveredlocations(Integer coveredlocations) {
		this.coveredlocations = coveredlocations;
	}
	public Integer getSumtotal_participants() {
		return sumtotal_participants;
	}
	public void setSumtotal_participants(Integer sumtotal_participants) {
		this.sumtotal_participants = sumtotal_participants;
	}
	
	
	
}
