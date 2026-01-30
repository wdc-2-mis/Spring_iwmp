package app.punarutthan.model;

import java.sql.Timestamp;
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
@Table(name="wdcpmksy1_punarutthan_plan_implementation_photo" ,schema="public")
public class Wdcpmksy1PunarutthanPlanImplementationPhoto {
	
	private int implementationPhotoId;
	private Wdcpmksy1PunarutthanPlanImplementation wdcpmksy1PunarutthanPlanImplementation;
    private String photoUrl;
	private String longitute;
	private String latitude;
	private String requestedIp;
	private String updatedBy;
	private Date updated_date;
    private String createdBy;
    private Date created_date;
    private Timestamp photo_timestamp;
    
    
    public Wdcpmksy1PunarutthanPlanImplementationPhoto() {}
    
    public Wdcpmksy1PunarutthanPlanImplementationPhoto(int implementationPhotoId, Wdcpmksy1PunarutthanPlanImplementation wdcpmksy1PunarutthanPlanImplementation, String photoUrl, String longitute, String latitude, Timestamp photo_timestamp) {
    	
    	this.implementationPhotoId=implementationPhotoId;
    	this.wdcpmksy1PunarutthanPlanImplementation=wdcpmksy1PunarutthanPlanImplementation;
        this.photoUrl=photoUrl;
 	    this.longitute=longitute;
 	    this.latitude=latitude;
 	    this.photo_timestamp=photo_timestamp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="implementation_photo_id", unique=true, nullable=false)
	public int getImplementationPhotoId() {
		return implementationPhotoId;
	}

	public void setImplementationPhotoId(int implementationPhotoId) {
		this.implementationPhotoId = implementationPhotoId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "implementation_id")
	public Wdcpmksy1PunarutthanPlanImplementation getWdcpmksy1PunarutthanPlanImplementation() {
		return wdcpmksy1PunarutthanPlanImplementation;
	}

	public void setWdcpmksy1PunarutthanPlanImplementation(
			Wdcpmksy1PunarutthanPlanImplementation wdcpmksy1PunarutthanPlanImplementation) {
		this.wdcpmksy1PunarutthanPlanImplementation = wdcpmksy1PunarutthanPlanImplementation;
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
	@Column(name="photo_timestamp")
	public Timestamp getPhoto_timestamp() {
		return photo_timestamp;
	}

	public void setPhoto_timestamp(Timestamp photo_timestamp) {
		this.photo_timestamp = photo_timestamp;
	}
    
    

}
