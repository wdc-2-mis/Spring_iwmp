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
    private List<String> photos_bhoomipoojan_lat;
    private List<String> photos_bhoomipoojan_lng;
    private List<String> photos_bhoomipoojan_time;
    
    private Integer lokarpan;
    private Integer no_works_lokarpan;
    private List<MultipartFile> photos_lokarpan;
    private List<String> lokarpan_lat;
    private List<String> lokarpan_lng;
    private List<String> lokarpan_time;
    
    private Integer shramdaan;
    private Integer no_location_shramdaan;
    private Integer no_people_shramdaan;
    private List<MultipartFile> photos_shramdaan;
    private List<String> shramdaan_lat;
    private List<String> shramdaan_lng;
    private List<String> shramdaan_time;
    
    private Integer forestry;
    private Integer area_plantation;
    private List<MultipartFile> photos_forestry;
    private List<String> forestry_lat;
    private List<String> forestry_lng;
    private List<String> forestry_time;
    
    private Integer awarded;
    private Integer no_awards;
    private List<MultipartFile> photos_janbhagidari;
    private List<String> janbhagidari_lat;
    private List<String> janbhagidari_lng;
    private List<String> janbhagidari_time;
    
    
    
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
	public List<String> getPhotos_bhoomipoojan_lat() {
		return photos_bhoomipoojan_lat;
	}
	public void setPhotos_bhoomipoojan_lat(List<String> photos_bhoomipoojan_lat) {
		this.photos_bhoomipoojan_lat = photos_bhoomipoojan_lat;
	}
	public List<String> getPhotos_bhoomipoojan_lng() {
		return photos_bhoomipoojan_lng;
	}
	public void setPhotos_bhoomipoojan_lng(List<String> photos_bhoomipoojan_lng) {
		this.photos_bhoomipoojan_lng = photos_bhoomipoojan_lng;
	}
	public List<String> getPhotos_bhoomipoojan_time() {
		return photos_bhoomipoojan_time;
	}
	public void setPhotos_bhoomipoojan_time(List<String> photos_bhoomipoojan_time) {
		this.photos_bhoomipoojan_time = photos_bhoomipoojan_time;
	}
	public List<String> getLokarpan_lat() {
		return lokarpan_lat;
	}
	public void setLokarpan_lat(List<String> lokarpan_lat) {
		this.lokarpan_lat = lokarpan_lat;
	}
	public List<String> getLokarpan_lng() {
		return lokarpan_lng;
	}
	public void setLokarpan_lng(List<String> lokarpan_lng) {
		this.lokarpan_lng = lokarpan_lng;
	}
	public List<String> getLokarpan_time() {
		return lokarpan_time;
	}
	public void setLokarpan_time(List<String> lokarpan_time) {
		this.lokarpan_time = lokarpan_time;
	}
	public List<String> getShramdaan_lat() {
		return shramdaan_lat;
	}
	public void setShramdaan_lat(List<String> shramdaan_lat) {
		this.shramdaan_lat = shramdaan_lat;
	}
	public List<String> getShramdaan_lng() {
		return shramdaan_lng;
	}
	public void setShramdaan_lng(List<String> shramdaan_lng) {
		this.shramdaan_lng = shramdaan_lng;
	}
	public List<String> getShramdaan_time() {
		return shramdaan_time;
	}
	public void setShramdaan_time(List<String> shramdaan_time) {
		this.shramdaan_time = shramdaan_time;
	}
	public List<String> getForestry_lat() {
		return forestry_lat;
	}
	public void setForestry_lat(List<String> forestry_lat) {
		this.forestry_lat = forestry_lat;
	}
	public List<String> getForestry_lng() {
		return forestry_lng;
	}
	public void setForestry_lng(List<String> forestry_lng) {
		this.forestry_lng = forestry_lng;
	}
	public List<String> getForestry_time() {
		return forestry_time;
	}
	public void setForestry_time(List<String> forestry_time) {
		this.forestry_time = forestry_time;
	}
	public List<String> getJanbhagidari_lat() {
		return janbhagidari_lat;
	}
	public void setJanbhagidari_lat(List<String> janbhagidari_lat) {
		this.janbhagidari_lat = janbhagidari_lat;
	}
	public List<String> getJanbhagidari_lng() {
		return janbhagidari_lng;
	}
	public void setJanbhagidari_lng(List<String> janbhagidari_lng) {
		this.janbhagidari_lng = janbhagidari_lng;
	}
	public List<String> getJanbhagidari_time() {
		return janbhagidari_time;
	}
	public void setJanbhagidari_time(List<String> janbhagidari_time) {
		this.janbhagidari_time = janbhagidari_time;
	}
    
    
    
    
    
}
