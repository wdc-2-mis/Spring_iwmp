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
	
	private BigDecimal prefarmerIncome; 									
	private BigDecimal midfarmerIncome;
	private BigDecimal controlfarmerincome ;
	private String 	remarkFormerIncome;
	
	private Integer farmerBenefited;
	private Integer control_farmer_benefited ;
	private String 	remarkFormerBenefited;
	
	private Integer mandaysGenerated;								
	private Integer  control_mandays_generated ;
	private String  remarkmandaysGenerated ;
	
	private BigDecimal predugWell ;
	private BigDecimal middugWell ;
	private BigDecimal control_dug_well;
	private String remarkdugWell; 
	
	private BigDecimal pretubeWell ;
	private BigDecimal midtubeWell ;
	private BigDecimal control_tube_well;
	private String remarktubeWell;
	
	private Date createdOn ;                    
	private String createdBy ;         
	private Date updatedOn ;                 
	private String requestIp; 
	
	public WdcpmksyMandaysDetails() {
    }

	
    public WdcpmksyMandaysDetails(int mandaysDetailId) {
        this.mandaysDetailId = mandaysDetailId;
    }
    
    public WdcpmksyMandaysDetails(int mandaysDetailId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation,BigDecimal prefarmerIncome,
    		BigDecimal midfarmerIncome, BigDecimal controlfarmerincome, String remarkFormerIncome, Integer farmerBenefited,Integer control_farmer_benefited,
    		String 	remarkFormerBenefited, Integer mandaysGenerated,Integer  control_mandays_generated,String  remarkmandaysGenerated, String remarkdugWell, BigDecimal control_dug_well,BigDecimal middugWell, 
    		BigDecimal predugWell, BigDecimal pretubeWell, BigDecimal midtubeWell,BigDecimal control_tube_well, String remarktubeWell,  Date createdOn, String createdBy, Date updatedOn, String requestIp) {
        this.mandaysDetailId = mandaysDetailId;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
        this.prefarmerIncome=prefarmerIncome;
        this.midfarmerIncome=midfarmerIncome;
        this.controlfarmerincome=controlfarmerincome;
        this.remarkFormerIncome=remarkFormerIncome;
        this.farmerBenefited=farmerBenefited;
        this.control_farmer_benefited=control_farmer_benefited;
        this.remarkFormerBenefited=remarkFormerBenefited;
        this.mandaysGenerated=mandaysGenerated;
        this.control_mandays_generated=control_mandays_generated;
        this.remarkmandaysGenerated=remarkmandaysGenerated;
        this.predugWell=predugWell;
        this.middugWell=middugWell;
        this.control_dug_well=control_dug_well;
        this.remarkdugWell=remarkdugWell;
        this.pretubeWell=pretubeWell;
        this.midtubeWell=midtubeWell;
        this.control_tube_well=control_tube_well;
        this.remarktubeWell=remarktubeWell;
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

	@Column(name="pre_farmer_income", precision=20)
	public BigDecimal getPrefarmerIncome() {
		return prefarmerIncome;
	}


	public void setPrefarmerIncome(BigDecimal prefarmerIncome) {
		this.prefarmerIncome = prefarmerIncome;
	}

	@Column(name="mid_farmer_income", precision=20)
	public BigDecimal getMidfarmerIncome() {
		return midfarmerIncome;
	}


	public void setMidfarmerIncome(BigDecimal midfarmerIncome) {
		this.midfarmerIncome = midfarmerIncome;
	}

	@Column(name="control_farmer_income", precision=20)
	public BigDecimal getControlfarmerincome() {
		return controlfarmerincome;
	}


	public void setControlfarmerincome(BigDecimal controlfarmerincome) {
		this.controlfarmerincome = controlfarmerincome;
	}

	@Column(name="remark_farmer_income", length=200)
	public String getRemarkFormerIncome() {
		return remarkFormerIncome;
	}


	public void setRemarkFormerIncome(String remarkFormerIncome) {
		this.remarkFormerIncome = remarkFormerIncome;
	}

	@Column(name="remark_farmer_benefited", length=200)
	public String getRemarkFormerBenefited() {
		return remarkFormerBenefited;
	}


	public void setRemarkFormerBenefited(String remarkFormerBenefited) {
		this.remarkFormerBenefited = remarkFormerBenefited;
	}

	@Column(name="remark_mandays_generated", length=200)
	public String getRemarkmandaysGenerated() {
		return remarkmandaysGenerated;
	}


	public void setRemarkmandaysGenerated(String remarkmandaysGenerated) {
		this.remarkmandaysGenerated = remarkmandaysGenerated;
	}

	@Column(name="pre_dug_well", precision=20)
	public BigDecimal getPredugWell() {
		return predugWell;
	}


	public void setPredugWell(BigDecimal predugWell) {
		this.predugWell = predugWell;
	}

	@Column(name="mid_dug_well", precision=20)
	public BigDecimal getMiddugWell() {
		return middugWell;
	}


	public void setMiddugWell(BigDecimal middugWell) {
		this.middugWell = middugWell;
	}

	@Column(name="remark_dug_well", length=200)
	public String getRemarkdugWell() {
		return remarkdugWell;
	}


	public void setRemarkdugWell(String remarkdugWell) {
		this.remarkdugWell = remarkdugWell;
	}

	@Column(name="pre_tube_well", precision=20)
	public BigDecimal getPretubeWell() {
		return pretubeWell;
	}


	public void setPretubeWell(BigDecimal pretubeWell) {
		this.pretubeWell = pretubeWell;
	}

	@Column(name="mid_tube_well", precision=20)
	public BigDecimal getMidtubeWell() {
		return midtubeWell;
	}


	public void setMidtubeWell(BigDecimal midtubeWell) {
		this.midtubeWell = midtubeWell;
	}

	@Column(name="remark_tube_well", length=200)
	public String getRemarktubeWell() {
		return remarktubeWell;
	}

	public void setRemarktubeWell(String remarktubeWell) {
		this.remarktubeWell = remarktubeWell;
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
