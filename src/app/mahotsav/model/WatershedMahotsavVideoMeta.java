package app.mahotsav.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "watershed_mahotsav_video_meta")
public class WatershedMahotsavVideoMeta {

    @Id
    @Column(name = "video_detail_id")
    private Integer videoDetailId;

    @Column(name = "reg_no")
    private String regNo;

    @Column(name = "reg_name")
    private String regName;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "platform")
    private String platform;

    @Column(name = "no_of_views")
    private Integer noOfViews;

    @Column(name = "no_of_likes")
    private Integer noOfLikes;

    @Column(name = "no_of_comments")
    private Integer noOfComments;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "status")
    private String status;

    @Column(name = "st_code")
    private Integer stCode;

    @Column(name = "dcode")
    private Integer dCode;
    
    

	public WatershedMahotsavVideoMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WatershedMahotsavVideoMeta(Integer videoDetailId, String regNo, String regName, String thumbnail,
			String platform, Integer noOfViews, Integer noOfLikes, Integer noOfComments, LocalDate createdDate,
			LocalDate updatedDate, String mediaUrl, String status, Integer stCode, Integer dCode) {
		super();
		this.videoDetailId = videoDetailId;
		this.regNo = regNo;
		this.regName = regName;
		this.thumbnail = thumbnail;
		this.platform = platform;
		this.noOfViews = noOfViews;
		this.noOfLikes = noOfLikes;
		this.noOfComments = noOfComments;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.mediaUrl = mediaUrl;
		this.status = status;
		this.stCode = stCode;
		this.dCode = dCode;
	}


	public Integer getVideoDetailId() {
		return videoDetailId;
	}

	public void setVideoDetailId(Integer videoDetailId) {
		this.videoDetailId = videoDetailId;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Integer getNoOfViews() {
		return noOfViews;
	}

	public void setNoOfViews(Integer noOfViews) {
		this.noOfViews = noOfViews;
	}

	public Integer getNoOfLikes() {
		return noOfLikes;
	}

	public void setNoOfLikes(Integer noOfLikes) {
		this.noOfLikes = noOfLikes;
	}

	public Integer getNoOfComments() {
		return noOfComments;
	}

	public void setNoOfComments(Integer noOfComments) {
		this.noOfComments = noOfComments;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStCode() {
		return stCode;
	}

	public void setStCode(Integer stCode) {
		this.stCode = stCode;
	}

	public Integer getdCode() {
		return dCode;
	}

	public void setdCode(Integer dCode) {
		this.dCode = dCode;
	}
    
    
}
