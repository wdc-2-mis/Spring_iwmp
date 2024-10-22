package app.bean;

public class StateToVillageBean {
	
	private String state_codelgd;
	private String st_name;
	private Integer district_codelgd;
	private String dist_name;
	private Integer  block_codelgd;
	private String block_name;
	private Integer gram_panchayat_lgd_code;
	private String gram_panchayat_name;
	private Integer village_lgdcode;
	private String village_name;
	private Boolean blockactive;
	private Boolean gpactive;
	private Boolean villageactive;
	
	
	public String getState_codelgd() {
		return state_codelgd;
	}
	public void setState_codelgd(String state_codelgd) {
		this.state_codelgd = state_codelgd;
	}
	public Integer getDistrict_codelgd() {
		return district_codelgd;
	}
	public void setDistrict_codelgd(Integer district_codelgd) {
		this.district_codelgd = district_codelgd;
	}
	public Integer getBlock_codelgd() {
		return block_codelgd;
	}
	public void setBlock_codelgd(Integer block_codelgd) {
		this.block_codelgd = block_codelgd;
	}
	public Integer getGram_panchayat_lgd_code() {
		return gram_panchayat_lgd_code;
	}
	public void setGram_panchayat_lgd_code(Integer gram_panchayat_lgd_code) {
		this.gram_panchayat_lgd_code = gram_panchayat_lgd_code;
	}
	public Integer getVillage_lgdcode() {
		return village_lgdcode;
	}
	public void setVillage_lgdcode(Integer village_lgdcode) {
		this.village_lgdcode = village_lgdcode;
	}
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	
	public String getDist_name() {
		return dist_name;
	}
	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}
	public String getBlock_name() {
		return block_name;
	}
	public void setBlock_name(String block_name) {
		this.block_name = block_name;
	}
	public String getGram_panchayat_name() {
		return gram_panchayat_name;
	}
	public void setGram_panchayat_name(String gram_panchayat_name) {
		this.gram_panchayat_name = gram_panchayat_name;
	}
	public String getVillage_name() {
		return village_name;
	}
	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}
	public Boolean getBlockactive() {
		return blockactive;
	}
	public void setBlockactive(Boolean blockactive) {
		this.blockactive = blockactive;
	}
	public Boolean getGpactive() {
		return gpactive;
	}
	public void setGpactive(Boolean gpactive) {
		this.gpactive = gpactive;
	}
	public Boolean getVillageactive() {
		return villageactive;
	}
	public void setVillageactive(Boolean villageactive) {
		this.villageactive = villageactive;
	}
	
	
	

}
