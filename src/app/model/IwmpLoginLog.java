package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the iwmp_login_log database table.
 * 
 */
@Entity
@Table(name="iwmp_login_log")
@NamedQuery(name="IwmpLoginLog.findAll", query="SELECT i FROM IwmpLoginLog i")
public class IwmpLoginLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="failed_cause")
	private String failedCause;

	@Column(name="ip_address")
	private String ipAddress;

	@Column(name="last_updated_by")
	private String lastUpdatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	@Column(name="login_dt")
	private Timestamp loginDt;

	@Column(name="login_sts")
	private String loginSts;

	private String loginid;

	private String referrer;

	@Column(name="request_ip")
	private String requestIp;

	@Column(name="session_id")
	private String sessionId;

	@Column(name="user_agent")
	private String userAgent;

	@Column(name="user_sts")
	private String userSts;

	@Column(name="user_type")
	private String userType;

	//bi-directional many-to-one association to IwmpUserReg
	@ManyToOne
	@JoinColumn(name="reg_id")
	private UserReg userReg;

	public IwmpLoginLog() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFailedCause() {
		return this.failedCause;
	}

	public void setFailedCause(String failedCause) {
		this.failedCause = failedCause;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Timestamp getLoginDt() {
		return this.loginDt;
	}

	public void setLoginDt(Timestamp loginDt) {
		loginDt = new Timestamp(System.currentTimeMillis());
		this.loginDt = loginDt;
	}

	public String getLoginSts() {
		return this.loginSts;
	}

	public void setLoginSts(String loginSts) {
		this.loginSts = loginSts;
	}

	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getReferrer() {
		return this.referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getRequestIp() {
		return this.requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserSts() {
		return this.userSts;
	}

	public void setUserSts(String userSts) {
		this.userSts = userSts;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public UserReg getUserReg() {
		return this.userReg;
	}

	public void setUserReg(UserReg userReg) {
		this.userReg = userReg;
	}

}