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

import app.mahotsav.model.WatershedMahotsavInauguarationActPhoto;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpVillage;

@Entity
@Table(name="wdcpmksy1_punarutthan_plan" ,schema="public")
public class Wdcpmksy1PunarutthanPlan {
	
	private int planId;
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
    private Set<Wdcpmksy1PunarutthanPlanPhoto> wdcpmksy1PunarutthanPlanPhoto = new HashSet<Wdcpmksy1PunarutthanPlanPhoto>(0);
    private Set<Wdcpmksy1PunarutthanPlanImplementation> wdcpmksy1PunarutthanPlanImplementation = new HashSet<Wdcpmksy1PunarutthanPlanImplementation>(0);
    
    
    public Wdcpmksy1PunarutthanPlan() { 
    	
    }
    
    public Wdcpmksy1PunarutthanPlan(int planId) { 
    	this.planId=planId;
    }
    
    public Wdcpmksy1PunarutthanPlan(int planId, IwmpState iwmpState, IwmpDistrict iwmpDistrict, String projectCd, IwmpVillage iwmpVillage, MStructure mStructure,
    		String noWork, BigDecimal wdf, BigDecimal mgnrega, BigDecimal other, Character status, Set<Wdcpmksy1PunarutthanPlanPhoto> wdcpmksy1PunarutthanPlanPhoto,
    		Set<Wdcpmksy1PunarutthanPlanImplementation> wdcpmksy1PunarutthanPlanImplementation) { 
    	
    	this.planId=planId;
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
    	this.wdcpmksy1PunarutthanPlanPhoto=wdcpmksy1PunarutthanPlanPhoto;
    	this.wdcpmksy1PunarutthanPlanImplementation=wdcpmksy1PunarutthanPlanImplementation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="plan_id", unique=true, nullable=false)
	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksy1PunarutthanPlan")
	public Set<Wdcpmksy1PunarutthanPlanPhoto> getWdcpmksy1PunarutthanPlanPhoto() {
		return wdcpmksy1PunarutthanPlanPhoto;
	}

	public void setWdcpmksy1PunarutthanPlanPhoto(Set<Wdcpmksy1PunarutthanPlanPhoto> wdcpmksy1PunarutthanPlanPhoto) {
		this.wdcpmksy1PunarutthanPlanPhoto = wdcpmksy1PunarutthanPlanPhoto;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksy1PunarutthanPlan")
	public Set<Wdcpmksy1PunarutthanPlanImplementation> getWdcpmksy1PunarutthanPlanImplementation() {
		return wdcpmksy1PunarutthanPlanImplementation;
	}

	public void setWdcpmksy1PunarutthanPlanImplementation(
			Set<Wdcpmksy1PunarutthanPlanImplementation> wdcpmksy1PunarutthanPlanImplementation) {
		this.wdcpmksy1PunarutthanPlanImplementation = wdcpmksy1PunarutthanPlanImplementation;
	}
    
    
    
    
    
    
}
