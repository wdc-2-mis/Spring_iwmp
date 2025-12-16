package app.mahotsav.bean;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class WatershedMahotsavProjectLevelBean {

    private Integer waterid;
    private String datetime;
    private Integer district;
    private Integer block;
    private String location;

    private Integer maleparticipants;
    private Integer femaleparticipants;
    private Integer centralministers;
    private Integer stateministers;
    private Integer membersofparliament;
    private Integer legassemblymembers;
    private Integer legcouncilmembers;
    private Integer publicreps;
    private Integer govofficials;

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
    private Integer st_code;
    private String stname;
    private String distname;
    private String blockname;
    private String projname;
    private Integer project;
    private Integer image_count;
    private Character status;
	public Integer getWaterid() {
		return waterid;
	}
	public void setWaterid(Integer waterid) {
		this.waterid = waterid;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getMaleparticipants() {
		return maleparticipants;
	}
	public void setMaleparticipants(Integer maleparticipants) {
		this.maleparticipants = maleparticipants;
	}
	public Integer getFemaleparticipants() {
		return femaleparticipants;
	}
	public void setFemaleparticipants(Integer femaleparticipants) {
		this.femaleparticipants = femaleparticipants;
	}
	public Integer getCentralministers() {
		return centralministers;
	}
	public void setCentralministers(Integer centralministers) {
		this.centralministers = centralministers;
	}
	public Integer getStateministers() {
		return stateministers;
	}
	public void setStateministers(Integer stateministers) {
		this.stateministers = stateministers;
	}
	public Integer getMembersofparliament() {
		return membersofparliament;
	}
	public void setMembersofparliament(Integer membersofparliament) {
		this.membersofparliament = membersofparliament;
	}
	public Integer getLegassemblymembers() {
		return legassemblymembers;
	}
	public void setLegassemblymembers(Integer legassemblymembers) {
		this.legassemblymembers = legassemblymembers;
	}
	public Integer getLegcouncilmembers() {
		return legcouncilmembers;
	}
	public void setLegcouncilmembers(Integer legcouncilmembers) {
		this.legcouncilmembers = legcouncilmembers;
	}
	public Integer getPublicreps() {
		return publicreps;
	}
	public void setPublicreps(Integer publicreps) {
		this.publicreps = publicreps;
	}
	public Integer getGovofficials() {
		return govofficials;
	}
	public void setGovofficials(Integer govofficials) {
		this.govofficials = govofficials;
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
	public String getProjname() {
		return projname;
	}
	public void setProjname(String projname) {
		this.projname = projname;
	}
	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
	public Integer getImage_count() {
		return image_count;
	}
	public void setImage_count(Integer image_count) {
		this.image_count = image_count;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
    
    
    
	
    
}
