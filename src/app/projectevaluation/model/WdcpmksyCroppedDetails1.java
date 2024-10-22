package app.projectevaluation.model;

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

@Entity
@Table(name="wdcpmksy_cropped_details_1" ,schema="public")
public class WdcpmksyCroppedDetails1 implements java.io.Serializable{
	
	private int croppedDetails1Id;						//  cropped_details_1_id
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;					// project_profile_id integer,
	//private Character projectControlled;									//   project_controlled
	private BigDecimal grossKharifCropArea;								//  gross_kharif_crop_area
	private BigDecimal grossRabiCropArea;							//   gross_rabi_crop_area
	private BigDecimal grossThirdCropArea; 							//   gross_third_crop_area
	private BigDecimal differentCropCereals;    					//   different_crop_cereals
	private BigDecimal differentCropPulses ;						//   different_crop_pulses
	private BigDecimal differentCropOilSeed ;						//   different_crop_oil_seed
	private BigDecimal differentCropMillets; 						//   different_crop_millets
	private BigDecimal differentCropOther ;						//   different_crop_other
	private BigDecimal horticultureArea ;							//	horticulture_area
	private BigDecimal netsownArea ;								//	netsown_area
	private BigDecimal croppingIntensity ;							//	cropping_intensity
	private BigDecimal diversifiedChange;							//	diversified_change
	
	
	private BigDecimal control_gross_kharif_crop_area ;
	private BigDecimal control_gross_rabi_crop_area;
	private BigDecimal control_gross_third_crop_area ;
	private BigDecimal control_different_crop_cereals ;
	private BigDecimal control_different_crop_pulses ;
	private BigDecimal control_different_crop_oil_seed ;
	private BigDecimal control_different_crop_millets ;
	private BigDecimal control_different_crop_other ;
	private BigDecimal control_horticulture_area ;
	private BigDecimal control_netsown_area ;
	private BigDecimal control_cropping_intensity ;
	private BigDecimal control_diversified_change ;
	
	
	
	
	
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ;
	private String requestIp; 
	
	public WdcpmksyCroppedDetails1() {
 	}

	
    public WdcpmksyCroppedDetails1(int croppedDetails1Id) {
        this.croppedDetails1Id = croppedDetails1Id;
    }
	
	
    public WdcpmksyCroppedDetails1(int croppedDetails1Id, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, Character projectControlled, BigDecimal grossKharifCropArea, BigDecimal grossRabiCropArea, BigDecimal grossThirdCropArea, BigDecimal differentCropCereals, BigDecimal differentCropPulses, BigDecimal differentCropOilSeed, BigDecimal differentCropMillets,
    		BigDecimal differentCropOther, BigDecimal horticultureArea, BigDecimal netsownArea, BigDecimal croppingIntensity, BigDecimal diversifiedChange, Date createdOn, String createdBy, Date updatedOn, String requestIp) {
    	
    	this.croppedDetails1Id=croppedDetails1Id;
    	this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
    	//this.projectControlled=projectControlled;
    	this.grossKharifCropArea=grossKharifCropArea;
    	this.grossRabiCropArea=grossRabiCropArea;
    	this.grossThirdCropArea=grossThirdCropArea;
    	this.differentCropCereals=differentCropCereals;
    	this.differentCropPulses=differentCropPulses;
    	this.differentCropOilSeed=differentCropOilSeed;
    	this.differentCropMillets=differentCropMillets;
    	this.differentCropOther=differentCropOther;
    	this.horticultureArea=horticultureArea;
    	this.netsownArea=netsownArea;
    	this.croppingIntensity=croppingIntensity;
    	this.diversifiedChange=diversifiedChange;
    	this.createdOn=createdOn;
    	this.createdBy=createdBy;
    	this.updatedOn=updatedOn;
    	this.requestIp=requestIp;
    	
 	}

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="cropped_details_1_id", unique=true, nullable=false)
	public int getCroppedDetails1Id() {
		return croppedDetails1Id;
	}


	public void setCroppedDetails1Id(int croppedDetails1Id) {
		this.croppedDetails1Id = croppedDetails1Id;
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

	@Column(name="gross_kharif_crop_area", precision=20)
	public BigDecimal getGrossKharifCropArea() {
		return grossKharifCropArea;
	}


	public void setGrossKharifCropArea(BigDecimal grossKharifCropArea) {
		this.grossKharifCropArea = grossKharifCropArea;
	}

	@Column(name="gross_rabi_crop_area", precision=20)
	public BigDecimal getGrossRabiCropArea() {
		return grossRabiCropArea;
	}


	public void setGrossRabiCropArea(BigDecimal grossRabiCropArea) {
		this.grossRabiCropArea = grossRabiCropArea;
	}

	@Column(name="gross_third_crop_area", precision=20)
	public BigDecimal getGrossThirdCropArea() {
		return grossThirdCropArea;
	}


	public void setGrossThirdCropArea(BigDecimal grossThirdCropArea) {
		this.grossThirdCropArea = grossThirdCropArea;
	}

	@Column(name="different_crop_cereals", precision=20)
	public BigDecimal getDifferentCropCereals() {
		return differentCropCereals;
	}


	public void setDifferentCropCereals(BigDecimal differentCropCereals) {
		this.differentCropCereals = differentCropCereals;
	}

	@Column(name="different_crop_pulses", precision=20)
	public BigDecimal getDifferentCropPulses() {
		return differentCropPulses;
	}


	public void setDifferentCropPulses(BigDecimal differentCropPulses) {
		this.differentCropPulses = differentCropPulses;
	}

	@Column(name="different_crop_oil_seed", precision=20)
	public BigDecimal getDifferentCropOilSeed() {
		return differentCropOilSeed;
	}


	public void setDifferentCropOilSeed(BigDecimal differentCropOilSeed) {
		this.differentCropOilSeed = differentCropOilSeed;
	}

	@Column(name="different_crop_millets", precision=20)
	public BigDecimal getDifferentCropMillets() {
		return differentCropMillets;
	}


	public void setDifferentCropMillets(BigDecimal differentCropMillets) {
		this.differentCropMillets = differentCropMillets;
	}

	@Column(name="different_crop_other", precision=20)
	public BigDecimal getDifferentCropOther() {
		return differentCropOther;
	}


	public void setDifferentCropOther(BigDecimal differentCropOther) {
		this.differentCropOther = differentCropOther;
	}

	@Column(name="horticulture_area", precision=20)
	public BigDecimal getHorticultureArea() {
		return horticultureArea;
	}


	public void setHorticultureArea(BigDecimal horticultureArea) {
		this.horticultureArea = horticultureArea;
	}

	@Column(name="netsown_area", precision=20)
	public BigDecimal getNetsownArea() {
		return netsownArea;
	}


	public void setNetsownArea(BigDecimal netsownArea) {
		this.netsownArea = netsownArea;
	}

	@Column(name="cropping_intensity", precision=20)
	public BigDecimal getCroppingIntensity() {
		return croppingIntensity;
	}


	public void setCroppingIntensity(BigDecimal croppingIntensity) {
		this.croppingIntensity = croppingIntensity;
	}

	@Column(name="diversified_change", precision=20)
	public BigDecimal getDiversifiedChange() {
		return diversifiedChange;
	}


	public void setDiversifiedChange(BigDecimal diversifiedChange) {
		this.diversifiedChange = diversifiedChange;
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

	@Column(name="control_gross_kharif_crop_area", precision=20)
	public BigDecimal getControl_gross_kharif_crop_area() {
		return control_gross_kharif_crop_area;
	}


	public void setControl_gross_kharif_crop_area(BigDecimal control_gross_kharif_crop_area) {
		this.control_gross_kharif_crop_area = control_gross_kharif_crop_area;
	}

	@Column(name="control_gross_rabi_crop_area", precision=20)
	public BigDecimal getControl_gross_rabi_crop_area() {
		return control_gross_rabi_crop_area;
	}


	public void setControl_gross_rabi_crop_area(BigDecimal control_gross_rabi_crop_area) {
		this.control_gross_rabi_crop_area = control_gross_rabi_crop_area;
	}

	@Column(name="control_gross_third_crop_area", precision=20)
	public BigDecimal getControl_gross_third_crop_area() {
		return control_gross_third_crop_area;
	}


	public void setControl_gross_third_crop_area(BigDecimal control_gross_third_crop_area) {
		this.control_gross_third_crop_area = control_gross_third_crop_area;
	}

	@Column(name="control_different_crop_cereals", precision=20)
	public BigDecimal getControl_different_crop_cereals() {
		return control_different_crop_cereals;
	}


	public void setControl_different_crop_cereals(BigDecimal control_different_crop_cereals) {
		this.control_different_crop_cereals = control_different_crop_cereals;
	}

	@Column(name="control_different_crop_pulses", precision=20)
	public BigDecimal getControl_different_crop_pulses() {
		return control_different_crop_pulses;
	}


	public void setControl_different_crop_pulses(BigDecimal control_different_crop_pulses) {
		this.control_different_crop_pulses = control_different_crop_pulses;
	}

	@Column(name="control_different_crop_oil_seed", precision=20)
	public BigDecimal getControl_different_crop_oil_seed() {
		return control_different_crop_oil_seed;
	}


	public void setControl_different_crop_oil_seed(BigDecimal control_different_crop_oil_seed) {
		this.control_different_crop_oil_seed = control_different_crop_oil_seed;
	}

	@Column(name="control_different_crop_millets", precision=20)
	public BigDecimal getControl_different_crop_millets() {
		return control_different_crop_millets;
	}


	public void setControl_different_crop_millets(BigDecimal control_different_crop_millets) {
		this.control_different_crop_millets = control_different_crop_millets;
	}

	@Column(name="control_different_crop_other", precision=20)
	public BigDecimal getControl_different_crop_other() {
		return control_different_crop_other;
	}


	public void setControl_different_crop_other(BigDecimal control_different_crop_other) {
		this.control_different_crop_other = control_different_crop_other;
	}

	@Column(name="control_horticulture_area", precision=20)
	public BigDecimal getControl_horticulture_area() {
		return control_horticulture_area;
	}


	public void setControl_horticulture_area(BigDecimal control_horticulture_area) {
		this.control_horticulture_area = control_horticulture_area;
	}

	@Column(name="control_netsown_area", precision=20)
	public BigDecimal getControl_netsown_area() {
		return control_netsown_area;
	}


	public void setControl_netsown_area(BigDecimal control_netsown_area) {
		this.control_netsown_area = control_netsown_area;
	}

	@Column(name="control_cropping_intensity", precision=20)
	public BigDecimal getControl_cropping_intensity() {
		return control_cropping_intensity;
	}


	public void setControl_cropping_intensity(BigDecimal control_cropping_intensity) {
		this.control_cropping_intensity = control_cropping_intensity;
	}

	@Column(name="control_diversified_change", precision=20)
	public BigDecimal getControl_diversified_change() {
		return control_diversified_change;
	}


	public void setControl_diversified_change(BigDecimal control_diversified_change) {
		this.control_diversified_change = control_diversified_change;
	}
    
    
    
    
    
    
    
	
	
	
	

}
