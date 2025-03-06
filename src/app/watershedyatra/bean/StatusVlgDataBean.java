package app.watershedyatra.bean;

public class StatusVlgDataBean {
	
	private Integer st_code;
	private String st_name;
	private String inauguration_date;
    private String start_date;
    private String last_date;
    private Integer entered_days;
    private Integer missed_days;
    private String missed_dates;
    
	public Integer getSt_code() {
		return st_code;
	}
	public void setSt_code(Integer st_code) {
		this.st_code = st_code;
	}
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public String getInauguration_date() {
		return inauguration_date;
	}
	public void setInauguration_date(String inauguration_date) {
		this.inauguration_date = inauguration_date;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	public Integer getEntered_days() {
		return entered_days;
	}
	public void setEntered_days(Integer entered_days) {
		this.entered_days = entered_days;
	}
	public Integer getMissed_days() {
		return missed_days;
	}
	public void setMissed_days(Integer missed_days) {
		this.missed_days = missed_days;
	}
	public String getMissed_dates() {
		return missed_dates;
	}
	public void setMissed_dates(String missed_dates) {
		this.missed_dates = missed_dates;
	}
	
    
}
