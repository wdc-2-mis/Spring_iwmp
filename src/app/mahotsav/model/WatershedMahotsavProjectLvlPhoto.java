package app.mahotsav.model;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "watershed_mahotsav_project_lvl_photo", schema = "public")
public class WatershedMahotsavProjectLvlPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_seq")
    @SequenceGenerator(name = "photo_seq", sequenceName = "watershed_mahotsav_project_lvl_photo_photo_id_seq", allocationSize = 1)
    @Column(name = "photo_id")
    private Integer photoId;

    @ManyToOne
    @JoinColumn(name = "watershed_mahotsav_id", referencedColumnName = "watershed_mahotsav_id")
    private WatershedMahotsavProjectLevel watershedMahotsav;

    @ManyToOne
    @JoinColumn(name = "act_id", referencedColumnName = "act_id")
    private WatershedMahotsavInauguarationActivityMaster activity;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(name = "longitute", length = 50)
    private String longitude;

    @Column(name = "latitude", length = 50)
    private String latitude;

    @Column(name = "requested_ip", length = 25)
    private String requestedIp;

    @Column(name = "updated_by", length = 25)
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "created_by", length = 25)
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;
    
    @Column(name = "photo_timestamp")
    private Timestamp phototimestamp;
    
    
    
	public WatershedMahotsavProjectLvlPhoto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WatershedMahotsavProjectLvlPhoto(Integer photoId, WatershedMahotsavProjectLevel watershedMahotsav,
			WatershedMahotsavInauguarationActivityMaster activity, String photoUrl, String longitude, String latitude,
			String requestedIp, String updatedBy, Date updatedDate, String createdBy, Date createdDate, Timestamp phototimestamp ) {
		super();
		this.photoId = photoId;
		this.watershedMahotsav = watershedMahotsav;
		this.activity = activity;
		this.photoUrl = photoUrl;
		this.longitude = longitude;
		this.latitude = latitude;
		this.requestedIp = requestedIp;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.phototimestamp = phototimestamp;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public WatershedMahotsavProjectLevel getWatershedMahotsav() {
		return watershedMahotsav;
	}

	public void setWatershedMahotsav(WatershedMahotsavProjectLevel watershedMahotsav) {
		this.watershedMahotsav = watershedMahotsav;
	}

	public WatershedMahotsavInauguarationActivityMaster getActivity() {
		return activity;
	}

	public void setActivity(WatershedMahotsavInauguarationActivityMaster activity) {
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getPhototimestamp() {
		return phototimestamp;
	}

	public void setPhototimestamp(Timestamp phototimestamp) {
		this.phototimestamp = phototimestamp;
	}

    
}
