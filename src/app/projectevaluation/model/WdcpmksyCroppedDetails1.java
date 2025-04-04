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
	private BigDecimal pregrossKharifCropArea;								//  gross_kharif_crop_area
	private BigDecimal pregrossRabiCropArea;							//   gross_rabi_crop_area
	private BigDecimal pregrossThirdCropArea; 							//   gross_third_crop_area
	private BigDecimal predifferentCropCereals;    					//   different_crop_cereals
	private BigDecimal predifferentCropPulses ;						//   different_crop_pulses
	private BigDecimal predifferentCropOilSeed ;						//   different_crop_oil_seed
	private BigDecimal predifferentCropMillets; 						//   different_crop_millets
	private BigDecimal predifferentCropOther ;						//   different_crop_other
	private BigDecimal prehorticultureArea ;							//	horticulture_area
	private BigDecimal prenetsownArea ;								//	netsown_area
	private BigDecimal precroppingIntensity ;							//	cropping_intensity
	private BigDecimal midgrossKharifCropArea;								//  gross_kharif_crop_area
	private BigDecimal midgrossRabiCropArea;							//   gross_rabi_crop_area
	private BigDecimal midgrossThirdCropArea; 							//   gross_third_crop_area
	private BigDecimal middifferentCropCereals;    					//   different_crop_cereals
	private BigDecimal middifferentCropPulses ;						//   different_crop_pulses
	private BigDecimal middifferentCropOilSeed ;						//   different_crop_oil_seed
	private BigDecimal middifferentCropMillets; 						//   different_crop_millets
	private BigDecimal middifferentCropOther ;						//   different_crop_other
	private BigDecimal midhorticultureArea ;							//	horticulture_area
	private BigDecimal midnetsownArea ;								//	netsown_area
	private BigDecimal midcroppingIntensity ;							//	cropping_intensity
	
	
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
	
	private String kharifCropremark;
	private String rabiCropremark; 
	private String thirdCropremark;
	
	private String cerealsremark;
	private String pulsesremark;
	
	private String oilSeedremark;
	private String milletsremark;
	
	private String othersremark;
	private String horticultureremark;
	
	private String netSownremark;
	private String cropIntensityremark;
	
	private String othercrop;
	
	
	
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ;
	private String requestIp; 
	
	
	public WdcpmksyCroppedDetails1() {
 	}

	
    public WdcpmksyCroppedDetails1(int croppedDetails1Id) {
        this.croppedDetails1Id = croppedDetails1Id;
    }
	
	public WdcpmksyCroppedDetails1(int croppedDetails1Id,
			WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation, BigDecimal pregrossKharifCropArea,
			BigDecimal pregrossRabiCropArea, BigDecimal pregrossThirdCropArea, BigDecimal predifferentCropCereals,
			BigDecimal predifferentCropPulses, BigDecimal predifferentCropOilSeed, BigDecimal predifferentCropMillets,
			BigDecimal predifferentCropOther, BigDecimal prehorticultureArea, BigDecimal prenetsownArea,
			BigDecimal precroppingIntensity, BigDecimal midgrossKharifCropArea, BigDecimal midgrossRabiCropArea,
			BigDecimal midgrossThirdCropArea, BigDecimal middifferentCropCereals, BigDecimal middifferentCropPulses,
			BigDecimal middifferentCropOilSeed, BigDecimal middifferentCropMillets, BigDecimal middifferentCropOther,
			BigDecimal midhorticultureArea, BigDecimal midnetsownArea, BigDecimal midcroppingIntensity,
			BigDecimal control_gross_kharif_crop_area, BigDecimal control_gross_rabi_crop_area,
			BigDecimal control_gross_third_crop_area, BigDecimal control_different_crop_cereals,
			BigDecimal control_different_crop_pulses, BigDecimal control_different_crop_oil_seed,
			BigDecimal control_different_crop_millets, BigDecimal control_different_crop_other,
			BigDecimal control_horticulture_area, BigDecimal control_netsown_area,
			BigDecimal control_cropping_intensity, String kharifCropremark, String rabiCropremark,
			String thirdCropremark, String cerealsremark, String pulsesremark, String oilSeedremark,
			String milletsremark, String othersremark, String horticultureremark, String netSownremark,
			String cropIntensityremark, String othercrop, Date createdOn, String createdBy, Date updatedOn, String requestIp) {
		super();
		this.croppedDetails1Id = croppedDetails1Id;
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
		this.pregrossKharifCropArea = pregrossKharifCropArea;
		this.pregrossRabiCropArea = pregrossRabiCropArea;
		this.pregrossThirdCropArea = pregrossThirdCropArea;
		this.predifferentCropCereals = predifferentCropCereals;
		this.predifferentCropPulses = predifferentCropPulses;
		this.predifferentCropOilSeed = predifferentCropOilSeed;
		this.predifferentCropMillets = predifferentCropMillets;
		this.predifferentCropOther = predifferentCropOther;
		this.prehorticultureArea = prehorticultureArea;
		this.prenetsownArea = prenetsownArea;
		this.precroppingIntensity = precroppingIntensity;
		this.midgrossKharifCropArea = midgrossKharifCropArea;
		this.midgrossRabiCropArea = midgrossRabiCropArea;
		this.midgrossThirdCropArea = midgrossThirdCropArea;
		this.middifferentCropCereals = middifferentCropCereals;
		this.middifferentCropPulses = middifferentCropPulses;
		this.middifferentCropOilSeed = middifferentCropOilSeed;
		this.middifferentCropMillets = middifferentCropMillets;
		this.middifferentCropOther = middifferentCropOther;
		this.midhorticultureArea = midhorticultureArea;
		this.midnetsownArea = midnetsownArea;
		this.midcroppingIntensity = midcroppingIntensity;
		this.control_gross_kharif_crop_area = control_gross_kharif_crop_area;
		this.control_gross_rabi_crop_area = control_gross_rabi_crop_area;
		this.control_gross_third_crop_area = control_gross_third_crop_area;
		this.control_different_crop_cereals = control_different_crop_cereals;
		this.control_different_crop_pulses = control_different_crop_pulses;
		this.control_different_crop_oil_seed = control_different_crop_oil_seed;
		this.control_different_crop_millets = control_different_crop_millets;
		this.control_different_crop_other = control_different_crop_other;
		this.control_horticulture_area = control_horticulture_area;
		this.control_netsown_area = control_netsown_area;
		this.control_cropping_intensity = control_cropping_intensity;
		this.kharifCropremark = kharifCropremark;
		this.rabiCropremark = rabiCropremark;
		this.thirdCropremark = thirdCropremark;
		this.cerealsremark = cerealsremark;
		this.pulsesremark = pulsesremark;
		this.oilSeedremark = oilSeedremark;
		this.milletsremark = milletsremark;
		this.othersremark = othersremark;
		this.horticultureremark = horticultureremark;
		this.netSownremark = netSownremark;
		this.cropIntensityremark = cropIntensityremark;
		this.othercrop = othercrop;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.requestIp = requestIp;
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
	
	@Column(name="pre_gross_kharif_crop_area", precision=20)
	public BigDecimal getPregrossKharifCropArea() {
		return pregrossKharifCropArea;
	}

	public void setPregrossKharifCropArea(BigDecimal pregrossKharifCropArea) {
		this.pregrossKharifCropArea = pregrossKharifCropArea;
	}

	@Column(name="pre_gross_rabi_crop_area", precision=20)
	public BigDecimal getPregrossRabiCropArea() {
		return pregrossRabiCropArea;
	}

	public void setPregrossRabiCropArea(BigDecimal pregrossRabiCropArea) {
		this.pregrossRabiCropArea = pregrossRabiCropArea;
	}

	@Column(name="pre_gross_third_crop_area", precision=20)
	public BigDecimal getPregrossThirdCropArea() {
		return pregrossThirdCropArea;
	}

	public void setPregrossThirdCropArea(BigDecimal pregrossThirdCropArea) {
		this.pregrossThirdCropArea = pregrossThirdCropArea;
	}

	@Column(name="pre_different_crop_cereals", precision=20)
	public BigDecimal getPredifferentCropCereals() {
		return predifferentCropCereals;
	}

	public void setPredifferentCropCereals(BigDecimal predifferentCropCereals) {
		this.predifferentCropCereals = predifferentCropCereals;
	}

	@Column(name="pre_different_crop_pulses", precision=20)
	public BigDecimal getPredifferentCropPulses() {
		return predifferentCropPulses;
	}

	public void setPredifferentCropPulses(BigDecimal predifferentCropPulses) {
		this.predifferentCropPulses = predifferentCropPulses;
	}

	@Column(name="pre_different_crop_oil_seed", precision=20)
	public BigDecimal getPredifferentCropOilSeed() {
		return predifferentCropOilSeed;
	}

	public void setPredifferentCropOilSeed(BigDecimal predifferentCropOilSeed) {
		this.predifferentCropOilSeed = predifferentCropOilSeed;
	}

	@Column(name="pre_different_crop_millets", precision=20)
	public BigDecimal getPredifferentCropMillets() {
		return predifferentCropMillets;
	}

	public void setPredifferentCropMillets(BigDecimal predifferentCropMillets) {
		this.predifferentCropMillets = predifferentCropMillets;
	}

	@Column(name="pre_different_crop_other", precision=20)
	public BigDecimal getPredifferentCropOther() {
		return predifferentCropOther;
	}

	public void setPredifferentCropOther(BigDecimal predifferentCropOther) {
		this.predifferentCropOther = predifferentCropOther;
	}

	@Column(name="pre_horticulture_area", precision=20)
	public BigDecimal getPrehorticultureArea() {
		return prehorticultureArea;
	}

	public void setPrehorticultureArea(BigDecimal prehorticultureArea) {
		this.prehorticultureArea = prehorticultureArea;
	}

	@Column(name="pre_netsown_area", precision=20)
	public BigDecimal getPrenetsownArea() {
		return prenetsownArea;
	}

	public void setPrenetsownArea(BigDecimal prenetsownArea) {
		this.prenetsownArea = prenetsownArea;
	}

	@Column(name="pre_cropping_intensity", precision=20)
	public BigDecimal getPrecroppingIntensity() {
		return precroppingIntensity;
	}

	public void setPrecroppingIntensity(BigDecimal precroppingIntensity) {
		this.precroppingIntensity = precroppingIntensity;
	}

	@Column(name="mid_gross_kharif_crop_area", precision=20)
	public BigDecimal getMidgrossKharifCropArea() {
		return midgrossKharifCropArea;
	}

	public void setMidgrossKharifCropArea(BigDecimal midgrossKharifCropArea) {
		this.midgrossKharifCropArea = midgrossKharifCropArea;
	}

	@Column(name="mid_gross_rabi_crop_area", precision=20)
	public BigDecimal getMidgrossRabiCropArea() {
		return midgrossRabiCropArea;
	}

	public void setMidgrossRabiCropArea(BigDecimal midgrossRabiCropArea) {
		this.midgrossRabiCropArea = midgrossRabiCropArea;
	}

	@Column(name="mid_gross_third_crop_area", precision=20)
	public BigDecimal getMidgrossThirdCropArea() {
		return midgrossThirdCropArea;
	}

	public void setMidgrossThirdCropArea(BigDecimal midgrossThirdCropArea) {
		this.midgrossThirdCropArea = midgrossThirdCropArea;
	}

	@Column(name="mid_different_crop_cereals", precision=20)
	public BigDecimal getMiddifferentCropCereals() {
		return middifferentCropCereals;
	}

	public void setMiddifferentCropCereals(BigDecimal middifferentCropCereals) {
		this.middifferentCropCereals = middifferentCropCereals;
	}

	@Column(name="mid_different_crop_pulses", precision=20)
	public BigDecimal getMiddifferentCropPulses() {
		return middifferentCropPulses;
	}

	public void setMiddifferentCropPulses(BigDecimal middifferentCropPulses) {
		this.middifferentCropPulses = middifferentCropPulses;
	}

	@Column(name="mid_different_crop_oil_seed", precision=20)
	public BigDecimal getMiddifferentCropOilSeed() {
		return middifferentCropOilSeed;
	}

	public void setMiddifferentCropOilSeed(BigDecimal middifferentCropOilSeed) {
		this.middifferentCropOilSeed = middifferentCropOilSeed;
	}

	@Column(name="mid_different_crop_millets", precision=20)
	public BigDecimal getMiddifferentCropMillets() {
		return middifferentCropMillets;
	}

	public void setMiddifferentCropMillets(BigDecimal middifferentCropMillets) {
		this.middifferentCropMillets = middifferentCropMillets;
	}

	@Column(name="mid_different_crop_other", precision=20)
	public BigDecimal getMiddifferentCropOther() {
		return middifferentCropOther;
	}

	public void setMiddifferentCropOther(BigDecimal middifferentCropOther) {
		this.middifferentCropOther = middifferentCropOther;
	}

	@Column(name="mid_horticulture_area", precision=20)
	public BigDecimal getMidhorticultureArea() {
		return midhorticultureArea;
	}

	public void setMidhorticultureArea(BigDecimal midhorticultureArea) {
		this.midhorticultureArea = midhorticultureArea;
	}

	@Column(name="mid_netsown_area", precision=20)
	public BigDecimal getMidnetsownArea() {
		return midnetsownArea;
	}

	public void setMidnetsownArea(BigDecimal midnetsownArea) {
		this.midnetsownArea = midnetsownArea;
	}

	@Column(name="mid_cropping_intensity", precision=20)
	public BigDecimal getMidcroppingIntensity() {
		return midcroppingIntensity;
	}

	public void setMidcroppingIntensity(BigDecimal midcroppingIntensity) {
		this.midcroppingIntensity = midcroppingIntensity;
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

	public String getKharifCropremark() {
		return kharifCropremark;
	}

	public void setKharifCropremark(String kharifCropremark) {
		this.kharifCropremark = kharifCropremark;
	}

	public String getRabiCropremark() {
		return rabiCropremark;
	}

	public void setRabiCropremark(String rabiCropremark) {
		this.rabiCropremark = rabiCropremark;
	}

	public String getThirdCropremark() {
		return thirdCropremark;
	}

	public void setThirdCropremark(String thirdCropremark) {
		this.thirdCropremark = thirdCropremark;
	}

	public String getCerealsremark() {
		return cerealsremark;
	}

	public void setCerealsremark(String cerealsremark) {
		this.cerealsremark = cerealsremark;
	}

	public String getPulsesremark() {
		return pulsesremark;
	}

	public void setPulsesremark(String pulsesremark) {
		this.pulsesremark = pulsesremark;
	}

	public String getOilSeedremark() {
		return oilSeedremark;
	}

	public void setOilSeedremark(String oilSeedremark) {
		this.oilSeedremark = oilSeedremark;
	}

	public String getMilletsremark() {
		return milletsremark;
	}

	public void setMilletsremark(String milletsremark) {
		this.milletsremark = milletsremark;
	}

	public String getOthersremark() {
		return othersremark;
	}

	public void setOthersremark(String othersremark) {
		this.othersremark = othersremark;
	}

	public String getHorticultureremark() {
		return horticultureremark;
	}

	public void setHorticultureremark(String horticultureremark) {
		this.horticultureremark = horticultureremark;
	}

	public String getNetSownremark() {
		return netSownremark;
	}

	public void setNetSownremark(String netSownremark) {
		this.netSownremark = netSownremark;
	}

	public String getCropIntensityremark() {
		return cropIntensityremark;
	}

	public void setCropIntensityremark(String cropIntensityremark) {
		this.cropIntensityremark = cropIntensityremark;
	}

	public String getOthercrop() {
		return othercrop;
	}


	public void setOthercrop(String othercrop) {
		this.othercrop = othercrop;
	}

	

}
