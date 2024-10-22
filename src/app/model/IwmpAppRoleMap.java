package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the iwmp_app_role_map database table.
 * 
 */
@Entity
@Table(name="iwmp_app_role_map")
@NamedQuery(name="IwmpAppRoleMap.findAll", query="SELECT i FROM IwmpAppRoleMap i")
public class IwmpAppRoleMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id")
	private Integer roleId;

	@Column(name="home_page")
	private String homePage;

	@Column(name="last_updated_by")
	private String lastUpdatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	@Column(name="request_ip")
	private String requestIp;

	@Column(name="role_name")
	private String roleName;

	//bi-directional many-to-one association to IwmpUserAppRoleMap
	@OneToMany(mappedBy="iwmpAppRoleMap")
	private List<IwmpUserAppRoleMap> iwmpUserAppRoleMaps;

	public IwmpAppRoleMap() {
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getHomePage() {
		return this.homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
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

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<IwmpUserAppRoleMap> getIwmpUserAppRoleMaps() {
		return this.iwmpUserAppRoleMaps;
	}

	public void setIwmpUserAppRoleMaps(List<IwmpUserAppRoleMap> iwmpUserAppRoleMaps) {
		this.iwmpUserAppRoleMaps = iwmpUserAppRoleMaps;
	}

	public IwmpUserAppRoleMap addIwmpUserAppRoleMap(IwmpUserAppRoleMap iwmpUserAppRoleMap) {
		getIwmpUserAppRoleMaps().add(iwmpUserAppRoleMap);
		iwmpUserAppRoleMap.setIwmpAppRoleMap(this);

		return iwmpUserAppRoleMap;
	}

	public IwmpUserAppRoleMap removeIwmpUserAppRoleMap(IwmpUserAppRoleMap iwmpUserAppRoleMap) {
		getIwmpUserAppRoleMaps().remove(iwmpUserAppRoleMap);
		iwmpUserAppRoleMap.setIwmpAppRoleMap(null);

		return iwmpUserAppRoleMap;
	}

}