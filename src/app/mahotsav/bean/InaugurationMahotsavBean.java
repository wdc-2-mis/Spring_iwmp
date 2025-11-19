package app.mahotsav.bean;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class InaugurationMahotsavBean {
	
	private Integer district;
    private Integer block;
    private String date;
    private String location;
    private Integer male_participants;
    private Integer female_participants;
    private Integer central_ministers;
    private Integer state_ministers;
    private Integer parliament;
    private Integer assembly_members;
    private Integer council_members;
    private Integer others;
    private Integer gov_officials;
    
    private Integer bhoomipoojan;
    private Integer no_works_bhoomipoojan;
    private List<MultipartFile> photos_bhoomipoojan;
    
    private Integer lokarpan;
    private Integer no_works_lokarpan;
    private List<MultipartFile> photos_lokarpan;
    
    private Integer shramdaan;
    private Integer no_location_shramdaan;
    private Integer no_people_shramdaan;
    private List<MultipartFile> photos_shramdaan;
    
    private Integer forestry;
    private Integer area_plantation;
    private List<MultipartFile> photos_forestry;
    
    private Integer awarded;
    private Integer no_awards;
    private List<MultipartFile> photos_janbhagidari;
    
    
    
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public Integer getBlock() {
		return block;
	}
	public void setBlock(Integer block) {
		this.block = block;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getMale_participants() {
		return male_participants;
	}
	public void setMale_participants(Integer male_participants) {
		this.male_participants = male_participants;
	}
	public Integer getFemale_participants() {
		return female_participants;
	}
	public void setFemale_participants(Integer female_participants) {
		this.female_participants = female_participants;
	}
	public Integer getCentral_ministers() {
		return central_ministers;
	}
	public void setCentral_ministers(Integer central_ministers) {
		this.central_ministers = central_ministers;
	}
	public Integer getState_ministers() {
		return state_ministers;
	}
	public void setState_ministers(Integer state_ministers) {
		this.state_ministers = state_ministers;
	}
	public Integer getParliament() {
		return parliament;
	}
	public void setParliament(Integer parliament) {
		this.parliament = parliament;
	}
	public Integer getAssembly_members() {
		return assembly_members;
	}
	public void setAssembly_members(Integer assembly_members) {
		this.assembly_members = assembly_members;
	}
	public Integer getCouncil_members() {
		return council_members;
	}
	public void setCouncil_members(Integer council_members) {
		this.council_members = council_members;
	}
	public Integer getOthers() {
		return others;
	}
	public void setOthers(Integer others) {
		this.others = others;
	}
	public Integer getGov_officials() {
		return gov_officials;
	}
	public void setGov_officials(Integer gov_officials) {
		this.gov_officials = gov_officials;
	}
	public Integer getBhoomipoojan() {
		return bhoomipoojan;
	}
	public void setBhoomipoojan(Integer bhoomipoojan) {
		this.bhoomipoojan = bhoomipoojan;
	}
	public Integer getNo_works_bhoomipoojan() {
		return no_works_bhoomipoojan;
	}
	public void setNo_works_bhoomipoojan(Integer no_works_bhoomipoojan) {
		this.no_works_bhoomipoojan = no_works_bhoomipoojan;
	}
	public List<MultipartFile> getPhotos_bhoomipoojan() {
		return photos_bhoomipoojan;
	}
	public void setPhotos_bhoomipoojan(List<MultipartFile> photos_bhoomipoojan) {
		this.photos_bhoomipoojan = photos_bhoomipoojan;
	}
	public Integer getLokarpan() {
		return lokarpan;
	}
	public void setLokarpan(Integer lokarpan) {
		this.lokarpan = lokarpan;
	}
	public Integer getNo_works_lokarpan() {
		return no_works_lokarpan;
	}
	public void setNo_works_lokarpan(Integer no_works_lokarpan) {
		this.no_works_lokarpan = no_works_lokarpan;
	}
	public List<MultipartFile> getPhotos_lokarpan() {
		return photos_lokarpan;
	}
	public void setPhotos_lokarpan(List<MultipartFile> photos_lokarpan) {
		this.photos_lokarpan = photos_lokarpan;
	}
	public Integer getShramdaan() {
		return shramdaan;
	}
	public void setShramdaan(Integer shramdaan) {
		this.shramdaan = shramdaan;
	}
	public Integer getNo_location_shramdaan() {
		return no_location_shramdaan;
	}
	public void setNo_location_shramdaan(Integer no_location_shramdaan) {
		this.no_location_shramdaan = no_location_shramdaan;
	}
	public Integer getNo_people_shramdaan() {
		return no_people_shramdaan;
	}
	public void setNo_people_shramdaan(Integer no_people_shramdaan) {
		this.no_people_shramdaan = no_people_shramdaan;
	}
	public List<MultipartFile> getPhotos_shramdaan() {
		return photos_shramdaan;
	}
	public void setPhotos_shramdaan(List<MultipartFile> photos_shramdaan) {
		this.photos_shramdaan = photos_shramdaan;
	}
	public Integer getForestry() {
		return forestry;
	}
	public void setForestry(Integer forestry) {
		this.forestry = forestry;
	}
	public Integer getArea_plantation() {
		return area_plantation;
	}
	public void setArea_plantation(Integer area_plantation) {
		this.area_plantation = area_plantation;
	}
	public List<MultipartFile> getPhotos_forestry() {
		return photos_forestry;
	}
	public void setPhotos_forestry(List<MultipartFile> photos_forestry) {
		this.photos_forestry = photos_forestry;
	}
	public Integer getAwarded() {
		return awarded;
	}
	public void setAwarded(Integer awarded) {
		this.awarded = awarded;
	}
	public Integer getNo_awards() {
		return no_awards;
	}
	public void setNo_awards(Integer no_awards) {
		this.no_awards = no_awards;
	}
	public List<MultipartFile> getPhotos_janbhagidari() {
		return photos_janbhagidari;
	}
	public void setPhotos_janbhagidari(List<MultipartFile> photos_janbhagidari) {
		this.photos_janbhagidari = photos_janbhagidari;
	}
    
    
    
    
    
}
