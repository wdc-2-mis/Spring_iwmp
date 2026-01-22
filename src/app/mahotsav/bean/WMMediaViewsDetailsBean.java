package app.mahotsav.bean;

import org.springframework.web.multipart.MultipartFile;

public class WMMediaViewsDetailsBean {

	private String regno;
	
	private Integer videoid;

    private MultipartFile photos_screenshot;

    private Long no_of_views;

    private Long no_of_comments;

    private Long no_of_likes;
    
    private Long no_of_shares;
    
    private Character status;
    
    private String mediatype;

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public Integer getVideoid() {
		return videoid;
	}

	public void setVideoid(Integer videoid) {
		this.videoid = videoid;
	}

	public MultipartFile getPhotos_screenshot() {
		return photos_screenshot;
	}

	public void setPhotos_screenshot(MultipartFile photos_screenshot) {
		this.photos_screenshot = photos_screenshot;
	}

	public Long getNo_of_views() {
		return no_of_views;
	}

	public void setNo_of_views(Long no_of_views) {
		this.no_of_views = no_of_views;
	}

	public Long getNo_of_comments() {
		return no_of_comments;
	}

	public void setNo_of_comments(Long no_of_comments) {
		this.no_of_comments = no_of_comments;
	}

	public Long getNo_of_likes() {
		return no_of_likes;
	}

	public void setNo_of_likes(Long no_of_likes) {
		this.no_of_likes = no_of_likes;
	}

	public Long getNo_of_shares() {
		return no_of_shares;
	}

	public void setNo_of_shares(Long no_of_shares) {
		this.no_of_shares = no_of_shares;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getMediatype() {
		return mediatype;
	}

	public void setMediatype(String mediatype) {
		this.mediatype = mediatype;
	}

    
}
