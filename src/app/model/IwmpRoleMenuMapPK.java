package app.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the iwmp_role_menu_map database table.
 * 
 */
@Embeddable
public class IwmpRoleMenuMapPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="app_id")
	private Integer appId;

	@Column(name="role_id")
	private long roleId;

	@Column(name="menu_id")
	private long menuId;

	public IwmpRoleMenuMapPK() {
	}
	public Integer getAppId() {
		return this.appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public long getRoleId() {
		return this.roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getMenuId() {
		return this.menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IwmpRoleMenuMapPK)) {
			return false;
		}
		IwmpRoleMenuMapPK castOther = (IwmpRoleMenuMapPK)other;
		return 
			this.appId.equals(castOther.appId)
			&& (this.roleId == castOther.roleId)
			&& (this.menuId == castOther.menuId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.appId.hashCode();
		hash = hash * prime + ((int) (this.roleId ^ (this.roleId >>> 32)));
		hash = hash * prime + ((int) (this.menuId ^ (this.menuId >>> 32)));
		
		return hash;
	}
}