package app.model.outcome;
// Generated 9 Dec, 2021 12:49:07 PM by Hibernate Tools 4.3.1


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

/**
 * GroundwaterDetail generated by hbm2java
 */
@Entity
@Table(name="groundwater_detail" ,schema="public")
public class GroundwaterDetail  implements java.io.Serializable {


     private int groundwaterDetailId;
     private GroundwaterMain groundwaterMain;
     private BigDecimal depthPremonsoon;
     private BigDecimal depthPostmonsoon;
     private Date updatedOn;
     private Date createdOn;
     private String requestIp;
     private String createdBy;
     private Character status;
     private BigDecimal ph;
     private Integer alkalinity;
     private Integer hardness;
     private Integer calcium;
     private Integer chloride;
     private BigDecimal nitrate;
     private Integer dissolvedSolid;
     private BigDecimal fluoride;

    public GroundwaterDetail() {
    }

	
    public GroundwaterDetail(int groundwaterDetailId) {
        this.groundwaterDetailId = groundwaterDetailId;
    }
    public GroundwaterDetail(int groundwaterDetailId, GroundwaterMain groundwaterMain, BigDecimal depthPremonsoon, BigDecimal depthPostmonsoon, Date updatedOn, Date createdOn, String requestIp, String createdBy, Character status, BigDecimal ph, Integer alkalinity, Integer hardness, Integer calcium, Integer chloride, BigDecimal nitrate, Integer dissolvedSolid, BigDecimal fluoride) {
       this.groundwaterDetailId = groundwaterDetailId;
       this.groundwaterMain = groundwaterMain;
       this.depthPremonsoon = depthPremonsoon;
       this.depthPostmonsoon = depthPostmonsoon;
       this.updatedOn = updatedOn;
       this.createdOn = createdOn;
       this.requestIp = requestIp;
       this.createdBy = createdBy;
       this.status=status;
       this.ph = ph;
       this.alkalinity = alkalinity;
       this.hardness = hardness;
       this.calcium = calcium;
       this.chloride = chloride;
       this.nitrate = nitrate;
       this.dissolvedSolid = dissolvedSolid;
       this.fluoride = fluoride;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="groundwater_detail_id", unique=true, nullable=false)
    public int getGroundwaterDetailId() {
        return this.groundwaterDetailId;
    }
    
    public void setGroundwaterDetailId(int groundwaterDetailId) {
        this.groundwaterDetailId = groundwaterDetailId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="groundwater_id")
    public GroundwaterMain getGroundwaterMain() {
        return this.groundwaterMain;
    }
    
    public void setGroundwaterMain(GroundwaterMain groundwaterMain) {
        this.groundwaterMain = groundwaterMain;
    }

    
    @Column(name="depth_premonsoon")
    public BigDecimal getDepthPremonsoon() {
        return this.depthPremonsoon;
    }
    
    public void setDepthPremonsoon(BigDecimal depthPremonsoon) {
        this.depthPremonsoon = depthPremonsoon;
    }

    
    @Column(name="depth_postmonsoon")
    public BigDecimal getDepthPostmonsoon() {
        return this.depthPostmonsoon;
    }
    
    public void setDepthPostmonsoon(BigDecimal depthPostmonsoon) {
        this.depthPostmonsoon = depthPostmonsoon;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="updated_on", length=13)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }
    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="created_on", length=13)
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
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

    
    @Column(name="ph", precision=10)
    public BigDecimal getPh() {
        return this.ph;
    }
    
    public void setPh(BigDecimal ph) {
        this.ph = ph;
    }

    
    @Column(name="alkalinity")
    public Integer getAlkalinity() {
        return this.alkalinity;
    }
    
    public void setAlkalinity(Integer alkalinity) {
        this.alkalinity = alkalinity;
    }

    
    @Column(name="hardness")
    public Integer getHardness() {
        return this.hardness;
    }
    
    public void setHardness(Integer hardness) {
        this.hardness = hardness;
    }

    
    @Column(name="calcium")
    public Integer getCalcium() {
        return this.calcium;
    }
    
    public void setCalcium(Integer calcium) {
        this.calcium = calcium;
    }

    
    @Column(name="chloride")
    public Integer getChloride() {
        return this.chloride;
    }
    
    public void setChloride(Integer chloride) {
        this.chloride = chloride;
    }

    
    

    
    public BigDecimal getNitrate() {
		return nitrate;
	}


	public void setNitrate(BigDecimal nitrate) {
		this.nitrate = nitrate;
	}


	@Column(name="dissolved_solid")
    public Integer getDissolvedSolid() {
        return this.dissolvedSolid;
    }
    
    public void setDissolvedSolid(Integer dissolvedSolid) {
        this.dissolvedSolid = dissolvedSolid;
    }

    
    @Column(name="fluoride", precision=10)
    public BigDecimal getFluoride() {
        return this.fluoride;
    }
    
    public void setFluoride(BigDecimal fluoride) {
        this.fluoride = fluoride;
    }




}


