package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the iwmp_user_app_role_map database table.
 * 
 */
@Entity
@Table(name="iwmp_user_app_role_map")
@NamedQuery(name="IwmpUserAppRoleMap.findAll", query="SELECT i FROM IwmpUserAppRoleMap i")
public class IwmpUserAppRoleMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_app_id")
	private Integer userAppId;

	@Column(name="app_id")
	private Integer appId;

	@Column(name="last_updated_by")
	private String lastUpdatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	@Column(name="request_ip")
	private String requestIp;

	@Column(name="user_id")
	private String userId;

	//bi-directional many-to-one association to IwmpAppRoleMap
	@ManyToOne
	@JoinColumn(name="role_id")
	private IwmpAppRoleMap iwmpAppRoleMap;

	public IwmpUserAppRoleMap() {
	}

	public Integer getUserAppId() {
		return this.userAppId;
	}

	public void setUserAppId(Integer userAppId) {
		this.userAppId = userAppId;
	}

	public Integer getAppId() {
		return this.appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
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

	public String getRequestIp() {
		return this.requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public IwmpAppRoleMap getIwmpAppRoleMap() {
		return this.iwmpAppRoleMap;
	}

	public void setIwmpAppRoleMap(IwmpAppRoleMap iwmpAppRoleMap) {
		this.iwmpAppRoleMap = iwmpAppRoleMap;
	}

}