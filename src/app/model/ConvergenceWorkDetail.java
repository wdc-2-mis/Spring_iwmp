package app.model;

import java.math.BigDecimal;
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

import app.model.master.IwmpMConvergenceScheme;
import app.model.project.IwmpProjectPhysicalAsset;

@SuppressWarnings("serial")
@Entity
@Table(name="convergence_work_detail", schema="public")
public class ConvergenceWorkDetail implements java.io.Serializable {
	
	private Integer convergenceWorkId;
	private IwmpProjectPhysicalAsset iwmpProjectPhysicalAsset;
	private Character cnvrgnceStatus;
	private IwmpMConvergenceScheme iwmpmConvergenceScheme;
	private BigDecimal expndtrWdc2;
	private BigDecimal expndtrCnvrgdScheme;
	private Character status;
	 private Date createdOn;
     private Date updatedOn;
	
	
	
	
	
	public ConvergenceWorkDetail() {
		
	}
	public ConvergenceWorkDetail(int convergenceWorkId, IwmpProjectPhysicalAsset iwmpProjectPhysicalAsset,
			Character cnvrgnceStatus, IwmpMConvergenceScheme iwmpmConvergenceScheme, BigDecimal expndtrWdc2,
			BigDecimal expndtrCnvrgdScheme, Character status, Date createdOn, Date updatedOn) {
		super();
		this.convergenceWorkId = convergenceWorkId;
		this.iwmpProjectPhysicalAsset = iwmpProjectPhysicalAsset;
		this.cnvrgnceStatus = cnvrgnceStatus;
		this.iwmpmConvergenceScheme = iwmpmConvergenceScheme;
		this.expndtrWdc2 = expndtrWdc2;
		this.expndtrCnvrgdScheme = expndtrCnvrgdScheme;
		this.status = status;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="convergence_work_id", unique=true, nullable=false)
	public Integer getConvergenceWorkId() {
		return convergenceWorkId;
	}
	public void setConvergenceWorkId(Integer convergenceWorkId) {
		this.convergenceWorkId = convergenceWorkId;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="assetid")
	public IwmpProjectPhysicalAsset getIwmpProjectPhysicalAsset() {
		return iwmpProjectPhysicalAsset;
	}
	public void setIwmpProjectPhysicalAsset(IwmpProjectPhysicalAsset iwmpProjectPhysicalAsset) {
		this.iwmpProjectPhysicalAsset = iwmpProjectPhysicalAsset;
	}
	@Column(name="cnvrgnce_status")
	public Character getCnvrgnceStatus() {
		return cnvrgnceStatus;
	}
	public void setCnvrgnceStatus(Character cnvrgnceStatus) {
		this.cnvrgnceStatus = cnvrgnceStatus;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="scheme_id")
	public IwmpMConvergenceScheme getIwmpmConvergenceScheme() {
		return iwmpmConvergenceScheme;
	}
	public void setIwmpmConvergenceScheme(IwmpMConvergenceScheme iwmpmConvergenceScheme) {
		this.iwmpmConvergenceScheme = iwmpmConvergenceScheme;
	}
	@Column(name="expndtr_wdc2")
	public BigDecimal getExpndtrWdc2() {
		return expndtrWdc2;
	}
	public void setExpndtrWdc2(BigDecimal expndtrWdc2) {
		this.expndtrWdc2 = expndtrWdc2;
	}
	@Column(name="expndtr_cnvrgd_scheme")
	public BigDecimal getExpndtrCnvrgdScheme() {
		return expndtrCnvrgdScheme;
	}
	public void setExpndtrCnvrgdScheme(BigDecimal expndtrCnvrgdScheme) {
		this.expndtrCnvrgdScheme = expndtrCnvrgdScheme;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="created_on", length=13)
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="updated_on", length=13)
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
	
	
	

}
