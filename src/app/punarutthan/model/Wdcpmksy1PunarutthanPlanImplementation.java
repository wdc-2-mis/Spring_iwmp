package app.punarutthan.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpVillage;

@Entity
@Table(name="wdcpmksy1_punarutthan_plan_implementation" ,schema="public")
public class Wdcpmksy1PunarutthanPlanImplementation {
	
	private int implementationPhotoId;
	private Wdcpmksy1PunarutthanPlan wdcpmksy1PunarutthanPlan;
	private IwmpState iwmpState;
    private IwmpDistrict iwmpDistrict;
    private String projectCd;
    private IwmpVillage iwmpVillage;
    private MStructure mStructure;
    private String noWork;
    private BigDecimal wdf;
    private BigDecimal mgnrega;
    private BigDecimal other;
    private Date createdOn;
    private Date updatedOn;
    private String requestIp;
    private String createdBy;
    private String updatedBy;
    private Character status;
    private Set<Wdcpmksy1PunarutthanPlanImplementationPhoto> wdcpmksy1PunarutthanPlanImplementationPhoto = new HashSet<Wdcpmksy1PunarutthanPlanImplementationPhoto>(0);
	
	public Wdcpmksy1PunarutthanPlanImplementation() {}
	
	public Wdcpmksy1PunarutthanPlanImplementation(int implementationPhotoId, Wdcpmksy1PunarutthanPlan wdcpmksy1PunarutthanPlan, IwmpState iwmpState, IwmpDistrict iwmpDistrict, String projectCd, IwmpVillage iwmpVillage, MStructure mStructure,
    		String noWork, BigDecimal wdf, BigDecimal mgnrega, BigDecimal other, Character status, Set<Wdcpmksy1PunarutthanPlanImplementationPhoto> wdcpmksy1PunarutthanPlanImplementationPhoto) {
		
		this.implementationPhotoId=implementationPhotoId;
		this.wdcpmksy1PunarutthanPlan=wdcpmksy1PunarutthanPlan;
		this.iwmpState=iwmpState;
    	this.iwmpDistrict=iwmpDistrict;
    	this.projectCd=projectCd;
    	this.iwmpVillage=iwmpVillage;
    	this.mStructure=mStructure;
    	this.noWork=noWork;
    	this.wdf=wdf;
    	this.mgnrega=mgnrega;
    	this.other=other;
    	this.status=status;
    	this.wdcpmksy1PunarutthanPlanImplementationPhoto=wdcpmksy1PunarutthanPlanImplementationPhoto;
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="implementation_id", unique=true, nullable=false)
	public int getImplementationPhotoId() {
		return implementationPhotoId;
	}

	public void setImplementationPhotoId(int implementationPhotoId) {
		this.implementationPhotoId = implementationPhotoId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "plan_id")
	public Wdcpmksy1PunarutthanPlan getWdcpmksy1PunarutthanPlan() {
		return wdcpmksy1PunarutthanPlan;
	}

	public void setWdcpmksy1PunarutthanPlan(Wdcpmksy1PunarutthanPlan wdcpmksy1PunarutthanPlan) {
		this.wdcpmksy1PunarutthanPlan = wdcpmksy1PunarutthanPlan;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "st_code")
	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dcode")
	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	@Column(name = "project_cd")
	public String getProjectCd() {
		return projectCd;
	}

	public void setProjectCd(String projectCd) {
		this.projectCd = projectCd;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "vcode")
	public IwmpVillage getIwmpVillage() {
		return iwmpVillage;
	}

	public void setIwmpVillage(IwmpVillage iwmpVillage) {
		this.iwmpVillage = iwmpVillage;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "structure_id")
	public MStructure getmStructure() {
		return mStructure;
	}

	public void setmStructure(MStructure mStructure) {
		this.mStructure = mStructure;
	}

	@Column(name = "no_work")
	public String getNoWork() {
		return noWork;
	}

	public void setNoWork(String noWork) {
		this.noWork = noWork;
	}

	@Column(name = "wdf")
	public BigDecimal getWdf() {
		return wdf;
	}

	public void setWdf(BigDecimal wdf) {
		this.wdf = wdf;
	}

	@Column(name = "mgnrega")
	public BigDecimal getMgnrega() {
		return mgnrega;
	}

	public void setMgnrega(BigDecimal mgnrega) {
		this.mgnrega = mgnrega;
	}

	@Column(name = "other")
	public BigDecimal getOther() {
		return other;
	}

	public void setOther(BigDecimal other) {
		this.other = other;
	}

	@Column(name = "updated_by")
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
    
	@Temporal(TemporalType.DATE)
    @Column(name="created_date", length=13)
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="updated_date", length=13)
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Column(name = "requested_ip", length=20)
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	@Column(name = "created_by", length=25)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "status")
	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksy1PunarutthanPlanImplementation")
	public Set<Wdcpmksy1PunarutthanPlanImplementationPhoto> getWdcpmksy1PunarutthanPlanImplementationPhoto() {
		return wdcpmksy1PunarutthanPlanImplementationPhoto;
	}

	public void setWdcpmksy1PunarutthanPlanImplementationPhoto(
			Set<Wdcpmksy1PunarutthanPlanImplementationPhoto> wdcpmksy1PunarutthanPlanImplementationPhoto) {
		this.wdcpmksy1PunarutthanPlanImplementationPhoto = wdcpmksy1PunarutthanPlanImplementationPhoto;
	}
	
	
	
	
	

}
