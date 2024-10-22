package app.model;

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

import app.model.master.IwmpVillage;

@Entity
@Table(name="bls_out_main_his" ,schema="public")

public class BlsOutMainHis  implements java.io.Serializable {


     private int blsOutMainIdPk;
     private IwmpMProject iwmpMProject;
     private IwmpVillage iwmpVillage;
     private MBlsOutcome MBlsOutcome;
     private String plotNo;
     private BigDecimal area;
     private Date createdOn;
     private Date updatedOn;
     private String requestIp;
     private String createdBy;
     private Character status;
     private Character micro_status;
     private BigDecimal micro_irrigation;
     private Set<BlsOutDetailHis> blsOutDetailHises = new HashSet<BlsOutDetailHis>(0);

    public BlsOutMainHis() {
    }

	
    public BlsOutMainHis(int blsOutMainIdPk) {
        this.blsOutMainIdPk = blsOutMainIdPk;
    }
    public BlsOutMainHis(int blsOutMainIdPk, IwmpMProject iwmpMProject, IwmpVillage iwmpVillage, MBlsOutcome MBlsOutcome, String plotNo, BigDecimal area, Date createdOn, Date updatedOn, String requestIp, String createdBy, Character status, Set<BlsOutDetailHis> blsOutDetailHises, Character micro_status, BigDecimal micro_irrigation) {
       this.blsOutMainIdPk = blsOutMainIdPk;
       this.iwmpMProject = iwmpMProject;
       this.iwmpVillage = iwmpVillage;
       this.MBlsOutcome = MBlsOutcome;
       this.plotNo = plotNo;
       this.area = area;
       this.createdOn = createdOn;
       this.updatedOn = updatedOn;
       this.requestIp = requestIp;
       this.createdBy = createdBy;
       this.status = status;
       this.blsOutDetailHises = blsOutDetailHises;
       this.micro_status = micro_status;
       this.micro_irrigation = micro_irrigation;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="bls_out_main_id_pk", unique=true, nullable=false)
    public int getBlsOutMainIdPk() {
        return this.blsOutMainIdPk;
    }
    
    public void setBlsOutMainIdPk(int blsOutMainIdPk) {
        this.blsOutMainIdPk = blsOutMainIdPk;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="proj_id")
    public IwmpMProject getIwmpMProject() {
        return this.iwmpMProject;
    }
    
    public void setIwmpMProject(IwmpMProject iwmpMProject) {
        this.iwmpMProject = iwmpMProject;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="vcode")
    public IwmpVillage getIwmpVillage() {
        return this.iwmpVillage;
    }
    
    public void setIwmpVillage(IwmpVillage iwmpVillage) {
        this.iwmpVillage = iwmpVillage;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="irrigation_status_id")
    public MBlsOutcome getMBlsOutcome() {
        return this.MBlsOutcome;
    }
    
    public void setMBlsOutcome(MBlsOutcome MBlsOutcome) {
        this.MBlsOutcome = MBlsOutcome;
    }

    
    @Column(name="plot_no", length=20)
    public String getPlotNo() {
        return this.plotNo;
    }
    
    public void setPlotNo(String plotNo) {
        this.plotNo = plotNo;
    }

    
    @Column(name="area", precision=10)
    public BigDecimal getArea() {
        return this.area;
    }
    
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="created_on", length=13)
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="updated_on", length=13)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }
    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    
    @Column(name="request_ip", length=20)
    public String getRequestIp() {
        return this.requestIp;
    }
    
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    
    @Column(name="created_by", length=20)
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    
    @Column(name="status", length=1)
    public Character getStatus() {
        return this.status;
    }
    
    public void setStatus(Character status) {
        this.status = status;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="blsOutMainHis")
    public Set<BlsOutDetailHis> getBlsOutDetailHises() {
        return this.blsOutDetailHises;
    }
    
    public void setBlsOutDetailHises(Set<BlsOutDetailHis> blsOutDetailHises) {
        this.blsOutDetailHises = blsOutDetailHises;
    }


	public Character getMicro_status() {
		return micro_status;
	}


	public void setMicro_status(Character micro_status) {
		this.micro_status = micro_status;
	}


	public BigDecimal getMicro_irrigation() {
		return micro_irrigation;
	}


	public void setMicro_irrigation(BigDecimal micro_irrigation) {
		this.micro_irrigation = micro_irrigation;
	}




}


