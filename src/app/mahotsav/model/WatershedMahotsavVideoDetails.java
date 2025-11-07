package app.mahotsav.model;

import java.util.Date;
import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;

@Entity
@Table(name = "watershed_mahotsav_video_details")
public class WatershedMahotsavVideoDetails implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer videoDetailId;
    private String longitute;
    private String latitude;
    private String mediaUrl;
    private String status;
    private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    
    private IwmpState iwmpState;
    private IwmpDistrict iwmpDistrict;
    private IwmpBlock iwmpBlock;
    private IwmpVillage iwmpVillage;
    private WatershedMahotsavMediaMaster mediaMaster;
    
    
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    public WatershedMahotsavVideoDetails() {
    }

    public WatershedMahotsavVideoDetails(Integer videoDetailId, String longitute, String latitude, String mediaUrl, String status, String requestedIp, 
    		String updatedBy, Date updatedDate, String createdBy, Date createdDate, IwmpState iwmpState, IwmpDistrict iwmpDistrict, IwmpBlock iwmpBlock, 
    		IwmpVillage iwmpVillage, WatershedMahotsavMediaMaster mediaMaster) 
    {
    	this.videoDetailId = videoDetailId;
    	this.longitute = longitute;
    	this.latitude = latitude;
        this.mediaUrl = mediaUrl;
        this.status = status;
        this.requestedIp = requestedIp;
        this.updatedBy =updatedBy;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.iwmpState = iwmpState;
    	this.iwmpDistrict = iwmpDistrict;
    	this.iwmpBlock = iwmpBlock;
        this.iwmpVillage = iwmpVillage;
        this.mediaMaster = mediaMaster;
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_detail_id", unique = true, nullable = false)
    public Integer getVideoDetailId() {
        return this.videoDetailId;
    }

    public void setVideoDetailId(Integer videoDetailId) {
        this.videoDetailId = videoDetailId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_code")
    public IwmpState getIwmpState() {
        return this.iwmpState;
    }

    public void setIwmpState(IwmpState iwmpState) {
        this.iwmpState = iwmpState;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dcode")
    public IwmpDistrict getIwmpDistrict() {
        return this.iwmpDistrict;
    }

    public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
        this.iwmpDistrict = iwmpDistrict;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bcode")
    public IwmpBlock getIwmpBlock() {
        return this.iwmpBlock;
    }

    public void setIwmpBlock(IwmpBlock iwmpBlock) {
        this.iwmpBlock = iwmpBlock;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode")
    public IwmpVillage getIwmpVillage() {
        return this.iwmpVillage;
    }

    public void setIwmpVillage(IwmpVillage iwmpVillage) {
        this.iwmpVillage = iwmpVillage;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media")
    public WatershedMahotsavMediaMaster getMediaMaster() {
        return this.mediaMaster;
    }

    public void setMediaMaster(WatershedMahotsavMediaMaster mediaMaster) {
        this.mediaMaster = mediaMaster;
    }

    @Column(name = "longitute", length = 50)
    public String getLongitute() {
        return this.longitute;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }

    @Column(name = "latitude", length = 50)
    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(name = "media_url", length = 100)
    public String getMediaUrl() {
        return this.mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    @Column(name = "status", length = 50)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}