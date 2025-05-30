package app.model;
// Generated 05-Apr-2022, 3:02:01 PM by Hibernate Tools 4.3.1


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

/**
 * BlsOutDetail generated by hbm2java
 */
@Entity
@Table(name="bls_out_detail"
    ,schema="public"
)
public class BlsOutDetail  implements java.io.Serializable {


     private int blsOutDetailIdPk;
     private BlsOutMain blsOutMain;
     private MBlsOutcome MBlsOutcomeByNoOfCropId;
     private MBlsOutcome MBlsOutcomeByClassificationLandId;
     private MBlsOutcome MBlsOutcomeByForestLandTypeId;
     private MBlsOutcome MBlsOutcomeByOwnershipId;
     private String landSubClassification;
     private String ownerName;
     private Date createdOn;
     private Date updatedOn;
     private String requestIp;
     private String createdBy;
     private Set<BlsOutDetailCrop> blsOutDetailTranxes = new HashSet<BlsOutDetailCrop>(0);

    public BlsOutDetail() {
    }

	
    public BlsOutDetail(int blsOutDetailIdPk) {
        this.blsOutDetailIdPk = blsOutDetailIdPk;
    }
    public BlsOutDetail(int blsOutDetailIdPk, BlsOutMain blsOutMain, MBlsOutcome MBlsOutcomeByNoOfCropId, MBlsOutcome MBlsOutcomeByClassificationLandId, MBlsOutcome MBlsOutcomeByForestLandTypeId,String landSubClassification, Date createdOn, Date updatedOn, String requestIp, String createdBy, Set<BlsOutDetailCrop> blsOutDetailTranxes) {
       this.blsOutDetailIdPk = blsOutDetailIdPk;
       this.blsOutMain = blsOutMain;
       this.MBlsOutcomeByNoOfCropId = MBlsOutcomeByNoOfCropId;
       this.MBlsOutcomeByClassificationLandId = MBlsOutcomeByClassificationLandId;
       this.MBlsOutcomeByForestLandTypeId = MBlsOutcomeByForestLandTypeId;
       this.landSubClassification = landSubClassification;
       this.createdOn = createdOn;
       this.updatedOn = updatedOn;
       this.requestIp = requestIp;
       this.createdBy = createdBy;
       this.blsOutDetailTranxes = blsOutDetailTranxes;
    }
   

	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bls_out_detail_id_pk", unique=true, nullable=false)
    public int getBlsOutDetailIdPk() {
        return this.blsOutDetailIdPk;
    }
    
    public void setBlsOutDetailIdPk(int blsOutDetailIdPk) {
        this.blsOutDetailIdPk = blsOutDetailIdPk;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="bls_out_main_id")
    public BlsOutMain getBlsOutMain() {
        return this.blsOutMain;
    }
    
    public void setBlsOutMain(BlsOutMain blsOutMain) {
        this.blsOutMain = blsOutMain;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="no_of_crop_id")
    public MBlsOutcome getMBlsOutcomeByNoOfCropId() {
        return this.MBlsOutcomeByNoOfCropId;
    }
    
    public void setMBlsOutcomeByNoOfCropId(MBlsOutcome MBlsOutcomeByNoOfCropId) {
        this.MBlsOutcomeByNoOfCropId = MBlsOutcomeByNoOfCropId;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="classification_land_id")
    public MBlsOutcome getMBlsOutcomeByClassificationLandId() {
        return this.MBlsOutcomeByClassificationLandId;
    }
    
    public void setMBlsOutcomeByClassificationLandId(MBlsOutcome MBlsOutcomeByClassificationLandId) {
        this.MBlsOutcomeByClassificationLandId = MBlsOutcomeByClassificationLandId;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="forest_land_type_id")
    public MBlsOutcome getMBlsOutcomeByForestLandTypeId() {
        return this.MBlsOutcomeByForestLandTypeId;
    }
    
    public void setMBlsOutcomeByForestLandTypeId(MBlsOutcome MBlsOutcomeByForestLandTypeId) {
        this.MBlsOutcomeByForestLandTypeId = MBlsOutcomeByForestLandTypeId;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ownership_id")
    public MBlsOutcome getMBlsOutcomeByOwnershipId() {
        return this.MBlsOutcomeByOwnershipId;
    }
    
    public void setMBlsOutcomeByOwnershipId(MBlsOutcome MBlsOutcomeByOwnershipId) {
        this.MBlsOutcomeByOwnershipId = MBlsOutcomeByOwnershipId;
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

    @OneToMany(fetch=FetchType.LAZY, mappedBy="blsOutDetail")
    public Set<BlsOutDetailCrop> getBlsOutDetailTranxes() {
        return this.blsOutDetailTranxes;
    }
    
    public void setBlsOutDetailTranxes(Set<BlsOutDetailCrop> blsOutDetailTranxes) {
        this.blsOutDetailTranxes = blsOutDetailTranxes;
    }

    @Column(name="owner_name", length=50)
    public String getOwnerName() {
        return this.ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    @Column(name="land_sub_classification", length=100)
    public String getLandSubClassification() {
		return landSubClassification;
	}

	public void setLandSubClassification(String landSubClassification) {
		this.landSubClassification = landSubClassification;
	}



}


