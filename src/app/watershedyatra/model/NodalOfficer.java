package app.watershedyatra.model;

import java.util.Date;
import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;

@Entity
@Table(name = "nodal_officer",schema="public")
public class NodalOfficer {
	
	
	private Integer nodalId;
	private IwmpState iwmpState;
	private IwmpDistrict iwmpDistrict;
	private IwmpBlock iwmpBlock;
	private String nodalName;
	private String designation;
	private String mobile;
	private String email;
	private Date createdDate;
	private String createdBy;
	private String requestedIp;
	private String updatedBy;
	private Date updatedDate;
	private String level;
	private Character status;
	
	public NodalOfficer() {}
	
	public NodalOfficer(Integer nodalId) {
		this.nodalId=nodalId;
	}
	
	public NodalOfficer(Integer nodalId,IwmpState iwmpState,IwmpDistrict iwmpDistrict,IwmpBlock iwmpBlock,String nodalName, String designation,String mobile,
			String email,Date createdDate,String createdBy,String requestedIp,String updatedBy,Date updatedDate,String level, Character status ) {
		this.nodalId=nodalId;
		this.iwmpState=iwmpState;
		this.iwmpDistrict=iwmpDistrict;
		this.iwmpBlock=iwmpBlock;
		this.nodalName=nodalName;
		this.designation=designation;
		this.mobile=mobile;
		this.email=email;
		this.createdDate=createdDate;
		this.createdBy=createdBy;
		this.requestedIp=requestedIp;
		this.updatedBy=updatedBy;
		this.updatedDate=updatedDate;
		this.level=level;
		this.status=status;
	}

	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="nodal_id", unique=true, nullable=false)
	public Integer getNodalId() {
		return nodalId;
	}

	public void setNodalId(Integer nodalId) {
		this.nodalId = nodalId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="st_code")
	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dcode")
	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bcode")
	public IwmpBlock getIwmpBlock() {
		return iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}

	@Column(name="nodal_name", length=200)
	public String getNodalName() {
		return nodalName;
	}

	public void setNodalName(String nodalName) {
		this.nodalName = nodalName;
	}

	@Column(name="designation", length=100)
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Column(name="mobile", length=20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name="email", length=100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="created_date", length=13)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="created_by", length=25)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="requested_ip", length=20)
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	@Column(name="updated_by", length=25)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="updated_date", length=13)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name="level", length=20)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name="status", length=1)
	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}
	
	
	
	
	
    
}
