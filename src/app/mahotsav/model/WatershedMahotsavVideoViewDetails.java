package app.mahotsav.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;

@Entity
@Table(name = "watershed_mahotsav_video_view_details", schema = "public")
public class WatershedMahotsavVideoViewDetails {



    @Id
    @Column(name = "video_detail_id", nullable = false)
    private Integer videoDetailId;

    @Column(name = "media_view_url", length = 500)
    private String mediaViewUrl;

    @Column(name = "status", length = 1)
    private Character status;

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

    @Column(name = "no_of_views")
    private Long noOfViews;

    @Column(name = "no_of_comments")
    private Long noOfComments;

    @Column(name = "no_of_likes")
    private Long noOfLikes;
    
    @Column(name = "no_of_shares")
    private Long noOfShares;

    // Relationship mapping to watershed_mahotsav_video_details
    @OneToOne
    @MapsId
    @JoinColumn(name = "video_detail_id", referencedColumnName = "video_detail_id",
                foreignKey = @ForeignKey(name = "fk_video_detail"))
    private WatershedMahotsavVideoDetails videoDetails;

    // --- Getters and Setters ---
    public Integer getVideoDetailId() {
        return videoDetailId;
    }

    public void setVideoDetailId(Integer videoDetailId) {
        this.videoDetailId = videoDetailId;
    }

    public String getMediaViewUrl() {
        return mediaViewUrl;
    }

    public void setMediaViewUrl(String mediaViewUrl) {
        this.mediaViewUrl = mediaViewUrl;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
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

    public Long getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(Long noOfViews) {
        this.noOfViews = noOfViews;
    }

    public Long getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(Long noOfComments) {
        this.noOfComments = noOfComments;
    }

    public Long getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(Long noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public Long getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(Long noOfShares) {
		this.noOfShares = noOfShares;
	}

	public WatershedMahotsavVideoDetails getVideoDetails() {
        return videoDetails;
    }

    public void setVideoDetails(WatershedMahotsavVideoDetails videoDetails) {
        this.videoDetails = videoDetails;
    }
}
