package app.model;
// Generated 8 Jan, 2021 2:53:30 PM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * IwmpUserProjectMap generated by hbm2java
 */
@Entity
@Table(name = "iwmp_user_project_map", schema = "public")
@NamedQuery(name = "IwmpUserProjectMap.findAll", query = "SELECT i FROM IwmpUserProjectMap i")
public class IwmpUserProjectMap implements java.io.Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private int id;
	private IwmpMProject iwmpMProject;
	private UserReg userReg;
	private Date insertDate;
	private String updatedBy;
	private String ipAddress;

	public IwmpUserProjectMap() {
	}

	public IwmpUserProjectMap(int id, UserReg iwmpUserReg, Date insertDate, String updatedBy) {
		this.id = id;
		this.userReg = iwmpUserReg;
		this.insertDate = insertDate;
		this.updatedBy = updatedBy;
	}

	public IwmpUserProjectMap(int id, IwmpMProject iwmpMProject, UserReg iwmpUserReg, Date insertDate, String updatedBy,
			String ipAddress) {
		this.id = id;
		this.iwmpMProject = iwmpMProject;
		this.userReg = iwmpUserReg;
		this.insertDate = insertDate;
		this.updatedBy = updatedBy;
		this.ipAddress = ipAddress;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "proj_id")
	public IwmpMProject getIwmpMProject() {
		return this.iwmpMProject;
	}

	public void setIwmpMProject(IwmpMProject iwmpMProject) {
		this.iwmpMProject = iwmpMProject;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reg_id", nullable = false)
	public UserReg getUserReg() {
		return this.userReg;
	}

	public void setUserReg(UserReg iwmpUserReg) {
		this.userReg = iwmpUserReg;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "insert_date", nullable = false, length = 13)
	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	@Column(name = "updated_by", nullable = false, length = 50)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "ip_address", length = 20)
	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
