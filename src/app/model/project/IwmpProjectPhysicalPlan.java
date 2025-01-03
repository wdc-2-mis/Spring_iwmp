package app.model.project;
// Generated 22 Mar, 2021 5:07:08 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.IwmpMFinYear;
import app.model.IwmpMProject;

/**
 * IwmpProjectPhysicalPlan generated by hbm2java
 */
@Entity
@Table(name="iwmp_project_physical_plan"
    ,schema="public"
)
public class IwmpProjectPhysicalPlan  implements java.io.Serializable {


     private int planid;
     private IwmpMFinYear iwmpMFinYear;
     private IwmpMProject iwmpMProject;
     private Character status;
     private String createdby;
     private Date createdon;
     private String updatedby;
     private Date updatedon;
     private String requestIp;
     private Set<IwmpProjectPhysicalTranx> iwmpProjectPhysicalTranxes = new HashSet<IwmpProjectPhysicalTranx>(0);
     private Set<IwmpProjectPhysicalAap> iwmpProjectPhysicalAaps = new HashSet<IwmpProjectPhysicalAap>(0);

    public IwmpProjectPhysicalPlan() {
    }

	
    public IwmpProjectPhysicalPlan(int planid) {
        this.planid = planid;
    }
    public IwmpProjectPhysicalPlan(int planid, IwmpMFinYear iwmpMFinYear, IwmpMProject iwmpMProject, Character status, String createdby, Date createdon, String updatedby, Date updatedon, String requestIp, Set<IwmpProjectPhysicalTranx> iwmpProjectPhysicalTranxes, Set<IwmpProjectPhysicalAap> iwmpProjectPhysicalAaps) {
       this.planid = planid;
       this.iwmpMFinYear = iwmpMFinYear;
       this.iwmpMProject = iwmpMProject;
       this.status = status;
       this.createdby = createdby;
       this.createdon = createdon;
       this.updatedby = updatedby;
       this.updatedon = updatedon;
       this.requestIp = requestIp;
       this.iwmpProjectPhysicalTranxes = iwmpProjectPhysicalTranxes;
       this.iwmpProjectPhysicalAaps = iwmpProjectPhysicalAaps;
    }
   
    @Id 
    @Column(name="planid", unique=true, nullable=false)
    public int getPlanid() {
        return this.planid;
    }
    
    public void setPlanid(int planid) {
        this.planid = planid;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="fin_yr_cd")
    public IwmpMFinYear getIwmpMFinYear() {
        return this.iwmpMFinYear;
    }
    
    public void setIwmpMFinYear(IwmpMFinYear iwmpMFinYear) {
        this.iwmpMFinYear = iwmpMFinYear;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="proj_id")
    public IwmpMProject getIwmpMProject() {
        return this.iwmpMProject;
    }
    
    public void setIwmpMProject(IwmpMProject iwmpMProject) {
        this.iwmpMProject = iwmpMProject;
    }

    
    @Column(name="status", length=1)
    public Character getStatus() {
        return this.status;
    }
    
    public void setStatus(Character status) {
        this.status = status;
    }

    
    @Column(name="createdby", length=20)
    public String getCreatedby() {
        return this.createdby;
    }
    
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdon", length=29)
    public Date getCreatedon() {
        return this.createdon;
    }
    
    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    
    @Column(name="updatedby", length=20)
    public String getUpdatedby() {
        return this.updatedby;
    }
    
    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updatedon", length=29)
    public Date getUpdatedon() {
        return this.updatedon;
    }
    
    public void setUpdatedon(Date updatedon) {
        this.updatedon = updatedon;
    }

    
    @Column(name="request_ip", length=20)
    public String getRequestIp() {
        return this.requestIp;
    }
    
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    @OneToMany(fetch=FetchType.LAZY,mappedBy="iwmpProjectPhysicalPlan")
    public Set<IwmpProjectPhysicalTranx> getIwmpProjectPhysicalTranxes() {
        return this.iwmpProjectPhysicalTranxes;
    }
    
    public void setIwmpProjectPhysicalTranxes(Set<IwmpProjectPhysicalTranx> iwmpProjectPhysicalTranxes) {
        this.iwmpProjectPhysicalTranxes = iwmpProjectPhysicalTranxes;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpProjectPhysicalPlan")
    public Set<IwmpProjectPhysicalAap> getIwmpProjectPhysicalAaps() {
        return this.iwmpProjectPhysicalAaps;
    }
    
    public void setIwmpProjectPhysicalAaps(Set<IwmpProjectPhysicalAap> iwmpProjectPhysicalAaps) {
        this.iwmpProjectPhysicalAaps = iwmpProjectPhysicalAaps;
    }




}


