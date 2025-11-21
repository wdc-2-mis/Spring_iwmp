package app.mahotsav.bean;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class WMPrabhatPheriBean {
	
	 private Integer prabhatpheri_id;
	 private String date1;           
	    private Integer stCode;       
	    private Integer district1;    
	    private Integer block1;       
	    private Integer village1;  
	    private Integer male_participants;
	    private Integer female_participants;
	    private List<MultipartFile> photos;
	    
	    private List<String> latitude;   
	    private List<String> longitute;
	    private List<String> photoTimestamp;
	    
	    
	    private Date prabhatpheri_date;      
	    private Integer st_code;             
	    private String stname;               
	    private Integer district;           
	    private String distname;             
	    private Integer block;               
	    private String blockname;            
	    private Integer village;            
	    private String villagename;          
	    private BigInteger photo_count;   
	    private String date; 
	    
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public Date getPrabhatpheri_date() {
			return prabhatpheri_date;
		}
		public void setPrabhatpheri_date(Date prabhatpheri_date) {
			this.prabhatpheri_date = prabhatpheri_date;
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
		public String getBlockname() {
			return blockname;
		}
		public void setBlockname(String blockname) {
			this.blockname = blockname;
		}
		public Integer getVillage() {
			return village;
		}
		public void setVillage(Integer village) {
			this.village = village;
		}
		public String getVillagename() {
			return villagename;
		}
		public void setVillagename(String villagename) {
			this.villagename = villagename;
		}
		public BigInteger getPhoto_count() {
			return photo_count;
		}
		public void setPhoto_count(BigInteger photo_count) {
			this.photo_count = photo_count;
		}
		public List<String> getPhotoTimestamp() {
			return photoTimestamp;
		}
		public void setPhotoTimestamp(List<String> photoTimestamp) {
			this.photoTimestamp = photoTimestamp;
		}
		public String getDate1() {
			return date1;
		}
		public void setDate1(String date1) {
			this.date1 = date1;
		}
		public Integer getStCode() {
			return stCode;
		}
		public void setStCode(Integer stCode) {
			this.stCode = stCode;
		}
		public Integer getDistrict1() {
			return district1;
		}
		public void setDistrict1(Integer district1) {
			this.district1 = district1;
		}
		public Integer getBlock1() {
			return block1;
		}
		public void setBlock1(Integer block1) {
			this.block1 = block1;
		}
		public Integer getVillage1() {
			return village1;
		}
		public void setVillage1(Integer village1) {
			this.village1 = village1;
		}
		public Integer getPrabhatpheri_id() {
			return prabhatpheri_id;
		}
		public void setPrabhatpheri_id(Integer prabhatpheri_id) {
			this.prabhatpheri_id = prabhatpheri_id;
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
		
	    
}
