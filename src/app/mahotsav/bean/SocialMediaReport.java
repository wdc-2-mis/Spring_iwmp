package app.mahotsav.bean;

import java.math.BigInteger;

public class SocialMediaReport {
	private Integer stcode;
	private String stname;
	private BigInteger no_facebook;
	private BigInteger no_youtube;
	private BigInteger no_instagram;
	private BigInteger no_twitter;
	private BigInteger no_linkedin;
	private BigInteger total_registered_user;
	private BigInteger total_video_uploaded;
	private String dist_name;
	private Integer dcode;
	
	public Integer getDcode() {
		return dcode;
	}
	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}
	public String getDist_name() {
		return dist_name;
	}
	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}
	public Integer getStcode() {
		return stcode;
	}
	public void setStcode(Integer stcode) {
		this.stcode = stcode;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	
	public BigInteger getTotal_registered_user() {
		return total_registered_user;
	}
	public void setTotal_registered_user(BigInteger total_registered_user) {
		this.total_registered_user = total_registered_user;
	}
	
	public BigInteger getTotal_video_uploaded() {
		return total_video_uploaded;
	}
	public void setTotal_video_uploaded(BigInteger total_video_uploaded) {
		this.total_video_uploaded = total_video_uploaded;
	}
	public BigInteger getNo_facebook() {
		return no_facebook;
	}
	public void setNo_facebook(BigInteger no_facebook) {
		this.no_facebook = no_facebook;
	}
	public BigInteger getNo_youtube() {
		return no_youtube;
	}
	public void setNo_youtube(BigInteger no_youtube) {
		this.no_youtube = no_youtube;
	}
	public BigInteger getNo_instagram() {
		return no_instagram;
	}
	public void setNo_instagram(BigInteger no_instagram) {
		this.no_instagram = no_instagram;
	}
	public BigInteger getNo_twitter() {
		return no_twitter;
	}
	public void setNo_twitter(BigInteger no_twitter) {
		this.no_twitter = no_twitter;
	}
	public BigInteger getNo_linkedin() {
		return no_linkedin;
	}
	public void setNo_linkedin(BigInteger no_linkedin) {
		this.no_linkedin = no_linkedin;
	}
	
	

}

