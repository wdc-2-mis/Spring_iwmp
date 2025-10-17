package app.projectevaluation.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ProductionDetailsBean {
	
	private Integer stcode;	
	private String stname;
	private Integer dcode;
	private String distname;
	private Integer totproj;	
	private BigDecimal prefpoturnover;
	private BigDecimal midfpoturnover;
	private BigDecimal controlfpoturnover;
	private BigDecimal prefpoincome;
	private BigDecimal midfpoincome;
	private BigDecimal controlfpoincome;
	private BigDecimal preannualincomeshg;
	private BigDecimal midannualincomeshg;
	private BigDecimal controlannualincomeshg;

	private BigInteger precommunitybasedshg;
	private BigInteger midcommunitybasedshg;
	private BigInteger controlcommunitybasedshg; 
	private BigInteger precommunitybasedfpo;
	private BigInteger midcommunitybasedfpo;
	private BigInteger controlcommunitybasedfpo; 
	private BigInteger precommunitybasedug; 
	private BigInteger midcommunitybasedug; 
	private BigInteger controlcommunitybasedug; 
	private BigInteger prememberbasedshg; 
	private BigInteger midmemberbasedshg; 
	private BigInteger controlmemberbasedshg; 
	private BigInteger prememberbasedfpo; 
	private BigInteger midmemberbasedfpo; 
	private BigInteger controlmemberbasedfpo; 
	private BigInteger prememberbasedug; 
	private BigInteger midmemberbasedug; 
	private BigInteger controlmemberbasedug;
	private Integer proj_id;
	private String proj_name;
	private Character status;
	
	
	
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public Integer getProj_id() {
		return proj_id;
	}
	public void setProj_id(Integer proj_id) {
		this.proj_id = proj_id;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public Integer getStcode() {
		return stcode;
	}
	public void setStcode(Integer stcode) {
		this.stcode = stcode;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
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
	public Integer getTotproj() {
		return totproj;
	}
	public void setTotproj(Integer totproj) {
		this.totproj = totproj;
	}
	public BigDecimal getPrefpoturnover() {
		return prefpoturnover;
	}
	public void setPrefpoturnover(BigDecimal prefpoturnover) {
		this.prefpoturnover = prefpoturnover;
	}
	public BigDecimal getMidfpoturnover() {
		return midfpoturnover;
	}
	public void setMidfpoturnover(BigDecimal midfpoturnover) {
		this.midfpoturnover = midfpoturnover;
	}
	public BigDecimal getControlfpoturnover() {
		return controlfpoturnover;
	}
	public void setControlfpoturnover(BigDecimal controlfpoturnover) {
		this.controlfpoturnover = controlfpoturnover;
	}
	public BigDecimal getPrefpoincome() {
		return prefpoincome;
	}
	public void setPrefpoincome(BigDecimal prefpoincome) {
		this.prefpoincome = prefpoincome;
	}
	public BigDecimal getMidfpoincome() {
		return midfpoincome;
	}
	public void setMidfpoincome(BigDecimal midfpoincome) {
		this.midfpoincome = midfpoincome;
	}
	public BigDecimal getControlfpoincome() {
		return controlfpoincome;
	}
	public void setControlfpoincome(BigDecimal controlfpoincome) {
		this.controlfpoincome = controlfpoincome;
	}
	public BigDecimal getPreannualincomeshg() {
		return preannualincomeshg;
	}
	public void setPreannualincomeshg(BigDecimal preannualincomeshg) {
		this.preannualincomeshg = preannualincomeshg;
	}
	public BigDecimal getMidannualincomeshg() {
		return midannualincomeshg;
	}
	public void setMidannualincomeshg(BigDecimal midannualincomeshg) {
		this.midannualincomeshg = midannualincomeshg;
	}
	public BigDecimal getControlannualincomeshg() {
		return controlannualincomeshg;
	}
	public void setControlannualincomeshg(BigDecimal controlannualincomeshg) {
		this.controlannualincomeshg = controlannualincomeshg;
	}
	public BigInteger getPrecommunitybasedshg() {
		return precommunitybasedshg;
	}
	public void setPrecommunitybasedshg(BigInteger precommunitybasedshg) {
		this.precommunitybasedshg = precommunitybasedshg;
	}
	public BigInteger getMidcommunitybasedshg() {
		return midcommunitybasedshg;
	}
	public void setMidcommunitybasedshg(BigInteger midcommunitybasedshg) {
		this.midcommunitybasedshg = midcommunitybasedshg;
	}
	public BigInteger getControlcommunitybasedshg() {
		return controlcommunitybasedshg;
	}
	public void setControlcommunitybasedshg(BigInteger controlcommunitybasedshg) {
		this.controlcommunitybasedshg = controlcommunitybasedshg;
	}
	public BigInteger getPrecommunitybasedfpo() {
		return precommunitybasedfpo;
	}
	public void setPrecommunitybasedfpo(BigInteger precommunitybasedfpo) {
		this.precommunitybasedfpo = precommunitybasedfpo;
	}
	public BigInteger getMidcommunitybasedfpo() {
		return midcommunitybasedfpo;
	}
	public void setMidcommunitybasedfpo(BigInteger midcommunitybasedfpo) {
		this.midcommunitybasedfpo = midcommunitybasedfpo;
	}
	public BigInteger getControlcommunitybasedfpo() {
		return controlcommunitybasedfpo;
	}
	public void setControlcommunitybasedfpo(BigInteger controlcommunitybasedfpo) {
		this.controlcommunitybasedfpo = controlcommunitybasedfpo;
	}
	public BigInteger getPrecommunitybasedug() {
		return precommunitybasedug;
	}
	public void setPrecommunitybasedug(BigInteger precommunitybasedug) {
		this.precommunitybasedug = precommunitybasedug;
	}
	public BigInteger getMidcommunitybasedug() {
		return midcommunitybasedug;
	}
	public void setMidcommunitybasedug(BigInteger midcommunitybasedug) {
		this.midcommunitybasedug = midcommunitybasedug;
	}
	public BigInteger getControlcommunitybasedug() {
		return controlcommunitybasedug;
	}
	public void setControlcommunitybasedug(BigInteger controlcommunitybasedug) {
		this.controlcommunitybasedug = controlcommunitybasedug;
	}
	public BigInteger getPrememberbasedshg() {
		return prememberbasedshg;
	}
	public void setPrememberbasedshg(BigInteger prememberbasedshg) {
		this.prememberbasedshg = prememberbasedshg;
	}
	public BigInteger getMidmemberbasedshg() {
		return midmemberbasedshg;
	}
	public void setMidmemberbasedshg(BigInteger midmemberbasedshg) {
		this.midmemberbasedshg = midmemberbasedshg;
	}
	public BigInteger getControlmemberbasedshg() {
		return controlmemberbasedshg;
	}
	public void setControlmemberbasedshg(BigInteger controlmemberbasedshg) {
		this.controlmemberbasedshg = controlmemberbasedshg;
	}
	public BigInteger getPrememberbasedfpo() {
		return prememberbasedfpo;
	}
	public void setPrememberbasedfpo(BigInteger prememberbasedfpo) {
		this.prememberbasedfpo = prememberbasedfpo;
	}
	public BigInteger getMidmemberbasedfpo() {
		return midmemberbasedfpo;
	}
	public void setMidmemberbasedfpo(BigInteger midmemberbasedfpo) {
		this.midmemberbasedfpo = midmemberbasedfpo;
	}
	public BigInteger getControlmemberbasedfpo() {
		return controlmemberbasedfpo;
	}
	public void setControlmemberbasedfpo(BigInteger controlmemberbasedfpo) {
		this.controlmemberbasedfpo = controlmemberbasedfpo;
	}
	public BigInteger getPrememberbasedug() {
		return prememberbasedug;
	}
	public void setPrememberbasedug(BigInteger prememberbasedug) {
		this.prememberbasedug = prememberbasedug;
	}
	public BigInteger getMidmemberbasedug() {
		return midmemberbasedug;
	}
	public void setMidmemberbasedug(BigInteger midmemberbasedug) {
		this.midmemberbasedug = midmemberbasedug;
	}
	public BigInteger getControlmemberbasedug() {
		return controlmemberbasedug;
	}
	public void setControlmemberbasedug(BigInteger controlmemberbasedug) {
		this.controlmemberbasedug = controlmemberbasedug;
	}
	
	

}
