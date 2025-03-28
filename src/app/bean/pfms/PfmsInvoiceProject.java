package app.bean.pfms;
// Generated 7 Apr, 2022 12:14:16 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
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

import app.model.IwmpMProject;

/**
 * PfmsInvoiceProject generated by hbm2java
 */
@Entity
@Table(name="pfms_invoice_project"
    ,schema="public"
)
public class PfmsInvoiceProject  implements java.io.Serializable {


     private int pfmsProjectId;
     private IwmpMProject iwmpMProject;
     private PfmsInvoiceData pfmsInvoiceData;
     private BigDecimal amount;
     private Date createdon;
     private String createdby;

    public PfmsInvoiceProject() {
    }

	
    public PfmsInvoiceProject(int pfmsProjectId) {
        this.pfmsProjectId = pfmsProjectId;
    }
    public PfmsInvoiceProject(int pfmsProjectId, IwmpMProject iwmpMProject, PfmsInvoiceData pfmsInvoiceData, BigDecimal amount, Date createdon, String createdby) {
       this.pfmsProjectId = pfmsProjectId;
       this.iwmpMProject = iwmpMProject;
       this.pfmsInvoiceData = pfmsInvoiceData;
       this.amount = amount;
       this.createdon = createdon;
       this.createdby = createdby;
    }
   
     @Id 

    
    @Column(name="pfms_project_id", unique=true, nullable=false)
    public int getPfmsProjectId() {
        return this.pfmsProjectId;
    }
    
    public void setPfmsProjectId(int pfmsProjectId) {
        this.pfmsProjectId = pfmsProjectId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_cd")
    public IwmpMProject getIwmpMProject() {
        return this.iwmpMProject;
    }
    
    public void setIwmpMProject(IwmpMProject iwmpMProject) {
        this.iwmpMProject = iwmpMProject;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pfmsid")
    public PfmsInvoiceData getPfmsInvoiceData() {
        return this.pfmsInvoiceData;
    }
    
    public void setPfmsInvoiceData(PfmsInvoiceData pfmsInvoiceData) {
        this.pfmsInvoiceData = pfmsInvoiceData;
    }

    
    @Column(name="amount", precision=10)
    public BigDecimal getAmount() {
        return this.amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdon", length=29)
    public Date getCreatedon() {
        return this.createdon;
    }
    
    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    
    @Column(name="createdby", length=50)
    public String getCreatedby() {
        return this.createdby;
    }
    
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }




}


