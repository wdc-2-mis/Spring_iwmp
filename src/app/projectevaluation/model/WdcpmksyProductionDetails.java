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
@Table(name="wdcpmksy_production_details" ,schema="public")
public class WdcpmksyProductionDetails implements java.io.Serializable{
	
	private int productionDetailId;							//   production_detail_id
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;					// project_profile_id
	//private Character projectControlled;               //   project_controlled
	private BigDecimal milchCattle;					// milch_cattle
	private BigDecimal fodderProduction; 				//  fodder_production
	private Integer ruralUrban;				//  rural_urban
	private Integer springRejuvenated;			//		spring_rejuvenated
	private Integer personBenefitte;			//   person_benefitte
	private Integer communityBasedShg; 			//  community_based_shg
	private Integer communityBasedFpo; 			//   community_based_fpo
	private Integer communityBasedUg; 			//   community_based_ug
	private Integer memberBasedShg;			//   member_based_shg
	private Integer memberBasedFpo;			//   member_based_fpo
	private Integer memberBasedUg;			//   member_based_ug
	private BigDecimal trunoverFpo; 			//   trunover_fpo
	private BigDecimal incomeFpo; 				//   income_fpo
	private BigDecimal annualIncomeShg; 		//   annual_income_shg
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ;
	private String requestIp; 
	
	private BigDecimal control_milch_cattle;
	private BigDecimal control_fodder_production;
	private Integer control_rural_urban;
	private Integer control_spring_rejuvenated ;
	private Integer control_person_benefitte ;
	private Integer control_community_based_shg ;
	private Integer control_community_based_fpo;
	private Integer control_community_based_ug;
	private Integer control_member_based_shg ;
	private Integer control_member_based_fpo ;
	private Integer control_member_based_ug ;
	private BigDecimal control_trunover_fpo;
	private BigDecimal control_income_fpo ;
	private BigDecimal control_annual_income_shg;
	
	
	
	
	
	
	
	
	public WdcpmksyProductionDetails() {
 	}

	
    public WdcpmksyProductionDetails(int productionDetailId) {
        this.productionDetailId = productionDetailId;
    }
    
    public WdcpmksyProductionDetails(int productionDetailId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, BigDecimal milchCattle, BigDecimal fodderProduction, Integer ruralUrban, Integer springRejuvenated, Integer personBenefitte, Integer communityBasedShg, Integer communityBasedFpo, Integer communityBasedUg, Integer memberBasedShg, Integer memberBasedFpo, Integer memberBasedUg,
    		BigDecimal trunoverFpo, BigDecimal incomeFpo, BigDecimal annualIncomeShg, Date createdOn, String createdBy, Date updatedOn, String requestIp) {
        this.productionDetailId = productionDetailId;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
       // this.projectControlled=projectControlled;
        this.milchCattle=milchCattle;
        this.fodderProduction=fodderProduction;
        this.ruralUrban=ruralUrban;
        this.springRejuvenated=springRejuvenated;
        this.personBenefitte=personBenefitte;
        this.communityBasedShg=communityBasedShg;
        this.communityBasedFpo=communityBasedFpo;
        this.communityBasedUg=communityBasedUg;
        this.memberBasedShg=memberBasedShg;
        this.memberBasedFpo=memberBasedFpo;
        this.memberBasedUg=memberBasedUg;
        this.trunoverFpo=trunoverFpo;
        this.incomeFpo=incomeFpo;
        this.annualIncomeShg=annualIncomeShg;
        this.createdOn=createdOn;
    	this.createdBy=createdBy;
    	this.updatedOn=updatedOn;
    	this.requestIp=requestIp;
         
    }

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="production_detail_id", unique=true, nullable=false)
	public int getProductionDetailId() {
		return productionDetailId;
	}


	public void setProductionDetailId(int productionDetailId) {
		this.productionDetailId = productionDetailId;
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

	@Column(name="milch_cattle", precision=20)
	public BigDecimal getMilchCattle() {
		return milchCattle;
	}


	public void setMilchCattle(BigDecimal milchCattle) {
		this.milchCattle = milchCattle;
	}

	@Column(name="fodder_production", precision=20)
	public BigDecimal getFodderProduction() {
		return fodderProduction;
	}


	public void setFodderProduction(BigDecimal fodderProduction) {
		this.fodderProduction = fodderProduction;
	}

	@Column(name="rural_urban", precision=20)
	public Integer getRuralUrban() {
		return ruralUrban;
	}


	public void setRuralUrban(Integer ruralUrban) {
		this.ruralUrban = ruralUrban;
	}

	@Column(name="spring_rejuvenated", precision=20)
	public Integer getSpringRejuvenated() {
		return springRejuvenated;
	}


	public void setSpringRejuvenated(Integer springRejuvenated) {
		this.springRejuvenated = springRejuvenated;
	}

	@Column(name="person_benefitte", precision=20)
	public Integer getPersonBenefitte() {
		return personBenefitte;
	}


	public void setPersonBenefitte(Integer personBenefitte) {
		this.personBenefitte = personBenefitte;
	}

	@Column(name="community_based_shg", precision=20)
	public Integer getCommunityBasedShg() {
		return communityBasedShg;
	}


	public void setCommunityBasedShg(Integer communityBasedShg) {
		this.communityBasedShg = communityBasedShg;
	}

	@Column(name="community_based_fpo", precision=20)
	public Integer getCommunityBasedFpo() {
		return communityBasedFpo;
	}


	public void setCommunityBasedFpo(Integer communityBasedFpo) {
		this.communityBasedFpo = communityBasedFpo;
	}

	@Column(name="community_based_ug", precision=20)
	public Integer getCommunityBasedUg() {
		return communityBasedUg;
	}


	public void setCommunityBasedUg(Integer communityBasedUg) {
		this.communityBasedUg = communityBasedUg;
	}

	@Column(name="member_based_shg", precision=20)
	public Integer getMemberBasedShg() {
		return memberBasedShg;
	}


	public void setMemberBasedShg(Integer memberBasedShg) {
		this.memberBasedShg = memberBasedShg;
	}

	@Column(name="member_based_fpo", precision=20)
	public Integer getMemberBasedFpo() {
		return memberBasedFpo;
	}


	public void setMemberBasedFpo(Integer memberBasedFpo) {
		this.memberBasedFpo = memberBasedFpo;
	}

	@Column(name="member_based_ug", precision=20)
	public Integer getMemberBasedUg() {
		return memberBasedUg;
	}


	public void setMemberBasedUg(Integer memberBasedUg) {
		this.memberBasedUg = memberBasedUg;
	}

	@Column(name="trunover_fpo", precision=20)
	public BigDecimal getTrunoverFpo() {
		return trunoverFpo;
	}


	public void setTrunoverFpo(BigDecimal trunoverFpo) {
		this.trunoverFpo = trunoverFpo;
	}

	@Column(name="income_fpo", precision=20)
	public BigDecimal getIncomeFpo() {
		return incomeFpo;
	}


	public void setIncomeFpo(BigDecimal incomeFpo) {
		this.incomeFpo = incomeFpo;
	}

	@Column(name="annual_income_shg", precision=20)
	public BigDecimal getAnnualIncomeShg() {
		return annualIncomeShg;
	}


	public void setAnnualIncomeShg(BigDecimal annualIncomeShg) {
		this.annualIncomeShg = annualIncomeShg;
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

	@Column(name="control_milch_cattle", precision=20)
	public BigDecimal getControl_milch_cattle() {
		return control_milch_cattle;
	}


	public void setControl_milch_cattle(BigDecimal control_milch_cattle) {
		this.control_milch_cattle = control_milch_cattle;
	}

	@Column(name="control_fodder_production", precision=20)
	public BigDecimal getControl_fodder_production() {
		return control_fodder_production;
	}


	public void setControl_fodder_production(BigDecimal control_fodder_production) {
		this.control_fodder_production = control_fodder_production;
	}

	@Column(name="control_rural_urban", precision=20)
	public Integer getControl_rural_urban() {
		return control_rural_urban;
	}


	public void setControl_rural_urban(Integer control_rural_urban) {
		this.control_rural_urban = control_rural_urban;
	}

	@Column(name="control_spring_rejuvenated", precision=20)
	public Integer getControl_spring_rejuvenated() {
		return control_spring_rejuvenated;
	}


	public void setControl_spring_rejuvenated(Integer control_spring_rejuvenated) {
		this.control_spring_rejuvenated = control_spring_rejuvenated;
	}

	@Column(name="control_person_benefitte", precision=20)
	public Integer getControl_person_benefitte() {
		return control_person_benefitte;
	}


	public void setControl_person_benefitte(Integer control_person_benefitte) {
		this.control_person_benefitte = control_person_benefitte;
	}

	@Column(name="control_community_based_shg", precision=20)
	public Integer getControl_community_based_shg() {
		return control_community_based_shg;
	}


	public void setControl_community_based_shg(Integer control_community_based_shg) {
		this.control_community_based_shg = control_community_based_shg;
	}

	@Column(name="control_community_based_fpo", precision=20)
	public Integer getControl_community_based_fpo() {
		return control_community_based_fpo;
	}


	public void setControl_community_based_fpo(Integer control_community_based_fpo) {
		this.control_community_based_fpo = control_community_based_fpo;
	}

	@Column(name="control_community_based_ug", precision=20)
	public Integer getControl_community_based_ug() {
		return control_community_based_ug;
	}


	public void setControl_community_based_ug(Integer control_community_based_ug) {
		this.control_community_based_ug = control_community_based_ug;
	}

	@Column(name="control_member_based_shg", precision=20)
	public Integer getControl_member_based_shg() {
		return control_member_based_shg;
	}


	public void setControl_member_based_shg(Integer control_member_based_shg) {
		this.control_member_based_shg = control_member_based_shg;
	}

	@Column(name="control_member_based_fpo", precision=20)
	public Integer getControl_member_based_fpo() {
		return control_member_based_fpo;
	}


	public void setControl_member_based_fpo(Integer control_member_based_fpo) {
		this.control_member_based_fpo = control_member_based_fpo;
	}

	@Column(name="control_member_based_ug", precision=20)
	public Integer getControl_member_based_ug() {
		return control_member_based_ug;
	}


	public void setControl_member_based_ug(Integer control_member_based_ug) {
		this.control_member_based_ug = control_member_based_ug;
	}

	@Column(name="control_trunover_fpo", precision=20)
	public BigDecimal getControl_trunover_fpo() {
		return control_trunover_fpo;
	}


	public void setControl_trunover_fpo(BigDecimal control_trunover_fpo) {
		this.control_trunover_fpo = control_trunover_fpo;
	}

	@Column(name="control_income_fpo", precision=20)
	public BigDecimal getControl_income_fpo() {
		return control_income_fpo;
	}


	public void setControl_income_fpo(BigDecimal control_income_fpo) {
		this.control_income_fpo = control_income_fpo;
	}

	@Column(name="control_annual_income_shg", precision=20)
	public BigDecimal getControl_annual_income_shg() {
		return control_annual_income_shg;
	}


	public void setControl_annual_income_shg(BigDecimal control_annual_income_shg) {
		this.control_annual_income_shg = control_annual_income_shg;
	}
    
    
    
    
    
    
    
    
    
	

}
