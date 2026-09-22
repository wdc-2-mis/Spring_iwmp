package app.swachhata.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;

@Entity
@Table(name = "watershed_swachhata_project_level")
public class WatershedSwachhataProjectLevel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "swachhata_id", unique = true, nullable = false)
	private Integer swachhataId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "st_code")
	private IwmpState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dcode")
	private IwmpDistrict district;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bcode")
	private IwmpBlock block;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vcode")
	private IwmpVillage village;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proj_id")
	private IwmpMProject project;

	@Column(name = "swachhata_date")
	private LocalDateTime swachhataDate;

	@Column(name = "swachhata_location", length = 200)
	private String swachhataLocation;

	@Column(name = "shg")
	private Integer shg;

	@Column(name = "usg")
	private Integer usg;

	@Column(name = "fpo")
	private Integer fpo;

	@Column(name = "student")
	private Integer student;

	@Column(name = "other")
	private Integer other;

	@Column(name = "sapling")
	private Integer sapling;

	@Column(name = "lokarpan")
	private Integer lokarpan;

	@Column(name = "shramdaan")
	private Integer shramdaan;

	@Column(name = "cleanliness")
	private Integer cleanliness;

	@Column(name = "awareness")
	private Integer awareness;

	@Column(name = "status", length = 1)
	private Character status;

	@Column(name = "requested_ip", length = 25)
	private String requestedIp;

	@Column(name = "updated_by", length = 25)
	private String updatedBy;

	@Column(name = "updated_date")
	private LocalDate updatedDate;

	@Column(name = "created_by", length = 25)
	private String createdBy;

	@Column(name = "created_date")
	private LocalDate createdDate;

	public Integer getSwachhataId() {
		return swachhataId;
	}

	public void setSwachhataId(Integer swachhataId) {
		this.swachhataId = swachhataId;
	}

	public IwmpState getState() {
		return state;
	}

	public void setState(IwmpState state) {
		this.state = state;
	}

	public IwmpDistrict getDistrict() {
		return district;
	}

	public void setDistrict(IwmpDistrict district) {
		this.district = district;
	}

	public IwmpBlock getBlock() {
		return block;
	}

	public void setBlock(IwmpBlock block) {
		this.block = block;
	}

	public IwmpVillage getVillage() {
		return village;
	}

	public void setVillage(IwmpVillage village) {
		this.village = village;
	}

	public IwmpMProject getProject() {
		return project;
	}

	public void setProject(IwmpMProject project) {
		this.project = project;
	}

	public LocalDateTime getSwachhataDate() {
		return swachhataDate;
	}

	public void setSwachhataDate(LocalDateTime swachhataDate) {
		this.swachhataDate = swachhataDate;
	}

	public String getSwachhataLocation() {
		return swachhataLocation;
	}

	public void setSwachhataLocation(String swachhataLocation) {
		this.swachhataLocation = swachhataLocation;
	}

	public Integer getShg() {
		return shg;
	}

	public void setShg(Integer shg) {
		this.shg = shg;
	}

	public Integer getUsg() {
		return usg;
	}

	public void setUsg(Integer usg) {
		this.usg = usg;
	}

	public Integer getFpo() {
		return fpo;
	}

	public void setFpo(Integer fpo) {
		this.fpo = fpo;
	}

	public Integer getStudent() {
		return student;
	}

	public void setStudent(Integer student) {
		this.student = student;
	}

	public Integer getOther() {
		return other;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	public Integer getSapling() {
		return sapling;
	}

	public void setSapling(Integer sapling) {
		this.sapling = sapling;
	}

	public Integer getLokarpan() {
		return lokarpan;
	}

	public void setLokarpan(Integer lokarpan) {
		this.lokarpan = lokarpan;
	}

	public Integer getShramdaan() {
		return shramdaan;
	}

	public void setShramdaan(Integer shramdaan) {
		this.shramdaan = shramdaan;
	}

	public Integer getCleanliness() {
		return cleanliness;
	}

	public void setCleanliness(Integer cleanliness) {
		this.cleanliness = cleanliness;
	}

	public Integer getAwareness() {
		return awareness;
	}

	public void setAwareness(Integer awareness) {
		this.awareness = awareness;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
}
