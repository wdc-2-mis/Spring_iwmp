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
@Table(name="wdcpmksy_fund_utilization" ,schema="public")
public class FundUtilization implements java.io.Serializable{
	
	
	private int fundUtilizationId;						//   fund_utilization_id ;
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;   ///   project_profile_id
	private BigDecimal fundUtilizationPrestatus;			//  fund_utilization_prestatus 
	private BigDecimal fundUtilizationMidstatus;			//   fund_utilization_midstatus 
	private String fundUtilizationRemark;					//fund_utilization_remark;
	private BigDecimal centralSharePrestatus;				//   central_share_prestatus
	private BigDecimal centralShareMidstatus;				//  central_share_midstatus
	private String centralShareRemark; 						//central_share_remark
    private BigDecimal stateSharePrestatus; 					// state_share_prestatus
    private BigDecimal stateShareMidstatus; 				//  state_share_midstatus
    private String stateShareRemark;						//  state_share_remark
    private BigDecimal totalFundPrestatus ;				// total_fund_prestatus
    private BigDecimal totalFundMidstatus; 				//   total_fund_midstatus
    private String totalFundRemark ;						//  total_fund_remark
    private BigDecimal totalFundPlannedPrestatus;				//   total_fund_planned_prestatus
    private BigDecimal totalFundPlannedMidstatus;				//   total_fund_planned_midstatus
    private String totalFundPlannedRemark; 					//   total_fund_planned_remark
    private BigDecimal totalExpenditurePrestatus ;				///   total_expenditure_prestatus
    private BigDecimal totalExpenditureMidstatus; 					//  total_expenditure_midstatus
    private String totalExpenditureRemark	;						//  total_expenditure_remark
    
    private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ;
	private String requestIp; 
	
	public FundUtilization() {
 	}

	
    public FundUtilization(int fundUtilizationId) {
        this.fundUtilizationId = fundUtilizationId;
    }
	
	
    public FundUtilization(int fundUtilizationId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, BigDecimal fundUtilizationPrestatus, BigDecimal fundUtilizationMidstatus, String fundUtilizationRemark, BigDecimal centralSharePrestatus, BigDecimal centralShareMidstatus, String centralShareRemark,
    		BigDecimal stateSharePrestatus, BigDecimal stateShareMidstatus, String stateShareRemark, BigDecimal totalFundPrestatus, BigDecimal totalFundMidstatus, String totalFundRemark, BigDecimal totalFundPlannedPrestatus, BigDecimal totalFundPlannedMidstatus, String totalFundPlannedRemark, BigDecimal totalExpenditurePrestatus,
    		BigDecimal totalExpenditureMidstatus, String totalExpenditureRemark, Date CreatedOn, String CreatedBy, Date UpdatedOn, String RequestIp) {
    		
    	this.fundUtilizationId = fundUtilizationId;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
        this.fundUtilizationPrestatus=fundUtilizationPrestatus;
        this.fundUtilizationMidstatus=fundUtilizationMidstatus;
        this.fundUtilizationRemark=fundUtilizationRemark;
        this.centralSharePrestatus=centralSharePrestatus;
        this.centralShareMidstatus=centralShareMidstatus;
        this.centralShareRemark=centralShareRemark;
        this.stateSharePrestatus=stateSharePrestatus;
        this.stateShareMidstatus=stateShareMidstatus;
        this.stateShareRemark=stateShareRemark;
        this.totalFundPrestatus=totalFundPrestatus;
        this.totalFundMidstatus=totalFundMidstatus;
        this.totalFundRemark=totalFundRemark;
        this.totalFundPlannedPrestatus=totalFundPlannedPrestatus;
        this.totalFundPlannedMidstatus=totalFundPlannedMidstatus;
        this.totalFundPlannedRemark=totalFundPlannedRemark;
        this.totalExpenditurePrestatus=totalExpenditurePrestatus;
        this.totalExpenditureMidstatus=totalExpenditureMidstatus;
        this.totalExpenditureRemark=totalExpenditureRemark;
        this.createdOn=CreatedOn;
	    this.createdBy=CreatedBy;
	    this.updatedOn=UpdatedOn;
	    this.requestIp=RequestIp;
    }

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="fund_utilization_id", unique=true, nullable=false)
	public int getFundUtilizationId() {
		return fundUtilizationId;
	}


	public void setFundUtilizationId(int fundUtilizationId) {
		this.fundUtilizationId = fundUtilizationId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_profile_id")
	public WdcpmksyProjectProfileEvaluation getWdcpmksyProjectProfileEvaluation() {
		return wdcpmksyProjectProfileEvaluation;
	}


	public void setWdcpmksyProjectProfileEvaluation(WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation) {
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
	}

	@Column(name="fund_utilization_prestatus", precision=20)
	public BigDecimal getFundUtilizationPrestatus() {
		return fundUtilizationPrestatus;
	}


	public void setFundUtilizationPrestatus(BigDecimal fundUtilizationPrestatus) {
		this.fundUtilizationPrestatus = fundUtilizationPrestatus;
	}

	@Column(name="fund_utilization_midstatus", precision=20)
	public BigDecimal getFundUtilizationMidstatus() {
		return fundUtilizationMidstatus;
	}


	public void setFundUtilizationMidstatus(BigDecimal fundUtilizationMidstatus) {
		this.fundUtilizationMidstatus = fundUtilizationMidstatus;
	}

	@Column(name="fund_utilization_remark", length=200)
	public String getFundUtilizationRemark() {
		return fundUtilizationRemark;
	}


	public void setFundUtilizationRemark(String fundUtilizationRemark) {
		this.fundUtilizationRemark = fundUtilizationRemark;
	}

	@Column(name="central_share_prestatus", precision=20)
	public BigDecimal getCentralSharePrestatus() {
		return centralSharePrestatus;
	}


	public void setCentralSharePrestatus(BigDecimal centralSharePrestatus) {
		this.centralSharePrestatus = centralSharePrestatus;
	}

	@Column(name="central_share_midstatus", precision=20)
	public BigDecimal getCentralShareMidstatus() {
		return centralShareMidstatus;
	}


	public void setCentralShareMidstatus(BigDecimal centralShareMidstatus) {
		this.centralShareMidstatus = centralShareMidstatus;
	}

	@Column(name="central_share_remark", length=200)
	public String getCentralShareRemark() {
		return centralShareRemark;
	}


	public void setCentralShareRemark(String centralShareRemark) {
		this.centralShareRemark = centralShareRemark;
	}

	@Column(name="state_share_prestatus", precision=20)
	public BigDecimal getStateSharePrestatus() {
		return stateSharePrestatus;
	}


	public void setStateSharePrestatus(BigDecimal stateSharePrestatus) {
		this.stateSharePrestatus = stateSharePrestatus;
	}

	@Column(name="state_share_midstatus", precision=20)
	public BigDecimal getStateShareMidstatus() {
		return stateShareMidstatus;
	}


	public void setStateShareMidstatus(BigDecimal stateShareMidstatus) {
		this.stateShareMidstatus = stateShareMidstatus;
	}

	@Column(name="state_share_remark", length=200)
	public String getStateShareRemark() {
		return stateShareRemark;
	}


	public void setStateShareRemark(String stateShareRemark) {
		this.stateShareRemark = stateShareRemark;
	}

	@Column(name="total_fund_prestatus", precision=20)
	public BigDecimal getTotalFundPrestatus() {
		return totalFundPrestatus;
	}


	public void setTotalFundPrestatus(BigDecimal totalFundPrestatus) {
		this.totalFundPrestatus = totalFundPrestatus;
	}

	@Column(name="total_fund_midstatus", precision=20)
	public BigDecimal getTotalFundMidstatus() {
		return totalFundMidstatus;
	}


	public void setTotalFundMidstatus(BigDecimal totalFundMidstatus) {
		this.totalFundMidstatus = totalFundMidstatus;
	}

	@Column(name="total_fund_remark", length=200)
	public String getTotalFundRemark() {
		return totalFundRemark;
	}


	public void setTotalFundRemark(String totalFundRemark) {
		this.totalFundRemark = totalFundRemark;
	}

	@Column(name="total_fund_planned_prestatus", precision=20)
	public BigDecimal getTotalFundPlannedPrestatus() {
		return totalFundPlannedPrestatus;
	}


	public void setTotalFundPlannedPrestatus(BigDecimal totalFundPlannedPrestatus) {
		this.totalFundPlannedPrestatus = totalFundPlannedPrestatus;
	}

	@Column(name="total_fund_planned_midstatus", precision=20)
	public BigDecimal getTotalFundPlannedMidstatus() {
		return totalFundPlannedMidstatus;
	}


	public void setTotalFundPlannedMidstatus(BigDecimal totalFundPlannedMidstatus) {
		this.totalFundPlannedMidstatus = totalFundPlannedMidstatus;
	}

	@Column(name="total_fund_planned_remark", length=200)
	public String getTotalFundPlannedRemark() {
		return totalFundPlannedRemark;
	}


	public void setTotalFundPlannedRemark(String totalFundPlannedRemark) {
		this.totalFundPlannedRemark = totalFundPlannedRemark;
	}

	@Column(name="total_expenditure_prestatus", precision=20)
	public BigDecimal getTotalExpenditurePrestatus() {
		return totalExpenditurePrestatus;
	}


	public void setTotalExpenditurePrestatus(BigDecimal totalExpenditurePrestatus) {
		this.totalExpenditurePrestatus = totalExpenditurePrestatus;
	}

	@Column(name="total_expenditure_midstatus", precision=20)
	public BigDecimal getTotalExpenditureMidstatus() {
		return totalExpenditureMidstatus;
	}


	public void setTotalExpenditureMidstatus(BigDecimal totalExpenditureMidstatus) {
		this.totalExpenditureMidstatus = totalExpenditureMidstatus;
	}

	@Column(name="total_expenditure_remark", length=200)
	public String getTotalExpenditureRemark() {
		return totalExpenditureRemark;
	}


	public void setTotalExpenditureRemark(String totalExpenditureRemark) {
		this.totalExpenditureRemark = totalExpenditureRemark;
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
