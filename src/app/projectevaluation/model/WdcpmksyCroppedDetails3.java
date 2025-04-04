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
@Table(name="wdcpmksy_cropped_details_3" ,schema="public")
public class WdcpmksyCroppedDetails3 implements java.io.Serializable {
	
	private int croppedDetails3Id;								//  cropped_details_3_id
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;					// project_profile_id	
	private BigDecimal prePlantationCover;				// pre_plantation_cover
	private BigDecimal midPlantationCover;				// mid_plantation_cover
	private BigDecimal controlPlantationCover;			// control_plantation_cover
	private String remarkPlantationCover;				// remark_plantation_cover
	private BigDecimal preRice;							// pre_rice
	private BigDecimal midRice;							// mid_rice
	private BigDecimal controlRice;						// control_rice
	private String remarkRice;							// remark_rice
	private BigDecimal preWheat;						// pre_wheat
	private BigDecimal midWheat;						// mid_wheat
	private BigDecimal controlWheat;					// control_wheat
	private String remarkWheat;							// remark_wheat
	private BigDecimal prePulses;						// pre_pulses
	private BigDecimal midPulses;						// mid_pulses
	private BigDecimal controlPulses;					// control_pulses
	private String remarkPulses;						// remark_pulses
	private BigDecimal preOilSeed;						// pre_oil_seed
	private BigDecimal midOilSeed;						// mid_oil_seed
	private BigDecimal controlOilSeed;					// control_oil_seed
	private String remarkOilSeed;						// remark_oil_seed
	private BigDecimal preMillets;						// pre_millets
	private BigDecimal midMillets;						// mid_millets
	private BigDecimal controlMillets;					// control_millets
	private String remarkMillets;						// remark_millets
	private BigDecimal preOther;						// pre_other
	private BigDecimal midOther;						// mid_other
	private BigDecimal controlOther;					// control_other
	private String remarkOther;							// remark_other
	private BigDecimal preCulturableWasteland;			// pre_culturable_wasteland
	private BigDecimal midCulturableWasteland;			// mid_culturable_wasteland
	private BigDecimal controlCulturableWasteland;		// control_culturable_wasteland
	private String remarkCulturableWasteland;			// remark_culturable_wasteland
	private BigDecimal preProtectiveIrrigation;			// pre_protective_irrigation
	private BigDecimal midProtectiveIrrigation;			// mid_protective_irrigation
	private BigDecimal controlProtectiveIrrigation;		// control_protective_irrigation
	private String remarkProtectiveIrrigation;			// remark_protective_irrigation
	private String othercrop;
	private Date createdOn;          	          		//created_on
	private String createdBy;         					//created_by
	private Date updatedOn;                 			//updated_on
	private String requestIp; 							//request_ip
	
	
	
	public WdcpmksyCroppedDetails3() {
 	}

    public WdcpmksyCroppedDetails3(int croppedDetails3Id) {
        this.croppedDetails3Id = croppedDetails3Id;
    }

	public WdcpmksyCroppedDetails3(int croppedDetails3Id, WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation, BigDecimal prePlantationCover,
			BigDecimal midPlantationCover, BigDecimal controlPlantationCover, String remarkPlantationCover, BigDecimal preRice, BigDecimal midRice, 
			BigDecimal controlRice, String remarkRice, BigDecimal preWheat, BigDecimal midWheat, BigDecimal controlWheat, String remarkWheat, BigDecimal prePulses,
			BigDecimal midPulses, BigDecimal controlPulses, String remarkPulses, BigDecimal preOilSeed, BigDecimal midOilSeed, BigDecimal controlOilSeed, 
			String remarkOilSeed, BigDecimal preMillets, BigDecimal midMillets, BigDecimal controlMillets, String remarkMillets, BigDecimal preOther,
			BigDecimal midOther, BigDecimal controlOther, String remarkOther, BigDecimal preCulturableWasteland, BigDecimal midCulturableWasteland, 
			BigDecimal controlCulturableWasteland, String remarkCulturableWasteland, BigDecimal preProtectiveIrrigation, BigDecimal midProtectiveIrrigation,
			BigDecimal controlProtectiveIrrigation, String remarkProtectiveIrrigation, String othercrop, Date createdOn, String createdBy, Date updatedOn, String requestIp) {
		
		this.croppedDetails3Id = croppedDetails3Id;
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
		this.prePlantationCover = prePlantationCover;
		this.midPlantationCover = midPlantationCover;
		this.controlPlantationCover = controlPlantationCover;
		this.remarkPlantationCover = remarkPlantationCover;
		this.preRice = preRice;
		this.midRice = midRice;
		this.controlRice = controlRice;
		this.remarkRice = remarkRice;
		this.preWheat = preWheat;
		this.midWheat = midWheat;
		this.controlWheat = controlWheat;
		this.remarkWheat = remarkWheat;
		this.prePulses = prePulses;
		this.midPulses = midPulses;
		this.controlPulses = controlPulses;
		this.remarkPulses = remarkPulses;
		this.preOilSeed = preOilSeed;
		this.midOilSeed = midOilSeed;
		this.controlOilSeed = controlOilSeed;
		this.remarkOilSeed = remarkOilSeed;
		this.preMillets = preMillets;
		this.midMillets = midMillets;
		this.controlMillets = controlMillets;
		this.remarkMillets = remarkMillets;
		this.preOther = preOther;
		this.midOther = midOther;
		this.controlOther = controlOther;
		this.remarkOther = remarkOther;
		this.preCulturableWasteland = preCulturableWasteland;
		this.midCulturableWasteland = midCulturableWasteland;
		this.controlCulturableWasteland = controlCulturableWasteland;
		this.remarkCulturableWasteland = remarkCulturableWasteland;
		this.preProtectiveIrrigation = preProtectiveIrrigation;
		this.midProtectiveIrrigation = midProtectiveIrrigation;
		this.controlProtectiveIrrigation = controlProtectiveIrrigation;
		this.remarkProtectiveIrrigation = remarkProtectiveIrrigation;
		this.othercrop = othercrop;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.requestIp = requestIp;
		
	}

	
	@Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="cropped_details_3_id", unique=true, nullable=false)
	public int getCroppedDetails3Id() {
		return croppedDetails3Id;
	}

	public void setCroppedDetails3Id(int croppedDetails3Id) {
		this.croppedDetails3Id = croppedDetails3Id;
	}

	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_profile_id")
	public WdcpmksyProjectProfileEvaluation getWdcpmksyProjectProfileEvaluation() {
		return wdcpmksyProjectProfileEvaluation;
	}

	public void setWdcpmksyProjectProfileEvaluation(WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation) {
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
	}


	@Column(name="pre_plantation_cover", precision=20)
	public BigDecimal getPrePlantationCover() {
		return prePlantationCover;
	}

	public void setPrePlantationCover(BigDecimal prePlantationCover) {
		this.prePlantationCover = prePlantationCover;
	}


	@Column(name="mid_plantation_cover", precision=20)
	public BigDecimal getMidPlantationCover() {
		return midPlantationCover;
	}

	public void setMidPlantationCover(BigDecimal midPlantationCover) {
		this.midPlantationCover = midPlantationCover;
	}


	@Column(name="control_plantation_cover", precision=20)
	public BigDecimal getControlPlantationCover() {
		return controlPlantationCover;
	}

	public void setControlPlantationCover(BigDecimal controlPlantationCover) {
		this.controlPlantationCover = controlPlantationCover;
	}


	@Column(name="remark_plantation_cover", length=200)
	public String getRemarkPlantationCover() {
		return remarkPlantationCover;
	}

	public void setRemarkPlantationCover(String remarkPlantationCover) {
		this.remarkPlantationCover = remarkPlantationCover;
	}


	@Column(name="pre_rice", precision=20)
	public BigDecimal getPreRice() {
		return preRice;
	}

	public void setPreRice(BigDecimal preRice) {
		this.preRice = preRice;
	}


	@Column(name="mid_rice", precision=20)
	public BigDecimal getMidRice() {
		return midRice;
	}

	public void setMidRice(BigDecimal midRice) {
		this.midRice = midRice;
	}


	@Column(name="control_rice", precision=20)
	public BigDecimal getControlRice() {
		return controlRice;
	}

	public void setControlRice(BigDecimal controlRice) {
		this.controlRice = controlRice;
	}


	@Column(name="remark_rice", length=200)
	public String getRemarkRice() {
		return remarkRice;
	}

	public void setRemarkRice(String remarkRice) {
		this.remarkRice = remarkRice;
	}


	@Column(name="pre_wheat", precision=20)
	public BigDecimal getPreWheat() {
		return preWheat;
	}

	public void setPreWheat(BigDecimal preWheat) {
		this.preWheat = preWheat;
	}


	@Column(name="mid_wheat", precision=20)
	public BigDecimal getMidWheat() {
		return midWheat;
	}

	public void setMidWheat(BigDecimal midWheat) {
		this.midWheat = midWheat;
	}


	@Column(name="control_wheat", precision=20)
	public BigDecimal getControlWheat() {
		return controlWheat;
	}

	public void setControlWheat(BigDecimal controlWheat) {
		this.controlWheat = controlWheat;
	}


	@Column(name="remark_wheat", length=200)
	public String getRemarkWheat() {
		return remarkWheat;
	}

	public void setRemarkWheat(String remarkWheat) {
		this.remarkWheat = remarkWheat;
	}


	@Column(name="pre_pulses", precision=20)
	public BigDecimal getPrePulses() {
		return prePulses;
	}

	public void setPrePulses(BigDecimal prePulses) {
		this.prePulses = prePulses;
	}


	@Column(name="mid_pulses", precision=20)
	public BigDecimal getMidPulses() {
		return midPulses;
	}

	public void setMidPulses(BigDecimal midPulses) {
		this.midPulses = midPulses;
	}


	@Column(name="control_pulses", precision=20)
	public BigDecimal getControlPulses() {
		return controlPulses;
	}

	public void setControlPulses(BigDecimal controlPulses) {
		this.controlPulses = controlPulses;
	}


	@Column(name="remark_pulses", length=200)
	public String getRemarkPulses() {
		return remarkPulses;
	}

	public void setRemarkPulses(String remarkPulses) {
		this.remarkPulses = remarkPulses;
	}


	@Column(name="pre_oil_seed", precision=20)
	public BigDecimal getPreOilSeed() {
		return preOilSeed;
	}

	public void setPreOilSeed(BigDecimal preOilSeed) {
		this.preOilSeed = preOilSeed;
	}


	@Column(name="mid_oil_seed", precision=20)
	public BigDecimal getMidOilSeed() {
		return midOilSeed;
	}

	public void setMidOilSeed(BigDecimal midOilSeed) {
		this.midOilSeed = midOilSeed;
	}


	@Column(name="control_oil_seed", precision=20)
	public BigDecimal getControlOilSeed() {
		return controlOilSeed;
	}

	public void setControlOilSeed(BigDecimal controlOilSeed) {
		this.controlOilSeed = controlOilSeed;
	}


	@Column(name="remark_oil_seed", length=200)
	public String getRemarkOilSeed() {
		return remarkOilSeed;
	}

	public void setRemarkOilSeed(String remarkOilSeed) {
		this.remarkOilSeed = remarkOilSeed;
	}


	@Column(name="pre_millets", precision=20)
	public BigDecimal getPreMillets() {
		return preMillets;
	}

	public void setPreMillets(BigDecimal preMillets) {
		this.preMillets = preMillets;
	}


	@Column(name="mid_millets", precision=20)
	public BigDecimal getMidMillets() {
		return midMillets;
	}

	public void setMidMillets(BigDecimal midMillets) {
		this.midMillets = midMillets;
	}


	@Column(name="control_millets", precision=20)
	public BigDecimal getControlMillets() {
		return controlMillets;
	}

	public void setControlMillets(BigDecimal controlMillets) {
		this.controlMillets = controlMillets;
	}


	@Column(name="remark_millets", length=200)
	public String getRemarkMillets() {
		return remarkMillets;
	}

	public void setRemarkMillets(String remarkMillets) {
		this.remarkMillets = remarkMillets;
	}


	@Column(name="pre_other", precision=20)
	public BigDecimal getPreOther() {
		return preOther;
	}

	public void setPreOther(BigDecimal preOther) {
		this.preOther = preOther;
	}


	@Column(name="mid_other", precision=20)
	public BigDecimal getMidOther() {
		return midOther;
	}

	public void setMidOther(BigDecimal midOther) {
		this.midOther = midOther;
	}


	@Column(name="control_other", precision=20)
	public BigDecimal getControlOther() {
		return controlOther;
	}

	public void setControlOther(BigDecimal controlOther) {
		this.controlOther = controlOther;
	}


	@Column(name="remark_other", length=200)
	public String getRemarkOther() {
		return remarkOther;
	}

	public void setRemarkOther(String remarkOther) {
		this.remarkOther = remarkOther;
	}


	@Column(name="pre_culturable_wasteland", precision=20)
	public BigDecimal getPreCulturableWasteland() {
		return preCulturableWasteland;
	}

	public void setPreCulturableWasteland(BigDecimal preCulturableWasteland) {
		this.preCulturableWasteland = preCulturableWasteland;
	}


	@Column(name="mid_culturable_wasteland", precision=20)
	public BigDecimal getMidCulturableWasteland() {
		return midCulturableWasteland;
	}

	public void setMidCulturableWasteland(BigDecimal midCulturableWasteland) {
		this.midCulturableWasteland = midCulturableWasteland;
	}


	@Column(name="control_culturable_wasteland", precision=20)
	public BigDecimal getControlCulturableWasteland() {
		return controlCulturableWasteland;
	}

	public void setControlCulturableWasteland(BigDecimal controlCulturableWasteland) {
		this.controlCulturableWasteland = controlCulturableWasteland;
	}


	@Column(name="remark_culturable_wasteland", length=200)
	public String getRemarkCulturableWasteland() {
		return remarkCulturableWasteland;
	}

	public void setRemarkCulturableWasteland(String remarkCulturableWasteland) {
		this.remarkCulturableWasteland = remarkCulturableWasteland;
	}


	@Column(name="pre_protective_irrigation", precision=20)
	public BigDecimal getPreProtectiveIrrigation() {
		return preProtectiveIrrigation;
	}

	public void setPreProtectiveIrrigation(BigDecimal preProtectiveIrrigation) {
		this.preProtectiveIrrigation = preProtectiveIrrigation;
	}


	@Column(name="mid_protective_irrigation", precision=20)
	public BigDecimal getMidProtectiveIrrigation() {
		return midProtectiveIrrigation;
	}

	public void setMidProtectiveIrrigation(BigDecimal midProtectiveIrrigation) {
		this.midProtectiveIrrigation = midProtectiveIrrigation;
	}


	@Column(name="control_protective_irrigation", precision=20)
	public BigDecimal getControlProtectiveIrrigation() {
		return controlProtectiveIrrigation;
	}

	public void setControlProtectiveIrrigation(BigDecimal controlProtectiveIrrigation) {
		this.controlProtectiveIrrigation = controlProtectiveIrrigation;
	}


	@Column(name="remark_protective_irrigation", length=200)
	public String getRemarkProtectiveIrrigation() {
		return remarkProtectiveIrrigation;
	}

	public void setRemarkProtectiveIrrigation(String remarkProtectiveIrrigation) {
		this.remarkProtectiveIrrigation = remarkProtectiveIrrigation;
	}
	
	public String getOthercrop() {
		return othercrop;
	}

	public void setOthercrop(String othercrop) {
		this.othercrop = othercrop;
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
	
    
}
