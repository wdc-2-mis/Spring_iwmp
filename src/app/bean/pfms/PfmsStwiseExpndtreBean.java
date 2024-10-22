package app.bean.pfms;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PfmsStwiseExpndtreBean {

	private int stcode;
	private String stname;
	private int dcode;
	private String distname;
	private BigDecimal dexpndtr;
	private BigDecimal bexpndtr;
	private BigDecimal gexpndtr;
	private BigDecimal vexpndtr;
	private BigInteger totaltranx;
	private BigInteger mappedtranx;
	private BigInteger mappedworkid;
	
	public BigInteger getMappedworkid() {
		return mappedworkid;
	}
	public void setMappedworkid(BigInteger mappedworkid) {
		this.mappedworkid = mappedworkid;
	}
	public int getStcode() {
		return stcode;
	}
	public void setStcode(int stcode) {
		this.stcode = stcode;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public int getDcode() {
		return dcode;
	}
	public void setDcode(int dcode) {
		this.dcode = dcode;
	}
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public BigDecimal getDexpndtr() {
		return dexpndtr;
	}
	public void setDexpndtr(BigDecimal dexpndtr) {
		this.dexpndtr = dexpndtr;
	}
	public BigDecimal getBexpndtr() {
		return bexpndtr;
	}
	public void setBexpndtr(BigDecimal bexpndtr) {
		this.bexpndtr = bexpndtr;
	}
	public BigDecimal getGexpndtr() {
		return gexpndtr;
	}
	public void setGexpndtr(BigDecimal gexpndtr) {
		this.gexpndtr = gexpndtr;
	}
	public BigDecimal getVexpndtr() {
		return vexpndtr;
	}
	public void setVexpndtr(BigDecimal vexpndtr) {
		this.vexpndtr = vexpndtr;
	}
	public BigInteger getTotaltranx() {
		return totaltranx;
	}
	public void setTotaltranx(BigInteger totaltranx) {
		this.totaltranx = totaltranx;
	}
	public BigInteger getMappedtranx() {
		return mappedtranx;
	}
	public void setMappedtranx(BigInteger mappedtranx) {
		this.mappedtranx = mappedtranx;
	}
	@Override
	public String toString() {
		return "PfmsStwiseExpndtreBean [stcode=" + stcode + ", stname=" + stname + ", dcode=" + dcode + ", distname=" +distname + ", dexpndtr=" + dexpndtr
				+ ", bexpndtr=" + bexpndtr + ", gexpndtr=" + gexpndtr + ", vexpndtr=" + vexpndtr + ", totaltranx=" + totaltranx + ", mappedtranx=" + mappedtranx + "]";
	}
	
	
}
