package app.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ProjectLocationBean {
	
	
	private String grampanchayatname;
	private Integer blockcode;
	private int villagecode;
	private String villagename;
	private String blockname;
	private Integer vcode;
	private Boolean status;
	private String projectname;
	private BigInteger  projectlocid;
	private String mpdprojname;
	private String wcname;
	private int wcid;
	private int wcid1;
	private BigInteger projwcid;
	private String wccomma;
	
	private String dist_name;
	private Integer proj_id;
	private String locstatus;
	private BigDecimal plot_area_basel;
	private String basestatus;
	
	
	
	public String getWccomma() {
		return wccomma;
	}
	public void setWccomma(String wccomma) {
		this.wccomma = wccomma;
	}
	public BigInteger getProjwcid() {
		return projwcid;
	}
	public void setProjwcid(BigInteger projwcid) {
		this.projwcid = projwcid;
	}
	public BigInteger getProjectlocid() {
		return projectlocid;
	}
	public void setProjectlocid(BigInteger projectlocid) {
		this.projectlocid = projectlocid;
	}
	public int getWcid() {
		return wcid;
	}
	public void setWcid(int wcid) {
		this.wcid = wcid;
	}
	private int projid;
	public int getProjid() {
		return projid;
	}
	public void setProjid(int projid) {
		this.projid = projid;
	}
	public String getMpdprojname() {
		return mpdprojname;
	}
	public void setMpdprojname(String mpdprojname) {
		this.mpdprojname = mpdprojname;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getWcname() {
		return wcname;
	}
	public void setWcname(String wcname) {
		this.wcname = wcname;
	}
	public Integer getBlockcode() {
		return blockcode;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public void setBlockcode(Integer blockcode) {
		this.blockcode = blockcode;
	}
	public int getVillagecode() {
		return villagecode;
	}
	public void setVillagecode(int villagecode) {
		this.villagecode = villagecode;
	}

	public Integer getVcode() {
		return vcode;
	}
	public void setVcode(Integer vcode) {
		this.vcode = vcode;
	}
	public String getBlockname() {
		return blockname;
	}
	public void setBlockname(String blockname) {
		this.blockname = blockname;
	}
	public String getGrampanchayatname() {
		return grampanchayatname;
	}
	public void setGrampanchayatname(String grampanchayatname) {
		this.grampanchayatname = grampanchayatname;
	}
	public String getVillagename() {
		return villagename;
	}
	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}
	public int getWcid1() {
		return wcid1;
	}
	public void setWcid1(int wcid1) {
		this.wcid1 = wcid1;
	}
	public Integer getProj_id() {
		return proj_id;
	}
	public void setProj_id(Integer proj_id) {
		this.proj_id = proj_id;
	}
	public String getLocstatus() {
		return locstatus;
	}
	public void setLocstatus(String locstatus) {
		this.locstatus = locstatus;
	}
	public BigDecimal getPlot_area_basel() {
		return plot_area_basel;
	}
	public void setPlot_area_basel(BigDecimal plot_area_basel) {
		this.plot_area_basel = plot_area_basel;
	}
	public String getBasestatus() {
		return basestatus;
	}
	public void setBasestatus(String basestatus) {
		this.basestatus = basestatus;
	}
	public String getDist_name() {
		return dist_name;
	}
	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}

	
	
	
	
}
