package app.model.project;
// Generated 24 Feb, 2021 11:47:03 AM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.master.IwmpMWc;

/**
 * IwmpProjectWc generated by hbm2java
 */
@Entity
@Table(name="iwmp_project_wc"
    ,schema="public"
)
public class IwmpProjectWc  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long projWcId;
     private IwmpMWc iwmpMWc;
     private IwmpProjectLocation iwmpProjectLocation;
     private String createdBy;
     private Date createdDt;
     private String lastUpdatedBy;
     private Date lastUpdatedDate;
     private String requestIp;
     private Boolean status;

    public IwmpProjectWc() {
    }

	
    public IwmpProjectWc(long projWcId) {
        this.projWcId = projWcId;
    }
    public IwmpProjectWc(long projWcId, IwmpMWc iwmpMWc, IwmpProjectLocation iwmpProjectLocation, String createdBy, Date createdDt, String lastUpdatedBy, Date lastUpdatedDate, String requestIp, Boolean status) {
       this.projWcId = projWcId;
       this.iwmpMWc = iwmpMWc;
       this.iwmpProjectLocation = iwmpProjectLocation;
       this.createdBy = createdBy;
       this.createdDt = createdDt;
       this.lastUpdatedBy = lastUpdatedBy;
       this.lastUpdatedDate = lastUpdatedDate;
       this.requestIp = requestIp;
       this.status = status;
    }
   
    @Id 
    @Column(name="proj_wc_id", unique=true, nullable=false)
    public long getProjWcId() {
        return this.projWcId;
    }
    
    public void setProjWcId(long projWcId) {
        this.projWcId = projWcId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="wc_id")
    public IwmpMWc getIwmpMWc() {
        return this.iwmpMWc;
    }
    
    public void setIwmpMWc(IwmpMWc iwmpMWc) {
        this.iwmpMWc = iwmpMWc;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="proj_location_id")
    public IwmpProjectLocation getIwmpProjectLocation() {
        return this.iwmpProjectLocation;
    }
    
    public void setIwmpProjectLocation(IwmpProjectLocation iwmpProjectLocation) {
        this.iwmpProjectLocation = iwmpProjectLocation;
    }

    
    @Column(name="created_by", length=20)
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_dt", length=29)
    public Date getCreatedDt() {
        return this.createdDt;
    }
    
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    
    @Column(name="last_updated_by", length=25)
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="last_updated_date", length=13)
    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }
    
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    
    @Column(name="request_ip", length=20)
    public String getRequestIp() {
        return this.requestIp;
    }
    
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    
    @Column(name="status")
    public Boolean getStatus() {
        return this.status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }




}


