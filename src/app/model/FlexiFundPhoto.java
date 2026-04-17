package app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "flexi_fund_photo")
public class FlexiFundPhoto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ff_photo_id")
    private Integer ffPhotoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flexi_fund_id")
    private FlexiFundDetails flexiFundDetails;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "photo_timestamp")
    private Timestamp photoTimestamp;

    @Column(name = "requested_ip")
    private String requestedIp;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_date")
    private Date updatedDate;

	public Integer getFfPhotoId() {
		return ffPhotoId;
	}

	public void setFfPhotoId(Integer ffPhotoId) {
		this.ffPhotoId = ffPhotoId;
	}

	public FlexiFundDetails getFlexiFundDetails() {
		return flexiFundDetails;
	}

	public void setFlexiFundDetails(FlexiFundDetails flexiFundDetails) {
		this.flexiFundDetails = flexiFundDetails;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Timestamp getPhotoTimestamp() {
		return photoTimestamp;
	}

	public void setPhotoTimestamp(Timestamp photoTimestamp) {
		this.photoTimestamp = photoTimestamp;
	}

	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
    
    
    
}
