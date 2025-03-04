package app.bean;

import java.math.BigInteger;

public class WatershedYatraDashboardChartBean {
	
	BigInteger totplannedloc;
	BigInteger completedyatraloc;
	BigInteger totplannedact;
	BigInteger totcompletedact;
	
	
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
	
	
	
}
