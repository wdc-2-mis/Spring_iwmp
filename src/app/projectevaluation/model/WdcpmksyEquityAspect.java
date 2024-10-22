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
@Table(name="wdcpmksy_equity_aspect" ,schema="public")
public class WdcpmksyEquityAspect implements java.io.Serializable{
	
	
	private int  equityAspectId;							///     equity_aspect_id
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;				//project_profile_id
	//private Character projectControlled;               //   project_controlled
	private Boolean waterCommittee;						//   water_committee
	private String waterCommitteeRemark;				//   water_committee_remark
    private Boolean fpoShgVli;						//   fpo_shg_vli
    private String fpoShgVliRemark;					//		fpo_shg_vli_remark
    private Boolean livelihoodOption; 				//    livelihood_option
    private String livelihoodOptionRemark;			// livelihood_option_remark
    private Date createdOn ;                    //created_on ;
   	private String createdBy ;         //created_by ;
   	private Date updatedOn ;                 //updated_on ;
   	private String requestIp; 
   	
   	private Boolean controlWaterCommittee;    //    control_water_committee
   	private Boolean controlFpoShgVli;        //    control_fpo_shg_vli
   	private Boolean controlLivelihoodOption;          //   control_livelihood_option
	
	
   	public WdcpmksyEquityAspect() {
 	}
   	
   	public WdcpmksyEquityAspect(int equityAspectId) {
   		this.equityAspectId=equityAspectId;
 	}
   	
	public WdcpmksyEquityAspect(int equityAspectId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, Character projectControlled, Boolean waterCommittee, String waterCommitteeRemark, Boolean fpoShgVli,
			String fpoShgVliRemark, Boolean livelihoodOption, String livelihoodOptionRemark, Date createdOn, String createdBy, Date updatedOn, String requestIp) {
   		this.equityAspectId=equityAspectId;
   		this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
   		//this.projectControlled=projectControlled;
   		this.waterCommittee=waterCommittee;
   		this.waterCommitteeRemark=waterCommitteeRemark;
   		this.fpoShgVli=fpoShgVli;
   		this.fpoShgVliRemark=fpoShgVliRemark;
   		this.livelihoodOption=livelihoodOption;
   		this.livelihoodOptionRemark=livelihoodOptionRemark;
   		this.createdOn=createdOn;
    	this.createdBy=createdBy;
    	this.updatedOn=updatedOn;
    	this.requestIp=requestIp;
   		
 	}

	@Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="equity_aspect_id", unique=true, nullable=false)
	public int getEquityAspectId() {
		return equityAspectId;
	}

	public void setEquityAspectId(int equityAspectId) {
		this.equityAspectId = equityAspectId;
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
	 * public void setProjectControlled(Character projectControlled) {
	 * this.projectControlled = projectControlled; }
	 */

	@Column(name="water_committee")
	public Boolean getWaterCommittee() {
		return waterCommittee;
	}

	public void setWaterCommittee(Boolean waterCommittee) {
		this.waterCommittee = waterCommittee;
	}

	@Column(name="water_committee_remark", length=200)
	public String getWaterCommitteeRemark() {
		return waterCommitteeRemark;
	}

	public void setWaterCommitteeRemark(String waterCommitteeRemark) {
		this.waterCommitteeRemark = waterCommitteeRemark;
	}

	@Column(name="fpo_shg_vli")
	public Boolean getFpoShgVli() {
		return fpoShgVli;
	}

	public void setFpoShgVli(Boolean fpoShgVli) {
		this.fpoShgVli = fpoShgVli;
	}

	@Column(name="fpo_shg_vli_remark", length=200)
	public String getFpoShgVliRemark() {
		return fpoShgVliRemark;
	}

	public void setFpoShgVliRemark(String fpoShgVliRemark) {
		this.fpoShgVliRemark = fpoShgVliRemark;
	}

	@Column(name="livelihood_option")
	public Boolean getLivelihoodOption() {
		return livelihoodOption;
	}

	public void setLivelihoodOption(Boolean livelihoodOption) {
		this.livelihoodOption = livelihoodOption;
	}

	@Column(name="livelihood_option_remark", length=200)
	public String getLivelihoodOptionRemark() {
		return livelihoodOptionRemark;
	}

	public void setLivelihoodOptionRemark(String livelihoodOptionRemark) {
		this.livelihoodOptionRemark = livelihoodOptionRemark;
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

	@Column(name="control_water_committee")
	public Boolean getControlWaterCommittee() {
		return controlWaterCommittee;
	}

	public void setControlWaterCommittee(Boolean controlWaterCommittee) {
		this.controlWaterCommittee = controlWaterCommittee;
	}

	@Column(name="control_fpo_shg_vli")
	public Boolean getControlFpoShgVli() {
		return controlFpoShgVli;
	}

	public void setControlFpoShgVli(Boolean controlFpoShgVli) {
		this.controlFpoShgVli = controlFpoShgVli;
	}

	@Column(name="control_livelihood_option")
	public Boolean getControlLivelihoodOption() {
		return controlLivelihoodOption;
	}

	public void setControlLivelihoodOption(Boolean controlLivelihoodOption) {
		this.controlLivelihoodOption = controlLivelihoodOption;
	}
	
	
	
	
	
	

}
