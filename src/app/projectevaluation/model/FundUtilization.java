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
	private BigDecimal centralShare;				//   central_share
	private String centralShareRemark; 						//central_share_remark
    private BigDecimal stateShare; 					// state_share
    private String stateShareRemark;						//  state_share_remark
    private BigDecimal totalFund;				// total_fund
    private String totalFundRemark ;						//  total_fund_remark
    private BigDecimal totalFundPlanned;				//   total_fund_planned
    private String totalFundPlannedRemark; 					//   total_fund_planned_remark
    private BigDecimal totalExpenditure;				///   total_expenditure
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
	
	
    public FundUtilization(int fundUtilizationId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, BigDecimal centralShare, String centralShareRemark,
    		BigDecimal stateShare, String stateShareRemark, BigDecimal totalFund, String totalFundRemark, BigDecimal totalFundPlanned, String totalFundPlannedRemark, 
    		BigDecimal totalExpenditure, String totalExpenditureRemark, Date CreatedOn, String CreatedBy, Date UpdatedOn, String RequestIp) {
    		
    	this.fundUtilizationId = fundUtilizationId;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
        this.centralShare=centralShare;
        this.centralShareRemark=centralShareRemark;
        this.stateShare=stateShare;
        this.stateShareRemark=stateShareRemark;
        this.totalFund=totalFund;
        this.totalFundRemark=totalFundRemark;
        this.totalFundPlanned=totalFundPlanned;
        this.totalFundPlannedRemark=totalFundPlannedRemark;
        this.totalExpenditure=totalExpenditure;
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

	@Column(name="central_share", precision=20)
	public BigDecimal getCentralShare() {
		return centralShare;
	}

	public void setCentralShare(BigDecimal centralShare) {
		this.centralShare = centralShare;
	}

	@Column(name="central_share_remark", length=200)
	public String getCentralShareRemark() {
		return centralShareRemark;
	}

	public void setCentralShareRemark(String centralShareRemark) {
		this.centralShareRemark = centralShareRemark;
	}

	@Column(name="state_share", precision=20)
	public BigDecimal getStateShare() {
		return stateShare;
	}

	public void setStateShare(BigDecimal stateShare) {
		this.stateShare = stateShare;
	}

	@Column(name="state_share_remark", length=200)
	public String getStateShareRemark() {
		return stateShareRemark;
	}

	public void setStateShareRemark(String stateShareRemark) {
		this.stateShareRemark = stateShareRemark;
	}

	@Column(name="total_fund", precision=20)
	public BigDecimal getTotalFund() {
		return totalFund;
	}

	public void setTotalFund(BigDecimal totalFund) {
		this.totalFund = totalFund;
	}

	@Column(name="total_fund_remark", length=200)
	public String getTotalFundRemark() {
		return totalFundRemark;
	}

	public void setTotalFundRemark(String totalFundRemark) {
		this.totalFundRemark = totalFundRemark;
	}

	@Column(name="total_fund_planned", precision=20)
	public BigDecimal getTotalFundPlanned() {
		return totalFundPlanned;
	}

	public void setTotalFundPlanned(BigDecimal totalFundPlanned) {
		this.totalFundPlanned = totalFundPlanned;
	}

	@Column(name="total_fund_planned_remark", length=200)
	public String getTotalFundPlannedRemark() {
		return totalFundPlannedRemark;
	}

	public void setTotalFundPlannedRemark(String totalFundPlannedRemark) {
		this.totalFundPlannedRemark = totalFundPlannedRemark;
	}

	@Column(name="total_expenditure", precision=20)
	public BigDecimal getTotalExpenditure() {
		return totalExpenditure;
	}

	public void setTotalExpenditure(BigDecimal totalExpenditure) {
		this.totalExpenditure = totalExpenditure;
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
