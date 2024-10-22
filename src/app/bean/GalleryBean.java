package app.bean;

import java.math.BigDecimal;

public class GalleryBean {
	private String stcode;
	private String stname;
	private String subject;
	private String path;
	private String filename;
	
	public String getStCode() {
		return this.stcode;
	}
	
	public void setStCode(String stCode) {
		this.stcode=stCode;
	}
	
	public String getStName() {
		return this.stname;
	}
	
	public void setStName(String stName) {
		this.stname=stName;
	}
	;
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject=subject;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path=path;
	}
	
	public String getFileName() {
		return this.filename;
	}
	
	public void setFileName(String fileName) {
		this.filename=fileName;
	}
	
}
