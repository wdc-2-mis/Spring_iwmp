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
	
	private BigDecimal preMilchCattle;				// pre_milch_cattle
	private BigDecimal midMilchCattle;				// mid_milch_cattle
	private BigDecimal controlMilchCattle;			// control_milch_cattle
	private String remarkMilchCattle;				// remark_milch_cattle
	private BigDecimal preFodderProduction;			// pre_fodder_production
	private BigDecimal midFodderProduction;			// mid_fodder_production
	private BigDecimal controlFodderProduction;		// control_fodder_production
	private String remarkFodderProduction;			// remark_fodder_production
	private Integer preRuralUrban;					// pre_rural_urban
	private Integer midRuralUrban;					// mid_rural_urban
	private Integer controlRuralUrban;				// control_rural_urban
	private String remarkRuralUrban;				// remark_rural_urban
	private Integer preSpringRejuvenated;			// pre_spring_rejuvenated
	private Integer midSpringRejuvenated;			// mid_spring_rejuvenated
	private Integer controlSpringRejuvenated;		// control_spring_rejuvenated
	private String remarkSpringRejuvenated;			// remark_spring_rejuvenated
	private Integer prePersonBenefitte;				// pre_person_benefitte
	private Integer midPersonBenefitte;				// mid_person_benefitte
	private Integer controlPersonBenefitte;			// control_person_benefitte
	private String remarkPersonBenefitte;			// remark_person_benefitte
	private Integer preCommunityBasedShg;			// pre_community_based_shg
	private Integer midCommunityBasedShg;			// mid_community_based_shg
	private Integer controlCommunityBasedShg;		// control_community_based_shg
	private String remarkCommunityBasedShg;			// remark_community_based_shg
	private Integer preCommunityBasedFpo;			// pre_community_based_fpo
	private Integer midCommunityBasedFpo;			// mid_community_based_fpo
	private Integer controlCommunityBasedFpo;		// control_community_based_fpo
	private String remarkCommunityBasedFpo;			// remark_community_based_fpo
	private Integer preCommunityBasedUg;			// pre_community_based_ug
	private Integer midCommunityBasedUg;			// mid_community_based_ug
	private Integer controlCommunityBasedUg;		// control_community_based_ug
	private String remarkCommunityBasedUg;			// remark_community_based_ug
	private Integer preMemberBasedShg;				// pre_member_based_shg
	private Integer midMemberBasedShg;				// mid_member_based_shg
	private Integer controlMemberBasedShg;			// control_member_based_shg
	private String remarkMemberBasedShg;			// remark_member_based_shg
	private Integer preMemberBasedFpo;				// pre_member_based_fpo
	private Integer midMemberBasedFpo;				// mid_member_based_fpo
	private Integer controlMemberBasedFpo;			// control_member_based_fpo
	private String remarkMemberBasedFpo;			// remark_member_based_fpo
	private Integer preMemberBasedUg;				// pre_member_based_ug
	private Integer midMemberBasedUg;				// mid_member_based_ug
	private Integer controlMemberBasedUg;			// control_member_based_ug
	private String remarkMemberBasedUg;				// remark_member_based_ug
	private BigDecimal preTrunoverFpo;				// pre_trunover_fpo
	private BigDecimal midTrunoverFpo;				// mid_trunover_fpo
	private BigDecimal controlTrunoverFpo;			// control_trunover_fpo
	private String remarkTrunoverFpo;				// remark_trunover_fpo
	private BigDecimal preIncomeFpo;				// pre_income_fpo
	private BigDecimal midIncomeFpo;				// mid_income_fpo
	private BigDecimal controlIncomeFpo;			// control_income_fpo
	private String remarkIncomeFpo;					// remark_income_fpo
	private BigDecimal preAnnualIncomeShg;			// pre_annual_income_shg
	private BigDecimal midAnnualIncomeShg;			// mid_annual_income_shg
	private BigDecimal controlAnnualIncomeShg;		// control_annual_income_shg
    private String remarkAnnualIncomeShg;			// remark_annual_income_shg
    private Date createdOn;                    		// created_on
	private String createdBy;         				// created_by
	private Date updatedOn;               			// updated_on
	private String requestIp;						// request_ip
	
	
	
	public WdcpmksyProductionDetails() {
 	}

	
    public WdcpmksyProductionDetails(int productionDetailId) {
        this.productionDetailId = productionDetailId;
    }
    
    
   
    

    public WdcpmksyProductionDetails(int productionDetailId, WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation, BigDecimal preMilchCattle,
			BigDecimal midMilchCattle, BigDecimal controlMilchCattle, String remarkMilchCattle, BigDecimal preFodderProduction, BigDecimal midFodderProduction, BigDecimal controlFodderProduction,
			String remarkFodderProduction, Integer preRuralUrban, Integer midRuralUrban, Integer controlRuralUrban,	String remarkRuralUrban, Integer preSpringRejuvenated, Integer midSpringRejuvenated,
			Integer controlSpringRejuvenated, String remarkSpringRejuvenated, Integer prePersonBenefitte, Integer midPersonBenefitte, Integer controlPersonBenefitte, String remarkPersonBenefitte,
			Integer preCommunityBasedShg, Integer midCommunityBasedShg, Integer controlCommunityBasedShg, String remarkCommunityBasedShg, Integer preCommunityBasedFpo, Integer midCommunityBasedFpo,
			Integer controlCommunityBasedFpo, String remarkCommunityBasedFpo, Integer preCommunityBasedUg, Integer midCommunityBasedUg, Integer controlCommunityBasedUg, String remarkCommunityBasedUg,
			Integer preMemberBasedShg, Integer midMemberBasedShg, Integer controlMemberBasedShg, String remarkMemberBasedShg, Integer preMemberBasedFpo, Integer midMemberBasedFpo,
			Integer controlMemberBasedFpo, String remarkMemberBasedFpo, Integer preMemberBasedUg, Integer midMemberBasedUg, Integer controlMemberBasedUg, String remarkMemberBasedUg,
			BigDecimal preTrunoverFpo, BigDecimal midTrunoverFpo, BigDecimal controlTrunoverFpo, String remarkTrunoverFpo, BigDecimal preIncomeFpo, BigDecimal midIncomeFpo, BigDecimal controlIncomeFpo,
			String remarkIncomeFpo, BigDecimal preAnnualIncomeShg, BigDecimal midAnnualIncomeShg, BigDecimal controlAnnualIncomeShg, String remarkAnnualIncomeShg, Date createdOn, String createdBy,
			Date updatedOn, String requestIp) {
		
		this.productionDetailId = productionDetailId;
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
		this.preMilchCattle = preMilchCattle;
		this.midMilchCattle = midMilchCattle;
		this.controlMilchCattle = controlMilchCattle;
		this.remarkMilchCattle = remarkMilchCattle;
		this.preFodderProduction = preFodderProduction;
		this.midFodderProduction = midFodderProduction;
		this.controlFodderProduction = controlFodderProduction;
		this.remarkFodderProduction = remarkFodderProduction;
		this.preRuralUrban = preRuralUrban;
		this.midRuralUrban = midRuralUrban;
		this.controlRuralUrban = controlRuralUrban;
		this.remarkRuralUrban = remarkRuralUrban;
		this.preSpringRejuvenated = preSpringRejuvenated;
		this.midSpringRejuvenated = midSpringRejuvenated;
		this.controlSpringRejuvenated = controlSpringRejuvenated;
		this.remarkSpringRejuvenated = remarkSpringRejuvenated;
		this.prePersonBenefitte = prePersonBenefitte;
		this.midPersonBenefitte = midPersonBenefitte;
		this.controlPersonBenefitte = controlPersonBenefitte;
		this.remarkPersonBenefitte = remarkPersonBenefitte;
		this.preCommunityBasedShg = preCommunityBasedShg;
		this.midCommunityBasedShg = midCommunityBasedShg;
		this.controlCommunityBasedShg = controlCommunityBasedShg;
		this.remarkCommunityBasedShg = remarkCommunityBasedShg;
		this.preCommunityBasedFpo = preCommunityBasedFpo;
		this.midCommunityBasedFpo = midCommunityBasedFpo;
		this.controlCommunityBasedFpo = controlCommunityBasedFpo;
		this.remarkCommunityBasedFpo = remarkCommunityBasedFpo;
		this.preCommunityBasedUg = preCommunityBasedUg;
		this.midCommunityBasedUg = midCommunityBasedUg;
		this.controlCommunityBasedUg = controlCommunityBasedUg;
		this.remarkCommunityBasedUg = remarkCommunityBasedUg;
		this.preMemberBasedShg = preMemberBasedShg;
		this.midMemberBasedShg = midMemberBasedShg;
		this.controlMemberBasedShg = controlMemberBasedShg;
		this.remarkMemberBasedShg = remarkMemberBasedShg;
		this.preMemberBasedFpo = preMemberBasedFpo;
		this.midMemberBasedFpo = midMemberBasedFpo;
		this.controlMemberBasedFpo = controlMemberBasedFpo;
		this.remarkMemberBasedFpo = remarkMemberBasedFpo;
		this.preMemberBasedUg = preMemberBasedUg;
		this.midMemberBasedUg = midMemberBasedUg;
		this.controlMemberBasedUg = controlMemberBasedUg;
		this.remarkMemberBasedUg = remarkMemberBasedUg;
		this.preTrunoverFpo = preTrunoverFpo;
		this.midTrunoverFpo = midTrunoverFpo;
		this.controlTrunoverFpo = controlTrunoverFpo;
		this.remarkTrunoverFpo = remarkTrunoverFpo;
		this.preIncomeFpo = preIncomeFpo;
		this.midIncomeFpo = midIncomeFpo;
		this.controlIncomeFpo = controlIncomeFpo;
		this.remarkIncomeFpo = remarkIncomeFpo;
		this.preAnnualIncomeShg = preAnnualIncomeShg;
		this.midAnnualIncomeShg = midAnnualIncomeShg;
		this.controlAnnualIncomeShg = controlAnnualIncomeShg;
		this.remarkAnnualIncomeShg = remarkAnnualIncomeShg;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.requestIp = requestIp;
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


	@Column(name="pre_milch_cattle", precision=20)
	public BigDecimal getPreMilchCattle() {
		return preMilchCattle;
	}

	public void setPreMilchCattle(BigDecimal preMilchCattle) {
		this.preMilchCattle = preMilchCattle;
	}


	@Column(name="mid_milch_cattle", precision=20)
	public BigDecimal getMidMilchCattle() {
		return midMilchCattle;
	}

	public void setMidMilchCattle(BigDecimal midMilchCattle) {
		this.midMilchCattle = midMilchCattle;
	}


	@Column(name="control_milch_cattle", precision=20)
	public BigDecimal getControlMilchCattle() {
		return controlMilchCattle;
	}

	public void setControlMilchCattle(BigDecimal controlMilchCattle) {
		this.controlMilchCattle = controlMilchCattle;
	}


	@Column(name="remark_milch_cattle", length=200)
	public String getRemarkMilchCattle() {
		return remarkMilchCattle;
	}

	public void setRemarkMilchCattle(String remarkMilchCattle) {
		this.remarkMilchCattle = remarkMilchCattle;
	}


	@Column(name="pre_fodder_production", precision=20)
	public BigDecimal getPreFodderProduction() {
		return preFodderProduction;
	}

	public void setPreFodderProduction(BigDecimal preFodderProduction) {
		this.preFodderProduction = preFodderProduction;
	}


	@Column(name="mid_fodder_production", precision=20)
	public BigDecimal getMidFodderProduction() {
		return midFodderProduction;
	}

	public void setMidFodderProduction(BigDecimal midFodderProduction) {
		this.midFodderProduction = midFodderProduction;
	}


	@Column(name="control_fodder_production", precision=20)
	public BigDecimal getControlFodderProduction() {
		return controlFodderProduction;
	}

	public void setControlFodderProduction(BigDecimal controlFodderProduction) {
		this.controlFodderProduction = controlFodderProduction;
	}


	@Column(name="remark_fodder_production", length=200)
	public String getRemarkFodderProduction() {
		return remarkFodderProduction;
	}

	public void setRemarkFodderProduction(String remarkFodderProduction) {
		this.remarkFodderProduction = remarkFodderProduction;
	}


	@Column(name="pre_rural_urban", precision=20)
	public Integer getPreRuralUrban() {
		return preRuralUrban;
	}

	public void setPreRuralUrban(Integer preRuralUrban) {
		this.preRuralUrban = preRuralUrban;
	}

	@Column(name="mid_rural_urban", precision=20)
	public Integer getMidRuralUrban() {
		return midRuralUrban;
	}

	public void setMidRuralUrban(Integer midRuralUrban) {
		this.midRuralUrban = midRuralUrban;
	}


	@Column(name="control_rural_urban", precision=20)
	public Integer getControlRuralUrban() {
		return controlRuralUrban;
	}

	public void setControlRuralUrban(Integer controlRuralUrban) {
		this.controlRuralUrban = controlRuralUrban;
	}


	@Column(name="remark_rural_urban", length=200)
	public String getRemarkRuralUrban() {
		return remarkRuralUrban;
	}

	public void setRemarkRuralUrban(String remarkRuralUrban) {
		this.remarkRuralUrban = remarkRuralUrban;
	}


	@Column(name="pre_spring_rejuvenated", precision=20)
	public Integer getPreSpringRejuvenated() {
		return preSpringRejuvenated;
	}

	public void setPreSpringRejuvenated(Integer preSpringRejuvenated) {
		this.preSpringRejuvenated = preSpringRejuvenated;
	}
	
	@Column(name="mid_spring_rejuvenated", precision=20)
	public Integer getMidSpringRejuvenated() {
		return midSpringRejuvenated;
	}

	public void setMidSpringRejuvenated(Integer midSpringRejuvenated) {
		this.midSpringRejuvenated = midSpringRejuvenated;
	}


	@Column(name="control_spring_rejuvenated", precision=20)
	public Integer getControlSpringRejuvenated() {
		return controlSpringRejuvenated;
	}

	public void setControlSpringRejuvenated(Integer controlSpringRejuvenated) {
		this.controlSpringRejuvenated = controlSpringRejuvenated;
	}


	@Column(name="remark_spring_rejuvenated", length=200)
	public String getRemarkSpringRejuvenated() {
		return remarkSpringRejuvenated;
	}

	public void setRemarkSpringRejuvenated(String remarkSpringRejuvenated) {
		this.remarkSpringRejuvenated = remarkSpringRejuvenated;
	}


	@Column(name="pre_person_benefitte", precision=20)
	public Integer getPrePersonBenefitte() {
		return prePersonBenefitte;
	}

	public void setPrePersonBenefitte(Integer prePersonBenefitte) {
		this.prePersonBenefitte = prePersonBenefitte;
	}
	
	@Column(name="mid_person_benefitte", precision=20)
	public Integer getMidPersonBenefitte() {
		return midPersonBenefitte;
	}

	public void setMidPersonBenefitte(Integer midPersonBenefitte) {
		this.midPersonBenefitte = midPersonBenefitte;
	}


	@Column(name="control_person_benefitte", precision=20)
	public Integer getControlPersonBenefitte() {
		return controlPersonBenefitte;
	}

	public void setControlPersonBenefitte(Integer controlPersonBenefitte) {
		this.controlPersonBenefitte = controlPersonBenefitte;
	}


	@Column(name="remark_person_benefitte", length=200)
	public String getRemarkPersonBenefitte() {
		return remarkPersonBenefitte;
	}

	public void setRemarkPersonBenefitte(String remarkPersonBenefitte) {
		this.remarkPersonBenefitte = remarkPersonBenefitte;
	}


	@Column(name="pre_community_based_shg", precision=20)
	public Integer getPreCommunityBasedShg() {
		return preCommunityBasedShg;
	}

	public void setPreCommunityBasedShg(Integer preCommunityBasedShg) {
		this.preCommunityBasedShg = preCommunityBasedShg;
	}
	
	@Column(name="mid_community_based_shg", precision=20)
	public Integer getMidCommunityBasedShg() {
		return midCommunityBasedShg;
	}

	public void setMidCommunityBasedShg(Integer midCommunityBasedShg) {
		this.midCommunityBasedShg = midCommunityBasedShg;
	}


	@Column(name="control_community_based_shg", precision=20)
	public Integer getControlCommunityBasedShg() {
		return controlCommunityBasedShg;
	}

	public void setControlCommunityBasedShg(Integer controlCommunityBasedShg) {
		this.controlCommunityBasedShg = controlCommunityBasedShg;
	}


	@Column(name="remark_community_based_shg", length=200)
	public String getRemarkCommunityBasedShg() {
		return remarkCommunityBasedShg;
	}

	public void setRemarkCommunityBasedShg(String remarkCommunityBasedShg) {
		this.remarkCommunityBasedShg = remarkCommunityBasedShg;
	}


	@Column(name="pre_community_based_fpo", precision=20)
	public Integer getPreCommunityBasedFpo() {
		return preCommunityBasedFpo;
	}

	public void setPreCommunityBasedFpo(Integer preCommunityBasedFpo) {
		this.preCommunityBasedFpo = preCommunityBasedFpo;
	}
	
	@Column(name="mid_community_based_fpo", precision=20)
	public Integer getMidCommunityBasedFpo() {
		return midCommunityBasedFpo;
	}

	public void setMidCommunityBasedFpo(Integer midCommunityBasedFpo) {
		this.midCommunityBasedFpo = midCommunityBasedFpo;
	}


	@Column(name="control_community_based_fpo", precision=20)
	public Integer getControlCommunityBasedFpo() {
		return controlCommunityBasedFpo;
	}

	public void setControlCommunityBasedFpo(Integer controlCommunityBasedFpo) {
		this.controlCommunityBasedFpo = controlCommunityBasedFpo;
	}


	@Column(name="remark_community_based_fpo", length=200)
	public String getRemarkCommunityBasedFpo() {
		return remarkCommunityBasedFpo;
	}

	public void setRemarkCommunityBasedFpo(String remarkCommunityBasedFpo) {
		this.remarkCommunityBasedFpo = remarkCommunityBasedFpo;
	}


	@Column(name="pre_community_based_ug", precision=20)
	public Integer getPreCommunityBasedUg() {
		return preCommunityBasedUg;
	}

	public void setPreCommunityBasedUg(Integer preCommunityBasedUg) {
		this.preCommunityBasedUg = preCommunityBasedUg;
	}
	
	@Column(name="mid_community_based_ug", precision=20)
	public Integer getMidCommunityBasedUg() {
		return midCommunityBasedUg;
	}

	public void setMidCommunityBasedUg(Integer midCommunityBasedUg) {
		this.midCommunityBasedUg = midCommunityBasedUg;
	}


	@Column(name="control_community_based_ug", precision=20)
	public Integer getControlCommunityBasedUg() {
		return controlCommunityBasedUg;
	}

	public void setControlCommunityBasedUg(Integer controlCommunityBasedUg) {
		this.controlCommunityBasedUg = controlCommunityBasedUg;
	}


	@Column(name="remark_community_based_ug", length=200)
	public String getRemarkCommunityBasedUg() {
		return remarkCommunityBasedUg;
	}

	public void setRemarkCommunityBasedUg(String remarkCommunityBasedUg) {
		this.remarkCommunityBasedUg = remarkCommunityBasedUg;
	}


	@Column(name="pre_member_based_shg", precision=20)
	public Integer getPreMemberBasedShg() {
		return preMemberBasedShg;
	}

	public void setPreMemberBasedShg(Integer preMemberBasedShg) {
		this.preMemberBasedShg = preMemberBasedShg;
	}
	
	@Column(name="mid_member_based_shg", precision=20)
	public Integer getMidMemberBasedShg() {
		return midMemberBasedShg;
	}

	public void setMidMemberBasedShg(Integer midMemberBasedShg) {
		this.midMemberBasedShg = midMemberBasedShg;
	}


	@Column(name="control_member_based_shg", precision=20)
	public Integer getControlMemberBasedShg() {
		return controlMemberBasedShg;
	}

	public void setControlMemberBasedShg(Integer controlMemberBasedShg) {
		this.controlMemberBasedShg = controlMemberBasedShg;
	}


	@Column(name="remark_member_based_shg", length=200)
	public String getRemarkMemberBasedShg() {
		return remarkMemberBasedShg;
	}

	public void setRemarkMemberBasedShg(String remarkMemberBasedShg) {
		this.remarkMemberBasedShg = remarkMemberBasedShg;
	}


	@Column(name="pre_member_based_fpo", precision=20)
	public Integer getPreMemberBasedFpo() {
		return preMemberBasedFpo;
	}

	public void setPreMemberBasedFpo(Integer preMemberBasedFpo) {
		this.preMemberBasedFpo = preMemberBasedFpo;
	}
	
	@Column(name="mid_member_based_fpo", precision=20)
	public Integer getMidMemberBasedFpo() {
		return midMemberBasedFpo;
	}

	public void setMidMemberBasedFpo(Integer midMemberBasedFpo) {
		this.midMemberBasedFpo = midMemberBasedFpo;
	}


	@Column(name="control_member_based_fpo", precision=20)
	public Integer getControlMemberBasedFpo() {
		return controlMemberBasedFpo;
	}

	public void setControlMemberBasedFpo(Integer controlMemberBasedFpo) {
		this.controlMemberBasedFpo = controlMemberBasedFpo;
	}


	@Column(name="remark_member_based_fpo", length=200)
	public String getRemarkMemberBasedFpo() {
		return remarkMemberBasedFpo;
	}

	public void setRemarkMemberBasedFpo(String remarkMemberBasedFpo) {
		this.remarkMemberBasedFpo = remarkMemberBasedFpo;
	}


	@Column(name="pre_member_based_ug", precision=20)
	public Integer getPreMemberBasedUg() {
		return preMemberBasedUg;
	}

	public void setPreMemberBasedUg(Integer preMemberBasedUg) {
		this.preMemberBasedUg = preMemberBasedUg;
	}
	
	@Column(name="mid_member_based_ug", precision=20)
	public Integer getMidMemberBasedUg() {
		return midMemberBasedUg;
	}

	public void setMidMemberBasedUg(Integer midMemberBasedUg) {
		this.midMemberBasedUg = midMemberBasedUg;
	}


	@Column(name="control_member_based_ug", precision=20)
	public Integer getControlMemberBasedUg() {
		return controlMemberBasedUg;
	}

	public void setControlMemberBasedUg(Integer controlMemberBasedUg) {
		this.controlMemberBasedUg = controlMemberBasedUg;
	}


	@Column(name="remark_member_based_ug", length=200)
	public String getRemarkMemberBasedUg() {
		return remarkMemberBasedUg;
	}

	public void setRemarkMemberBasedUg(String remarkMemberBasedUg) {
		this.remarkMemberBasedUg = remarkMemberBasedUg;
	}


	@Column(name="pre_trunover_fpo", precision=20)
	public BigDecimal getPreTrunoverFpo() {
		return preTrunoverFpo;
	}

	public void setPreTrunoverFpo(BigDecimal preTrunoverFpo) {
		this.preTrunoverFpo = preTrunoverFpo;
	}


	@Column(name="mid_trunover_fpo", precision=20)
	public BigDecimal getMidTrunoverFpo() {
		return midTrunoverFpo;
	}

	public void setMidTrunoverFpo(BigDecimal midTrunoverFpo) {
		this.midTrunoverFpo = midTrunoverFpo;
	}


	@Column(name="control_trunover_fpo", precision=20)
	public BigDecimal getControlTrunoverFpo() {
		return controlTrunoverFpo;
	}

	public void setControlTrunoverFpo(BigDecimal controlTrunoverFpo) {
		this.controlTrunoverFpo = controlTrunoverFpo;
	}


	@Column(name="remark_trunover_fpo", length=200)
	public String getRemarkTrunoverFpo() {
		return remarkTrunoverFpo;
	}

	public void setRemarkTrunoverFpo(String remarkTrunoverFpo) {
		this.remarkTrunoverFpo = remarkTrunoverFpo;
	}


	@Column(name="pre_income_fpo", precision=20)
	public BigDecimal getPreIncomeFpo() {
		return preIncomeFpo;
	}

	public void setPreIncomeFpo(BigDecimal preIncomeFpo) {
		this.preIncomeFpo = preIncomeFpo;
	}


	@Column(name="mid_income_fpo", precision=20)
	public BigDecimal getMidIncomeFpo() {
		return midIncomeFpo;
	}

	public void setMidIncomeFpo(BigDecimal midIncomeFpo) {
		this.midIncomeFpo = midIncomeFpo;
	}


	@Column(name="control_income_fpo", precision=20)
	public BigDecimal getControlIncomeFpo() {
		return controlIncomeFpo;
	}

	public void setControlIncomeFpo(BigDecimal controlIncomeFpo) {
		this.controlIncomeFpo = controlIncomeFpo;
	}


	@Column(name="remark_income_fpo", length=200)
	public String getRemarkIncomeFpo() {
		return remarkIncomeFpo;
	}

	public void setRemarkIncomeFpo(String remarkIncomeFpo) {
		this.remarkIncomeFpo = remarkIncomeFpo;
	}


	@Column(name="pre_annual_income_shg", precision=20)
	public BigDecimal getPreAnnualIncomeShg() {
		return preAnnualIncomeShg;
	}

	public void setPreAnnualIncomeShg(BigDecimal preAnnualIncomeShg) {
		this.preAnnualIncomeShg = preAnnualIncomeShg;
	}


	@Column(name="mid_annual_income_shg", precision=20)
	public BigDecimal getMidAnnualIncomeShg() {
		return midAnnualIncomeShg;
	}

	public void setMidAnnualIncomeShg(BigDecimal midAnnualIncomeShg) {
		this.midAnnualIncomeShg = midAnnualIncomeShg;
	}


	@Column(name="control_annual_income_shg", precision=20)
	public BigDecimal getControlAnnualIncomeShg() {
		return controlAnnualIncomeShg;
	}

	public void setControlAnnualIncomeShg(BigDecimal controlAnnualIncomeShg) {
		this.controlAnnualIncomeShg = controlAnnualIncomeShg;
	}


	@Column(name="remark_annual_income_shg", length=200)
	public String getRemarkAnnualIncomeShg() {
		return remarkAnnualIncomeShg;
	}

	public void setRemarkAnnualIncomeShg(String remarkAnnualIncomeShg) {
		this.remarkAnnualIncomeShg = remarkAnnualIncomeShg;
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
