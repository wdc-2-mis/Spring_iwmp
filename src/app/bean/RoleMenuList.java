package app.bean;

public class RoleMenuList {
	private String 	rolename;
	private Integer menuid;
	private String menuname;
	private Integer parentid;
	private String parentname;
	private String target;
	private Integer sequence;
	private Integer countsub;
	private Integer hseqno;
	private Boolean pactive;
	private Boolean cactive;
	
	
	public Boolean getPactive() {
		return pactive;
	}
	public void setPactive(Boolean pactive) {
		this.pactive = pactive;
	}
	public Boolean getCactive() {
		return cactive;
	}
	public void setCactive(Boolean cactive) {
		this.cactive = cactive;
	}
	public Integer getHseqno() {
		return hseqno;
	}
	public void setHseqno(Integer hseqNo) {
		this.hseqno = hseqNo;
	}
	public Integer getCountsub() {
		return countsub;
	}
	public void setCountsub(Integer countsub) {
		this.countsub = countsub;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Integer getMenuid() {
		return menuid;
	}
	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}
