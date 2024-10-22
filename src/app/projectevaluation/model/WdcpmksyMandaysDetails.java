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
@Table(name="wdcpmksy_mandays_details" ,schema="public")
public class WdcpmksyMandaysDetails implements java.io.Serializable{
	
	private int mandaysDetailId;									//    mandays_detail_id;
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;   ///   project_profile_id
	//private Character projectControlled;               //   project_controlled
	private BigDecimal culturableWasteland;								//	culturable_wasteland 
	private Integer whsConstructedRejuvenated;						//	whs_constructed_rejuvenated 
	private BigDecimal soilMoisture;									//	soil_moisture
	private BigDecimal protectiveIrrigation;							//  protective_irrigation
	private BigDecimal degradedRainfed; 								//	degraded_rainfed 
	private BigDecimal farmerIncome; 									//	farmer_income
	private Integer farmerBenefited;									//	farmer_benefited
	private Integer mandaysGenerated;								//	mandays_generated
	private BigDecimal dugWell ;									//	dug_well
	private BigDecimal tubeWell ;										//	tube_well
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ; 
	private String requestIp; 
	
	private BigDecimal control_culturable_wasteland ;
	private Integer control_whs_constructed_rejuvenated ;
	private BigDecimal control_soil_moisture;
	private BigDecimal control_protective_irrigation; 
	private BigDecimal control_degraded_rainfed;
	private BigDecimal control_farmer_income ;
    private Integer control_farmer_benefited ;
    private Integer  control_mandays_generated ;
    private BigDecimal control_dug_well;
    private BigDecimal control_tube_well;
	
	public WdcpmksyMandaysDetails() {
    }

	
    public WdcpmksyMandaysDetails(int mandaysDetailId) {
        this.mandaysDetailId = mandaysDetailId;
    }
    
    public WdcpmksyMandaysDetails(int mandaysDetailId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, Character projectControlled, BigDecimal culturableWasteland, Integer whsConstructedRejuvenated, BigDecimal soilMoisture, BigDecimal protectiveIrrigation, BigDecimal degradedRainfed, BigDecimal farmerIncome, Integer farmerBenefited,
    		Integer mandaysGenerated, BigDecimal dugWell, BigDecimal tubeWell,  Date createdOn, String createdBy, Date updatedOn, String requestIp) {
        this.mandaysDetailId = mandaysDetailId;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
     //   this.projectControlled=projectControlled;
        this.culturableWasteland=culturableWasteland;
        this.whsConstructedRejuvenated=whsConstructedRejuvenated;
        this.soilMoisture=soilMoisture;
        this.protectiveIrrigation=protectiveIrrigation;
        this.degradedRainfed=degradedRainfed;
        this.farmerIncome=farmerIncome;
        this.farmerBenefited=farmerBenefited;
        this.mandaysGenerated=mandaysGenerated;
        this.dugWell=dugWell;
        this.tubeWell=tubeWell;
        this.createdOn=createdOn;
        this.createdBy=createdBy;
        this.updatedOn=updatedOn;
        this.requestIp=requestIp;
        
    }

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="mandays_detail_id", unique=true, nullable=false)
	public int getMandaysDetailId() {
		return mandaysDetailId;
	}


	public void setMandaysDetailId(int mandaysDetailId) {
		this.mandaysDetailId = mandaysDetailId;
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

	@Column(name="culturable_wasteland", precision=20)
	public BigDecimal getCulturableWasteland() {
		return culturableWasteland;
	}


	public void setCulturableWasteland(BigDecimal culturableWasteland) {
		this.culturableWasteland = culturableWasteland;
	}

	@Column(name="whs_constructed_rejuvenated")
	public Integer getWhsConstructedRejuvenated() {
		return whsConstructedRejuvenated;
	}


	public void setWhsConstructedRejuvenated(Integer whsConstructedRejuvenated) {
		this.whsConstructedRejuvenated = whsConstructedRejuvenated;
	}

	@Column(name="soil_moisture", precision=20)
	public BigDecimal getSoilMoisture() {
		return soilMoisture;
	}


	public void setSoilMoisture(BigDecimal soilMoisture) {
		this.soilMoisture = soilMoisture;
	}

	@Column(name="protective_irrigation", precision=20)
	public BigDecimal getProtectiveIrrigation() {
		return protectiveIrrigation;
	}


	public void setProtectiveIrrigation(BigDecimal protectiveIrrigation) {
		this.protectiveIrrigation = protectiveIrrigation;
	}

	@Column(name="degraded_rainfed", precision=20)
	public BigDecimal getDegradedRainfed() {
		return degradedRainfed;
	}


	public void setDegradedRainfed(BigDecimal degradedRainfed) {
		this.degradedRainfed = degradedRainfed;
	}

	@Column(name="farmer_income", precision=20)
	public BigDecimal getFarmerIncome() {
		return farmerIncome;
	}

	public void setFarmerIncome(BigDecimal farmerIncome) {
		this.farmerIncome = farmerIncome;
	}

	@Column(name="farmer_benefited")
	public Integer getFarmerBenefited() {
		return farmerBenefited;
	}


	public void setFarmerBenefited(Integer farmerBenefited) {
		this.farmerBenefited = farmerBenefited;
	}

	@Column(name="mandays_generated")
	public Integer getMandaysGenerated() {
		return mandaysGenerated;
	}


	public void setMandaysGenerated(Integer mandaysGenerated) {
		this.mandaysGenerated = mandaysGenerated;
	}

	@Column(name="dug_well", precision=20)
	public BigDecimal getDugWell() {
		return dugWell;
	}


	public void setDugWell(BigDecimal dugWell) {
		this.dugWell = dugWell;
	}

	@Column(name="tube_well", precision=20)
	public BigDecimal getTubeWell() {
		return tubeWell;
	}


	public void setTubeWell(BigDecimal tubeWell) {
		this.tubeWell = tubeWell;
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

	@Column(name="control_culturable_wasteland", precision=20)
	public BigDecimal getControl_culturable_wasteland() {
		return control_culturable_wasteland;
	}

	
	public void setControl_culturable_wasteland(BigDecimal control_culturable_wasteland) {
		this.control_culturable_wasteland = control_culturable_wasteland;
	}

	@Column(name="control_whs_constructed_rejuvenated")
	public Integer getControl_whs_constructed_rejuvenated() {
		return control_whs_constructed_rejuvenated;
	}

	
	public void setControl_whs_constructed_rejuvenated(Integer control_whs_constructed_rejuvenated) {
		this.control_whs_constructed_rejuvenated = control_whs_constructed_rejuvenated;
	}

	@Column(name="control_soil_moisture", precision=20)
	public BigDecimal getControl_soil_moisture() {
		return control_soil_moisture;
	}


	public void setControl_soil_moisture(BigDecimal control_soil_moisture) {
		this.control_soil_moisture = control_soil_moisture;
	}

	@Column(name="control_protective_irrigation", precision=20)
	public BigDecimal getControl_protective_irrigation() {
		return control_protective_irrigation;
	}


	public void setControl_protective_irrigation(BigDecimal control_protective_irrigation) {
		this.control_protective_irrigation = control_protective_irrigation;
	}

	@Column(name="control_degraded_rainfed", precision=20)
	public BigDecimal getControl_degraded_rainfed() {
		return control_degraded_rainfed;
	}


	public void setControl_degraded_rainfed(BigDecimal control_degraded_rainfed) {
		this.control_degraded_rainfed = control_degraded_rainfed;
	}

	@Column(name="control_farmer_income", precision=20)
	public BigDecimal getControl_farmer_income() {
		return control_farmer_income;
	}


	public void setControl_farmer_income(BigDecimal control_farmer_income) {
		this.control_farmer_income = control_farmer_income;
	}

	@Column(name="control_farmer_benefited")
	public Integer getControl_farmer_benefited() {
		return control_farmer_benefited;
	}


	public void setControl_farmer_benefited(Integer control_farmer_benefited) {
		this.control_farmer_benefited = control_farmer_benefited;
	}

	@Column(name="control_mandays_generated")
	public Integer getControl_mandays_generated() {
		return control_mandays_generated;
	}


	public void setControl_mandays_generated(Integer control_mandays_generated) {
		this.control_mandays_generated = control_mandays_generated;
	}

	@Column(name="control_dug_well", precision=20)
	public BigDecimal getControl_dug_well() {
		return control_dug_well;
	}


	public void setControl_dug_well(BigDecimal control_dug_well) {
		this.control_dug_well = control_dug_well;
	}

	@Column(name="control_tube_well", precision=20)
	public BigDecimal getControl_tube_well() {
		return control_tube_well;
	}


	public void setControl_tube_well(BigDecimal control_tube_well) {
		this.control_tube_well = control_tube_well;
	}
    
    
    
    
    
    
    

}
