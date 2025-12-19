package app.mahotsav.bean;

import org.springframework.web.multipart.MultipartFile;

public class WMMediaViewsDetailsBean {

	private String regno;
	
	private Integer videoid;

    private MultipartFile photos_screenshot;

    private Long no_of_views;

    private Long no_of_subscriber;

    private Long no_of_likes;
    
    private Character status;

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

	public Long getNo_of_subscriber() {
		return no_of_subscriber;
	}

	public void setNo_of_subscriber(Long no_of_subscriber) {
		this.no_of_subscriber = no_of_subscriber;
	}

	public Long getNo_of_likes() {
		return no_of_likes;
	}

	public void setNo_of_likes(Long no_of_likes) {
		this.no_of_likes = no_of_likes;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

    
}
