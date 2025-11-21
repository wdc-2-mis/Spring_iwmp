package app.mahotsav.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "watershed_mahotsav_prabhatpheri_act_photo", schema = "public")
public class MahotsavPrabhatPheriPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id", unique = true, nullable = false)
    private Integer photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prabhatpheri_id")
    private MahotsavPrabhatPheri wmPrabhatPheri;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "longitute")
    private String longitute;

    @Column(name = "latitude")
    private String latitude;
    
    @Column(name = "photo_timestamp")
    private Timestamp photo_timestamp;

    @Column(name = "requested_ip", length = 20)
    private String requestedIp;

    @Column(name = "updated_by", length = 25)
    private String updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_date", length = 13)
    private Date updated_date;

    @Column(name = "created_by", length = 25)
    private String createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date", length = 13)
    private Date created_date;


    // Getters & Setters

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public MahotsavPrabhatPheri getWmPrabhatPheri() {
        return wmPrabhatPheri;
    }

    public void setWmPrabhatPheri(MahotsavPrabhatPheri wmPrabhatPheri) {
        this.wmPrabhatPheri = wmPrabhatPheri;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLongitute() {
        return longitute;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public Timestamp getPhoto_timestamp() {
		return photo_timestamp;
	}

	public void setPhoto_timestamp(Timestamp photo_timestamp) {
		this.photo_timestamp = photo_timestamp;
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

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
