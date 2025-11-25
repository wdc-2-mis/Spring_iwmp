package app.mahotsav.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    private List<String> photos_lokarpan_lat;
    private List<String> photos_lokarpan_lng;
    private List<String> photos_lokarpan_time;
    
    private Integer shramdaan;
    private Integer no_location_shramdaan;
    private Integer no_people_shramdaan;
    private List<MultipartFile> photos_shramdaan;
    private List<String> photos_shramdaan_lat;
    private List<String> photos_shramdaan_lng;
    private List<String> photos_shramdaan_time;
    
    private Integer forestry;
    private Integer area_plantation;
    private List<MultipartFile> photos_forestry;
    private List<String> photos_forestry_lat;
    private List<String> photos_forestry_lng;
    private List<String> photos_forestry_time;
    
    private Integer awarded;
    private Integer no_awards;
    private List<MultipartFile> photos_janbhagidari;
    private List<String> photos_janbhagidari_lat;
    private List<String> photos_janbhagidari_lng;
    private List<String> photos_janbhagidari_time;
    
    private Integer inauguaration_id;
    private Integer st_code;
    private String stname;
    private String distname;
    private String blockname;
    private Integer forestry_horticulture;
    private Integer image_count;
    private Integer participants;
    private Integer total_participation;
    
    
    private BigInteger pr_male;
    private BigInteger pr_female;
    private BigInteger pr_total_male_female;
    private BigInteger no_of_prabhat;
    private BigInteger total_prabhat_photo;
    private BigInteger no_of_projectlvl;
    private BigInteger pl_male;
    private BigInteger pl_female;
    private BigInteger pl_total_male_female;
    private BigInteger representatives;
    private BigInteger govt_officials;
    private BigInteger total_projlvl_photo;
    private BigInteger bhoomi_poojan;
    private BigInteger lokarpans;
    private BigInteger shramdaan_location;
    private BigInteger shramdaan_participated;
    private BigInteger no_of_agro;
    private BigInteger total_participations;
    
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
	
	public List<String> getPhotos_lokarpan_lat() {
		return photos_lokarpan_lat;
	}
	public void setPhotos_lokarpan_lat(List<String> photos_lokarpan_lat) {
		this.photos_lokarpan_lat = photos_lokarpan_lat;
	}
	public List<String> getPhotos_lokarpan_lng() {
		return photos_lokarpan_lng;
	}
	public void setPhotos_lokarpan_lng(List<String> photos_lokarpan_lng) {
		this.photos_lokarpan_lng = photos_lokarpan_lng;
	}
	public List<String> getPhotos_lokarpan_time() {
		return photos_lokarpan_time;
	}
	public void setPhotos_lokarpan_time(List<String> photos_lokarpan_time) {
		this.photos_lokarpan_time = photos_lokarpan_time;
	}
	public List<String> getPhotos_shramdaan_lat() {
		return photos_shramdaan_lat;
	}
	public void setPhotos_shramdaan_lat(List<String> photos_shramdaan_lat) {
		this.photos_shramdaan_lat = photos_shramdaan_lat;
	}
	public List<String> getPhotos_shramdaan_lng() {
		return photos_shramdaan_lng;
	}
	public void setPhotos_shramdaan_lng(List<String> photos_shramdaan_lng) {
		this.photos_shramdaan_lng = photos_shramdaan_lng;
	}
	public List<String> getPhotos_shramdaan_time() {
		return photos_shramdaan_time;
	}
	public void setPhotos_shramdaan_time(List<String> photos_shramdaan_time) {
		this.photos_shramdaan_time = photos_shramdaan_time;
	}
	public List<String> getPhotos_forestry_lat() {
		return photos_forestry_lat;
	}
	public void setPhotos_forestry_lat(List<String> photos_forestry_lat) {
		this.photos_forestry_lat = photos_forestry_lat;
	}
	public List<String> getPhotos_forestry_lng() {
		return photos_forestry_lng;
	}
	public void setPhotos_forestry_lng(List<String> photos_forestry_lng) {
		this.photos_forestry_lng = photos_forestry_lng;
	}
	public List<String> getPhotos_forestry_time() {
		return photos_forestry_time;
	}
	public void setPhotos_forestry_time(List<String> photos_forestry_time) {
		this.photos_forestry_time = photos_forestry_time;
	}
	public List<String> getPhotos_janbhagidari_lat() {
		return photos_janbhagidari_lat;
	}
	public void setPhotos_janbhagidari_lat(List<String> photos_janbhagidari_lat) {
		this.photos_janbhagidari_lat = photos_janbhagidari_lat;
	}
	public List<String> getPhotos_janbhagidari_lng() {
		return photos_janbhagidari_lng;
	}
	public void setPhotos_janbhagidari_lng(List<String> photos_janbhagidari_lng) {
		this.photos_janbhagidari_lng = photos_janbhagidari_lng;
	}
	public List<String> getPhotos_janbhagidari_time() {
		return photos_janbhagidari_time;
	}
	public void setPhotos_janbhagidari_time(List<String> photos_janbhagidari_time) {
		this.photos_janbhagidari_time = photos_janbhagidari_time;
	}
	public Integer getInauguaration_id() {
		return inauguaration_id;
	}
	public void setInauguaration_id(Integer inauguaration_id) {
		this.inauguaration_id = inauguaration_id;
	}
	public Integer getSt_code() {
		return st_code;
	}
	public void setSt_code(Integer st_code) {
		this.st_code = st_code;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public String getBlockname() {
		return blockname;
	}
	public void setBlockname(String blockname) {
		this.blockname = blockname;
	}
	public Integer getForestry_horticulture() {
		return forestry_horticulture;
	}
	public void setForestry_horticulture(Integer forestry_horticulture) {
		this.forestry_horticulture = forestry_horticulture;
	}
	public Integer getImage_count() {
		return image_count;
	}
	public void setImage_count(Integer image_count) {
		this.image_count = image_count;
	}
	public Integer getParticipants() {
		return participants;
	}
	public void setParticipants(Integer participants) {
		this.participants = participants;
	}
	public Integer getTotal_participation() {
		return total_participation;
	}
	public void setTotal_participation(Integer total_participation) {
		this.total_participation = total_participation;
	}
	public BigInteger getPr_male() {
		return pr_male;
	}
	public void setPr_male(BigInteger pr_male) {
		this.pr_male = pr_male;
	}
	public BigInteger getPr_female() {
		return pr_female;
	}
	public void setPr_female(BigInteger pr_female) {
		this.pr_female = pr_female;
	}
	public BigInteger getPr_total_male_female() {
		return pr_total_male_female;
	}
	public void setPr_total_male_female(BigInteger pr_total_male_female) {
		this.pr_total_male_female = pr_total_male_female;
	}
	public BigInteger getNo_of_prabhat() {
		return no_of_prabhat;
	}
	public void setNo_of_prabhat(BigInteger no_of_prabhat) {
		this.no_of_prabhat = no_of_prabhat;
	}
	public BigInteger getTotal_prabhat_photo() {
		return total_prabhat_photo;
	}
	public void setTotal_prabhat_photo(BigInteger total_prabhat_photo) {
		this.total_prabhat_photo = total_prabhat_photo;
	}
	public BigInteger getNo_of_projectlvl() {
		return no_of_projectlvl;
	}
	public void setNo_of_projectlvl(BigInteger no_of_projectlvl) {
		this.no_of_projectlvl = no_of_projectlvl;
	}
	public BigInteger getPl_male() {
		return pl_male;
	}
	public void setPl_male(BigInteger pl_male) {
		this.pl_male = pl_male;
	}
	public BigInteger getPl_female() {
		return pl_female;
	}
	public void setPl_female(BigInteger pl_female) {
		this.pl_female = pl_female;
	}
	public BigInteger getPl_total_male_female() {
		return pl_total_male_female;
	}
	public void setPl_total_male_female(BigInteger pl_total_male_female) {
		this.pl_total_male_female = pl_total_male_female;
	}
	public BigInteger getRepresentatives() {
		return representatives;
	}
	public void setRepresentatives(BigInteger representatives) {
		this.representatives = representatives;
	}
	public BigInteger getGovt_officials() {
		return govt_officials;
	}
	public void setGovt_officials(BigInteger govt_officials) {
		this.govt_officials = govt_officials;
	}
	public BigInteger getTotal_projlvl_photo() {
		return total_projlvl_photo;
	}
	public void setTotal_projlvl_photo(BigInteger total_projlvl_photo) {
		this.total_projlvl_photo = total_projlvl_photo;
	}
	public BigInteger getBhoomi_poojan() {
		return bhoomi_poojan;
	}
	public void setBhoomi_poojan(BigInteger bhoomi_poojan) {
		this.bhoomi_poojan = bhoomi_poojan;
	}
	public BigInteger getLokarpans() {
		return lokarpans;
	}
	public void setLokarpans(BigInteger lokarpans) {
		this.lokarpans = lokarpans;
	}
	public BigInteger getShramdaan_location() {
		return shramdaan_location;
	}
	public void setShramdaan_location(BigInteger shramdaan_location) {
		this.shramdaan_location = shramdaan_location;
	}
	public BigInteger getShramdaan_participated() {
		return shramdaan_participated;
	}
	public void setShramdaan_participated(BigInteger shramdaan_participated) {
		this.shramdaan_participated = shramdaan_participated;
	}
	public BigInteger getNo_of_agro() {
		return no_of_agro;
	}
	public void setNo_of_agro(BigInteger no_of_agro) {
		this.no_of_agro = no_of_agro;
	}
	public BigInteger getTotal_participations() {
		return total_participations;
	}
	public void setTotal_participations(BigInteger total_participations) {
		this.total_participations = total_participations;
	}
    
    
    
    
    
}
