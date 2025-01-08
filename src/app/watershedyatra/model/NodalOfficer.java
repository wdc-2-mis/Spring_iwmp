package app.watershedyatra.model;

import java.util.Date;
import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;

@Entity
@Table(name = "nodal_officer")
public class NodalOfficer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nodalId;

    @Column(name = "st_code")
    private Integer stCode;

    @Column(name = "dcode")
    private Integer dCode;

    @Column(name = "bcode")
    private Integer bCode;

    @Column(name = "nodal_name")
    private String nodalName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "mobile")
    private Integer mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "requested_ip")
    private String requestedIp;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "st_code", referencedColumnName = "st_code", insertable = false, updatable = false)
    private IwmpState iwmpState;

    @ManyToOne
    @JoinColumn(name = "dcode", referencedColumnName = "dcode", insertable = false, updatable = false)
    private IwmpDistrict iwmpDistrict;
    
    @ManyToOne
    @JoinColumn(name = "bcode", referencedColumnName = "bcode", insertable = false, updatable = false)
    private IwmpBlock iwmpBlock;
    
    
	public Integer getNodalId() {
		return nodalId;
	}

	public void setNodalId(Integer nodalId) {
		this.nodalId = nodalId;
	}

	public Integer getStCode() {
		return stCode;
	}

	public void setStCode(Integer stCode) {
		this.stCode = stCode;
	}

	public Integer getdCode() {
		return dCode;
	}

	public void setdCode(Integer dCode) {
		this.dCode = dCode;
	}

	public Integer getbCode() {
		return bCode;
	}

	public void setbCode(Integer bCode) {
		this.bCode = bCode;
	}

	public String getNodalName() {
		return nodalName;
	}

	public void setNodalName(String nodalName) {
		this.nodalName = nodalName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	public IwmpBlock getIwmpBlock() {
		return iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}
    
    
}
