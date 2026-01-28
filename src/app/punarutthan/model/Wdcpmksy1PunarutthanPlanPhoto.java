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
@Table(name="wdcpmksy1_punarutthan_plan_photo" ,schema="public")
public class Wdcpmksy1PunarutthanPlanPhoto {
	
	private int planPhotoId;
	private Wdcpmksy1PunarutthanPlan wdcpmksy1PunarutthanPlan;
    private String photoUrl;
	private String longitute;
	private String latitude;
	private String requestedIp;
	private String updatedBy;
	private Date updated_date;
    private String createdBy;
    private Date created_date;
    private Timestamp photo_timestamp;
    
    
   public Wdcpmksy1PunarutthanPlanPhoto() {} 
   
   public Wdcpmksy1PunarutthanPlanPhoto(int planPhotoId, Wdcpmksy1PunarutthanPlan wdcpmksy1PunarutthanPlan, String photoUrl, String longitute, String latitude, Timestamp photo_timestamp ) {
	   
	   this.planPhotoId=planPhotoId;
	   this.wdcpmksy1PunarutthanPlan=wdcpmksy1PunarutthanPlan;
	   this.photoUrl=photoUrl;
	   this.longitute=longitute;
	   this.latitude=latitude;
	   this.photo_timestamp=photo_timestamp;
   }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="plan_photo_id", unique=true, nullable=false)
	public int getPlanPhotoId() {
		return planPhotoId;
	}
	
	public void setPlanPhotoId(int planPhotoId) {
		this.planPhotoId = planPhotoId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "plan_id")
	public Wdcpmksy1PunarutthanPlan getWdcpmksy1PunarutthanPlan() {
		return wdcpmksy1PunarutthanPlan;
	}
	
	public void setWdcpmksy1PunarutthanPlan(Wdcpmksy1PunarutthanPlan wdcpmksy1PunarutthanPlan) {
		this.wdcpmksy1PunarutthanPlan = wdcpmksy1PunarutthanPlan;
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
