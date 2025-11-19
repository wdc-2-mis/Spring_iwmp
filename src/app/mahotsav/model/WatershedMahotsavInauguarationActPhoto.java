package app.mahotsav.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="watershed_mahotsav_inauguaration_act_photo" ,schema="public")
public class WatershedMahotsavInauguarationActPhoto {
	
	
	private int photoId;
	private WatershedMahotsavInauguaration watershedMahotsavInauguaration;
	private Integer actId;
	private String photoUrl;
	private String longitute;
	private String latitude;
	private String requestedIp;
	private String updatedBy;
	private Date updated_date;
    private String createdBy;
    private Date created_date;
    
    public WatershedMahotsavInauguarationActPhoto() { }
    
    public WatershedMahotsavInauguarationActPhoto(Integer photoId) {
    	this.photoId = photoId;
    }
    
    public WatershedMahotsavInauguarationActPhoto(Integer photoId, WatershedMahotsavInauguaration watershedMahotsavInauguaration, Integer actId, String photoUrl,
    		String longitute, String latitude, String requestedIp, String updatedBy, Date updated_date, String createdBy, Date created_date) {
    	
    	this.photoId = photoId;
    	this.watershedMahotsavInauguaration = watershedMahotsavInauguaration;
    	this.actId = actId;
    	this.photoUrl = photoUrl;
    	this.longitute = longitute;
    	this.latitude = latitude;
    	this.requestedIp = requestedIp;
    	this.updatedBy = updatedBy;
    	this.updated_date = updated_date;
    	this.createdBy = createdBy;
    	this.created_date = created_date;
    	
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="photo_id", unique=true, nullable=false)
	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "inauguaration_id")
	public WatershedMahotsavInauguaration getWatershedMahotsavInauguaration() {
		return watershedMahotsavInauguaration;
	}

	public void setWatershedMahotsavInauguaration(WatershedMahotsavInauguaration watershedMahotsavInauguaration) {
		this.watershedMahotsavInauguaration = watershedMahotsavInauguaration;
	}
	@Column(name = "act_id")
	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
	@Column(name = "photo_url")
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	@Column(name = "longitute")
	public String getLongitute() {
		return longitute;
	}

	public void setLongitute(String longitute) {
		this.longitute = longitute;
	}
	@Column(name = "latitude")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@Column(name = "requested_ip", length=20)
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}
	@Column(name = "updated_by", length=25)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="updated_date", length=13)
	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}
	@Column(name = "created_by", length=25)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="created_date", length=13)
	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
    
    
    
    
    
    
    
    

}
