package app.bean;

public class RevisedActPlanBean {
	
	private String projectname;
	private String finyeardesc;
	private Character dprstatus;
	private Character status;
	private String authname;
	private Integer actplanid;
	
	public RevisedActPlanBean() {
	}
	public RevisedActPlanBean(String projectname, String finyeardesc, Character dprstatus,Character status, String authname, Integer actplanid) {
		super();
		this.projectname = projectname;
		this.finyeardesc = finyeardesc;
		this.dprstatus = dprstatus;
		this.status = status;
		this.authname = authname;
		this.actplanid = actplanid;
	}
	public String getprojectname() {
		return projectname;
	}
	public void setprojectname(String projectname) {
		this.projectname = projectname;
	}
	public String getfinyeardesc() {
		return finyeardesc;
	}
	public void setfinyeardesc(String finyeardesc) {
		this.finyeardesc = finyeardesc;
	}
	public Character getdprstatus() {
		return dprstatus;
	}
	public void setdprstatus(Character dprstatus) {
		this.dprstatus = dprstatus;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public String getauthname() {
		return authname;
	}
	public void setauthname(String authname) {
		this.authname = authname;
	}
	public Integer getActplanid() {
		return actplanid;
	}
	public void setActplanid(Integer actplanid) {
		this.actplanid = actplanid;
	}

}
