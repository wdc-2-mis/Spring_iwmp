package app.model;

import java.io.Serializable;
import javax.persistence.*;

import app.model.master.IwmpBlock;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the iwmp_user_map database table.
 * 
 */
@Entity
@Table(name="iwmp_user_map")
@NamedQuery(name="UserMap.findAll", query="SELECT i FROM UserMap i")
public class UserMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="map_id")
	private Integer mapId;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Temporal(TemporalType.DATE)
	@Column(name="creator_date")
	private Date creatorDate;

	@Column(name="creator_id")
	private String creatorId;

	@Column(name="dist_code")
	private Integer distCode;

	@Column(name="last_updated_by")
	private String lastUpdatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	@Column(name="proj_code")
	private String projCode;

	@Column(name="request_ip")
	private String requestIp;

	

	//bi-directional many-to-one association to IwmpBlock
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bcode")
	private IwmpBlock iwmpBlock;

	//bi-directional many-to-one association to IwmpDistrict
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="dcode")
	private IwmpDistrict iwmpDistrict;

	//bi-directional many-to-one association to IwmpState
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="st_code")
	private IwmpState iwmpState;

	//bi-directional many-to-one association to IwmpUserReg
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="reg_id")
	private UserReg userReg;

	public UserMap() {
	}

	public Integer getMapId() {
		return this.mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreatorDate() {
		return this.creatorDate;
	}

	public void setCreatorDate(Date creatorDate) {
		this.creatorDate = creatorDate;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getDcodeTemp() {
		return this.distCode;
	}

	public void setDcodeTemp(Integer distCode) {
		this.distCode = distCode;
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

	public String getProjCode() {
		return this.projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getRequestIp() {
		return this.requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	

	public IwmpBlock getIwmpBlock() {
		return this.iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}

	public IwmpDistrict getIwmpDistrict() {
		return this.iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	public IwmpState getIwmpState() {
		return this.iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	public UserReg getUserReg() {
		return this.userReg;
	}

	public void setUserReg(UserReg userReg) {
		this.userReg = userReg;
	}

}