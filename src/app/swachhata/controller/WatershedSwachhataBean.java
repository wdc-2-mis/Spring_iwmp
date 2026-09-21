package app.swachhata.controller;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class WatershedSwachhataBean {
	
	
	private Integer st_code;
    private String stname;
    private String distname;
    private String blockname;
    private String projname;
    private Integer project;
    private Integer image_count;
    private Character status;
	private Integer waterid;
    private String datetime;
    private Integer district;
    private Integer block;
    private Integer village;
    private String location;

    private Integer shg;
    private Integer ug;
    private Integer fpo;
    private Integer youth;
    private Integer other;
    private Integer total;

    private Integer sapling;
    private Integer no_sapling;
    private List<MultipartFile> photos_sapling;
    private List<String> photos_sapling_lat;
    private List<String> photos_sapling_lng;
    private List<String> photos_sapling_time;
    
    private Integer lokarpan;
    private Integer no_works_lokarpan;
    private List<MultipartFile> photos_lokarpan;
    private List<String> photos_lokarpan_lat;
    private List<String> photos_lokarpan_lng;
    private List<String> photos_lokarpan_time;
    
    private Integer shramdaan;
    private Integer no_location_shramdaan;
    private List<MultipartFile> photos_shramdaan;
    private List<String> photos_shramdaan_lat;
    private List<String> photos_shramdaan_lng;
    private List<String> photos_shramdaan_time;
    
    private Integer cleanliness;
    private Integer no_cleanliness;
    private List<MultipartFile> photos_cleanliness;
    private List<String> photos_cleanliness_lat;
    private List<String> photos_cleanliness_lng;
    private List<String> photos_cleanliness_time;
    
    private Integer awareness;
    private Integer no_awareness;
    private List<MultipartFile> photos_awareness;
    private List<String> photos_awareness_lat;
    private List<String> photos_awareness_lng;
    private List<String> photos_awareness_time;
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
	public Integer getVillage() {
		return village;
	}
	public void setVillage(Integer village) {
		this.village = village;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getShg() {
		return shg;
	}
	public void setShg(Integer shg) {
		this.shg = shg;
	}
	public Integer getUg() {
		return ug;
	}
	public void setUg(Integer ug) {
		this.ug = ug;
	}
	public Integer getFpo() {
		return fpo;
	}
	public void setFpo(Integer fpo) {
		this.fpo = fpo;
	}
	public Integer getYouth() {
		return youth;
	}
	public void setYouth(Integer youth) {
		this.youth = youth;
	}
	public Integer getOther() {
		return other;
	}
	public void setOther(Integer other) {
		this.other = other;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getSapling() {
		return sapling;
	}
	public void setSapling(Integer sapling) {
		this.sapling = sapling;
	}
	public Integer getNo_sapling() {
		return no_sapling;
	}
	public void setNo_sapling(Integer no_sapling) {
		this.no_sapling = no_sapling;
	}
	public List<MultipartFile> getPhotos_sapling() {
		return photos_sapling;
	}
	public void setPhotos_sapling(List<MultipartFile> photos_sapling) {
		this.photos_sapling = photos_sapling;
	}
	public List<String> getPhotos_sapling_lat() {
		return photos_sapling_lat;
	}
	public void setPhotos_sapling_lat(List<String> photos_sapling_lat) {
		this.photos_sapling_lat = photos_sapling_lat;
	}
	public List<String> getPhotos_sapling_lng() {
		return photos_sapling_lng;
	}
	public void setPhotos_sapling_lng(List<String> photos_sapling_lng) {
		this.photos_sapling_lng = photos_sapling_lng;
	}
	public List<String> getPhotos_sapling_time() {
		return photos_sapling_time;
	}
	public void setPhotos_sapling_time(List<String> photos_sapling_time) {
		this.photos_sapling_time = photos_sapling_time;
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
	public Integer getCleanliness() {
		return cleanliness;
	}
	public void setCleanliness(Integer cleanliness) {
		this.cleanliness = cleanliness;
	}
	public Integer getNo_cleanliness() {
		return no_cleanliness;
	}
	public void setNo_cleanliness(Integer no_cleanliness) {
		this.no_cleanliness = no_cleanliness;
	}
	public List<MultipartFile> getPhotos_cleanliness() {
		return photos_cleanliness;
	}
	public void setPhotos_cleanliness(List<MultipartFile> photos_cleanliness) {
		this.photos_cleanliness = photos_cleanliness;
	}
	public List<String> getPhotos_cleanliness_lat() {
		return photos_cleanliness_lat;
	}
	public void setPhotos_cleanliness_lat(List<String> photos_cleanliness_lat) {
		this.photos_cleanliness_lat = photos_cleanliness_lat;
	}
	public List<String> getPhotos_cleanliness_lng() {
		return photos_cleanliness_lng;
	}
	public void setPhotos_cleanliness_lng(List<String> photos_cleanliness_lng) {
		this.photos_cleanliness_lng = photos_cleanliness_lng;
	}
	public List<String> getPhotos_cleanliness_time() {
		return photos_cleanliness_time;
	}
	public void setPhotos_cleanliness_time(List<String> photos_cleanliness_time) {
		this.photos_cleanliness_time = photos_cleanliness_time;
	}
	public Integer getAwareness() {
		return awareness;
	}
	public void setAwareness(Integer awareness) {
		this.awareness = awareness;
	}
	public Integer getNo_awareness() {
		return no_awareness;
	}
	public void setNo_awareness(Integer no_awareness) {
		this.no_awareness = no_awareness;
	}
	public List<MultipartFile> getPhotos_awareness() {
		return photos_awareness;
	}
	public void setPhotos_awareness(List<MultipartFile> photos_awareness) {
		this.photos_awareness = photos_awareness;
	}
	public List<String> getPhotos_awareness_lat() {
		return photos_awareness_lat;
	}
	public void setPhotos_awareness_lat(List<String> photos_awareness_lat) {
		this.photos_awareness_lat = photos_awareness_lat;
	}
	public List<String> getPhotos_awareness_lng() {
		return photos_awareness_lng;
	}
	public void setPhotos_awareness_lng(List<String> photos_awareness_lng) {
		this.photos_awareness_lng = photos_awareness_lng;
	}
	public List<String> getPhotos_awareness_time() {
		return photos_awareness_time;
	}
	public void setPhotos_awareness_time(List<String> photos_awareness_time) {
		this.photos_awareness_time = photos_awareness_time;
	}
    
    

}
