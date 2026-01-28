package app.punarutthan.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class WatershedPunarutthanBean {
	
	
	private Integer st_code;       
    private Integer district1;    
    private String project;       
    private Integer village1;  
    private Integer structure;
    private Integer work;
    private BigDecimal wdf;
    private BigDecimal mgnrega;
    private BigDecimal other;
    
    private List<MultipartFile> photos;
    private List<String> latitude;   
    private List<String> longitute;
    private List<String> photoTimestamp;
    
    private String stname;               
    private Integer district;           
    private String distname;             
    private Integer block;     
    private Integer plan_id;
    private Integer dcode;
    private String project_cd;
    private String proj_name;
    private Integer vcode;
    private String villagename;
    private Integer structure_id;
    private String structurename;
    private Integer no_work;
    private BigDecimal totalcost;
   
	public Integer getDistrict1() {
		return district1;
	}
	public void setDistrict1(Integer district1) {
		this.district1 = district1;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Integer getVillage1() {
		return village1;
	}
	public void setVillage1(Integer village1) {
		this.village1 = village1;
	}
	public Integer getStructure() {
		return structure;
	}
	public void setStructure(Integer structure) {
		this.structure = structure;
	}
	public Integer getWork() {
		return work;
	}
	public void setWork(Integer work) {
		this.work = work;
	}
	public BigDecimal getWdf() {
		return wdf;
	}
	public void setWdf(BigDecimal wdf) {
		this.wdf = wdf;
	}
	public BigDecimal getMgnrega() {
		return mgnrega;
	}
	public void setMgnrega(BigDecimal mgnrega) {
		this.mgnrega = mgnrega;
	}
	public BigDecimal getOther() {
		return other;
	}
	public void setOther(BigDecimal other) {
		this.other = other;
	}
	public List<MultipartFile> getPhotos() {
		return photos;
	}
	public void setPhotos(List<MultipartFile> photos) {
		this.photos = photos;
	}
	public List<String> getLatitude() {
		return latitude;
	}
	public void setLatitude(List<String> latitude) {
		this.latitude = latitude;
	}
	public List<String> getLongitute() {
		return longitute;
	}
	public void setLongitute(List<String> longitute) {
		this.longitute = longitute;
	}
	public List<String> getPhotoTimestamp() {
		return photoTimestamp;
	}
	public void setPhotoTimestamp(List<String> photoTimestamp) {
		this.photoTimestamp = photoTimestamp;
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
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public Integer getBlock() {
		return block;
	}
	public void setBlock(Integer block) {
		this.block = block;
	}
	public Integer getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(Integer plan_id) {
		this.plan_id = plan_id;
	}
	public Integer getDcode() {
		return dcode;
	}
	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}
	public String getProject_cd() {
		return project_cd;
	}
	public void setProject_cd(String project_cd) {
		this.project_cd = project_cd;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public Integer getVcode() {
		return vcode;
	}
	public void setVcode(Integer vcode) {
		this.vcode = vcode;
	}
	public String getVillagename() {
		return villagename;
	}
	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}
	public Integer getStructure_id() {
		return structure_id;
	}
	public void setStructure_id(Integer structure_id) {
		this.structure_id = structure_id;
	}
	public String getStructurename() {
		return structurename;
	}
	public void setStructurename(String structurename) {
		this.structurename = structurename;
	}
	public Integer getNo_work() {
		return no_work;
	}
	public void setNo_work(Integer no_work) {
		this.no_work = no_work;
	}
	public BigDecimal getTotalcost() {
		return totalcost;
	}
	public void setTotalcost(BigDecimal totalcost) {
		this.totalcost = totalcost;
	}
	 
	

}
