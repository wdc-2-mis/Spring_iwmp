package app.model.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.IwmpMProject;
import app.model.master.IwmpMWc;
import app.model.master.IwmpVillage;

@Entity
@Table(name="wdcpmksy_mapping_nrmwork_otherwork", schema="public")
public class WdcpmksyMappingNRMWorkOtherWork implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private int nrmworkOtherworkId;
	private IwmpMProject iwmpMProject;
    private IwmpProjectPhysicalAsset iwmpProjectPhysicalAsset;
    private Integer otherworkId;
    private String otherHead;
    private String createdBy;
    private Date createdDt;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private String requestIp;
    private Boolean status;
    

    public WdcpmksyMappingNRMWorkOtherWork() {
    }

	
    public WdcpmksyMappingNRMWorkOtherWork(int nrmworkOtherworkId) {
        this.nrmworkOtherworkId = nrmworkOtherworkId;
    }
    
    public WdcpmksyMappingNRMWorkOtherWork(int nrmworkOtherworkId, IwmpMProject iwmpMProject, IwmpProjectPhysicalAsset iwmpProjectPhysicalAsset, Integer otherworkId, String otherHead, String createdBy, Date createdDt, String lastUpdatedBy, Date lastUpdatedDate, String requestIp, Boolean status) {
        this.nrmworkOtherworkId = nrmworkOtherworkId;
        this.iwmpMProject=iwmpMProject;
        this.iwmpProjectPhysicalAsset=iwmpProjectPhysicalAsset;
        this.otherworkId=otherworkId;
        this.otherHead=otherHead;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
        this.requestIp = requestIp;
        this.status = status;
    }

    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="nrmwork_otherwork_id", unique=true, nullable=false)
	public int getNrmworkOtherworkId() {
		return nrmworkOtherworkId;
	}


	public void setNrmworkOtherworkId(int nrmworkOtherworkId) {
		this.nrmworkOtherworkId = nrmworkOtherworkId;
	}

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="proj_id")
	public IwmpMProject getIwmpMProject() {
		return iwmpMProject;
	}


	public void setIwmpMProject(IwmpMProject iwmpMProject) {
		this.iwmpMProject = iwmpMProject;
	}

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="nrmwork_id")
	public IwmpProjectPhysicalAsset getIwmpProjectPhysicalAsset() {
		return iwmpProjectPhysicalAsset;
	}


	public void setIwmpProjectPhysicalAsset(IwmpProjectPhysicalAsset iwmpProjectPhysicalAsset) {
		this.iwmpProjectPhysicalAsset = iwmpProjectPhysicalAsset;
	}

	@Column(name="otherwork_id")
	public Integer getOtherworkId() {
		return otherworkId;
	}


	public void setOtherworkId(Integer otherworkId) {
		this.otherworkId = otherworkId;
	}

	@Column(name="other_head", length=1)
	public String getOtherHead() {
		return otherHead;
	}


	public void setOtherHead(String otherHead) {
		this.otherHead = otherHead;
	}

	@Column(name="created_by", length=20)
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="created_dt", length=13)
	public Date getCreatedDt() {
		return createdDt;
	}


	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	@Column(name="last_updated_by", length=25)
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="last_updated_date", length=13)
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}


	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Column(name="request_ip", length=20)
	public String getRequestIp() {
		return requestIp;
	}


	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	@Column(name="status")
	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}
    
    
	

}
