package app.bean;

import java.math.BigDecimal;

public class VillageRecordBean {
	
	private Integer sno;
	private String st_name;
	private String district;
	private String block;
	private String village;
	private Integer lgd_code_village;
	private String international_border;
	private BigDecimal distancefromlac;
	private Integer st_code;
	private Integer dist_code;
	private Integer bcode;
	private Integer vcode;
	private Boolean status;
	
	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public Integer getLgd_code_village() {
		return lgd_code_village;
	}
	public void setLgd_code_village(Integer lgd_code_village) {
		this.lgd_code_village = lgd_code_village;
	}
	public String getInternational_border() {
		return international_border;
	}
	public void setInternation_border(String international_border) {
		this.international_border = international_border;
	}
	public BigDecimal getDistancefromlac() {
		return distancefromlac;
	}
	public void setDistancefromlac(BigDecimal distancefromlac) {
		this.distancefromlac = distancefromlac;
	}
	public Integer getSt_code() {
		return st_code;
	}
	public void setSt_code(Integer st_code) {
		this.st_code = st_code;
	}
	public Integer getDist_code() {
		return dist_code;
	}
	public void setDist_code(Integer dist_code) {
		this.dist_code = dist_code;
	}
	public Integer getBcode() {
		return bcode;
	}
	public void setBcode(Integer bcode) {
		this.bcode = bcode;
	}
	public Integer getVcode() {
		return vcode;
	}
	public void setVcode(Integer vcode) {
		this.vcode = vcode;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

}
