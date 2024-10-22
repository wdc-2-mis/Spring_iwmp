package app.projectevaluation.model;

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

@Entity
@Table(name="wdcpmksy_ecological_perspective" ,schema="public")
public class WdcpmksyEcologicalPerspective implements java.io.Serializable{
	
	
	private int ecologicalPerspectiveId;				//   ecological_perspective_id
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;				//project_profile_id
	//private Character projectControlled;               //   project_controlled
	private Boolean naturalResource; 					//    natural_resource
	private String naturalResourceRemark; 				//    natural_resource_remark
    private Boolean normsRelating; 						//    norms_relating
    private String normsRelatingRemark; 				//	norms_relating_remark	
    private Boolean anturalAsset; 						//    antural_asset
    private String anturalAssetRemark;					//	 antural_asset_remark	
    private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ;
	private String requestIp; 
	
	
	private Boolean control_natural_resource ;
	private Boolean control_norms_relating ;
	private Boolean control_antural_asset ;
	
	
	public WdcpmksyEcologicalPerspective() {
 	}

	public WdcpmksyEcologicalPerspective(int ecologicalPerspectiveId) {
		this.ecologicalPerspectiveId = ecologicalPerspectiveId;
 	}
	
    public WdcpmksyEcologicalPerspective(int ecologicalPerspectiveId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, Character projectControlled, Boolean naturalResource, String naturalResourceRemark, Boolean normsRelating, String normsRelatingRemark, Boolean anturalAsset,
    		String anturalAssetRemark, Date createdOn, String createdBy, Date updatedOn, String requestIp) {
        this.ecologicalPerspectiveId = ecologicalPerspectiveId;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
    //    this.projectControlled=projectControlled;
        this.naturalResource=naturalResource;
        this.naturalResourceRemark=naturalResourceRemark;
        this.normsRelating=normsRelating;
        this.normsRelatingRemark=normsRelatingRemark;
        this.anturalAsset=anturalAsset;
        this.anturalAssetRemark=anturalAssetRemark;
        this.createdOn=createdOn;
    	this.createdBy=createdBy;
    	this.updatedOn=updatedOn;
    	this.requestIp=requestIp;
        
    }

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ecological_perspective_id", unique=true, nullable=false)
	public int getEcologicalPerspectiveId() {
		return ecologicalPerspectiveId;
	}


	public void setEcologicalPerspectiveId(int ecologicalPerspectiveId) {
		this.ecologicalPerspectiveId = ecologicalPerspectiveId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_profile_id")
	public WdcpmksyProjectProfileEvaluation getWdcpmksyProjectProfileEvaluation() {
		return wdcpmksyProjectProfileEvaluation;
	}


	public void setWdcpmksyProjectProfileEvaluation(WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation) {
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
	}

	/*
	 * @Column(name="project_controlled", length=1) public Character
	 * getProjectControlled() { return projectControlled; }
	 * 
	 * 
	 * public void setProjectControlled(Character projectControlled) {
	 * this.projectControlled = projectControlled; }
	 */

	@Column(name="natural_resource")
	public Boolean getNaturalResource() {
		return naturalResource;
	}


	public void setNaturalResource(Boolean naturalResource) {
		this.naturalResource = naturalResource;
	}

	@Column(name="natural_resource_remark", length=200)
	public String getNaturalResourceRemark() {
		return naturalResourceRemark;
	}


	public void setNaturalResourceRemark(String naturalResourceRemark) {
		this.naturalResourceRemark = naturalResourceRemark;
	}

	@Column(name="norms_relating")
	public Boolean getNormsRelating() {
		return normsRelating;
	}


	public void setNormsRelating(Boolean normsRelating) {
		this.normsRelating = normsRelating;
	}

	@Column(name="norms_relating_remark", length=200)
	public String getNormsRelatingRemark() {
		return normsRelatingRemark;
	}


	public void setNormsRelatingRemark(String normsRelatingRemark) {
		this.normsRelatingRemark = normsRelatingRemark;
	}

	@Column(name="antural_asset")
	public Boolean getAnturalAsset() {
		return anturalAsset;
	}


	public void setAnturalAsset(Boolean anturalAsset) {
		this.anturalAsset = anturalAsset;
	}

	@Column(name="antural_asset_remark", length=200)
	public String getAnturalAssetRemark() {
		return anturalAssetRemark;
	}


	public void setAnturalAssetRemark(String anturalAssetRemark) {
		this.anturalAssetRemark = anturalAssetRemark;
	}


	@Temporal(TemporalType.DATE)
    @Column(name="created_on")
	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="created_by", length=20)
	public String getCreatedBy() {
		return createdBy;
	}

	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name="request_ip", length=20)
	public String getRequestIp() {
		return requestIp;
	}


	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	@Column(name="control_natural_resource")
	public Boolean getControl_natural_resource() {
		return control_natural_resource;
	}

	public void setControl_natural_resource(Boolean control_natural_resource) {
		this.control_natural_resource = control_natural_resource;
	}

	@Column(name="control_norms_relating")
	public Boolean getControl_norms_relating() {
		return control_norms_relating;
	}

	public void setControl_norms_relating(Boolean control_norms_relating) {
		this.control_norms_relating = control_norms_relating;
	}

	@Column(name="control_antural_asset")
	public Boolean getControl_antural_asset() {
		return control_antural_asset;
	}

	public void setControl_antural_asset(Boolean control_antural_asset) {
		this.control_antural_asset = control_antural_asset;
	}
    
    
    
    
    
	

}
