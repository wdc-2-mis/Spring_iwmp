package app.swachhata.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "watershed_swachhata_project_level_photo")
public class WatershedSwachhataProjectLevelPhoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "photo_id", unique = true, nullable = false)
	private Integer photoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "swachhata_id")
	private WatershedSwachhataProjectLevel swachhata;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "act_id")
	private WatershedSwachhataActivityMaster activity;

	@Column(name = "photo_url", length = 500)
	private String photoUrl;

	@Column(name = "longitude", length = 50)
	private String longitude;

	@Column(name = "latitude", length = 50)
	private String latitude;

	@Column(name = "requested_ip", length = 25)
	private String requestedIp;

	@Column(name = "updated_by", length = 25)
	private String updatedBy;

	@Column(name = "updated_date")
	private LocalDate updatedDate;

	@Column(name = "created_by", length = 25)
	private String createdBy;

	@Column(name = "created_date")
	private LocalDate createdDate;

	@Column(name = "photo_timestamp")
	private LocalDateTime photoTimestamp;

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public WatershedSwachhataProjectLevel getSwachhata() {
		return swachhata;
	}

	public void setSwachhata(WatershedSwachhataProjectLevel swachhata) {
		this.swachhata = swachhata;
	}

	public WatershedSwachhataActivityMaster getActivity() {
		return activity;
	}

	public void setActivity(WatershedSwachhataActivityMaster activity) {
		this.activity = activity;
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

	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getPhotoTimestamp() {
		return photoTimestamp;
	}

	public void setPhotoTimestamp(LocalDateTime photoTimestamp) {
		this.photoTimestamp = photoTimestamp;
	}
}
