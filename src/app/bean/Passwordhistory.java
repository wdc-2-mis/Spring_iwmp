package app.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name ="iwmp_pwd_history",  uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID") })
public class Passwordhistory {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
	@Column(name = "loginid")
	private String loginid;
	@Column(name = "encrypted_pass")
	private String encrypted_pass;
	@Column(name = "ip_address")
	private String ip_address;
	@Column(name = "user_type")
	private String user_type;
	@Column(name = "last_updated_by")
	private String last_updated_by;
	@Column(name = "session_id")
	private String session_id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getEncrypted_pass() {
		return encrypted_pass;
	}
	public void setEncrypted_pass(String encrypted_pass) {
		this.encrypted_pass = encrypted_pass;
	}
	
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getLast_updated_by() {
		return last_updated_by;
	}
	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}
	
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	
	
}
