package app.bean.reports;

import org.springframework.web.multipart.MultipartFile;

public class UserFileUploadBean {
	
	private String subject;
	private MultipartFile  theFile;
	private String Publish;
	private String category_type;
	private String New_File;
	private String upload_category;
	private String videopath;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public MultipartFile getTheFile() {
		return theFile;
	}
	public void setTheFile(MultipartFile theFile) {
		this.theFile = theFile;
	}
	public String getPublish() {
		return Publish;
	}
	public void setPublish(String publish) {
		Publish = publish;
	}
	public String getCategory_type() {
		return category_type;
	}
	public void setCategory_type(String category_type) {
		this.category_type = category_type;
	}
	public String getNew_File() {
		return New_File;
	}
	public void setNew_File(String new_File) {
		New_File = new_File;
	}
	public String getUpload_category() {
		return upload_category;
	}
	public void setUpload_category(String upload_category) {
		this.upload_category = upload_category;
	}
	public String getVideopath() {
		return videopath;
	}
	public void setVideopath(String videopath) {
		this.videopath = videopath;
	}
	
	
	

}
