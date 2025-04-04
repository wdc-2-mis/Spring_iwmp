package app.model;
// Generated Nov 3, 2022, 12:37:41 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.IwmpState;

/**
 * PfmsTreasuryreceiptDetaildata generated by hbm2java
 */
@Entity
@Table(name="pfms_treasuryreceipt_detaildata"
    ,schema="public"
)
public class PfmsTreasuryreceiptDetaildata  implements java.io.Serializable {


     private int treasuryreceiptdetaildataId;
     private IwmpState iwmpState;
     private String financialYear;
     private String transactionId;
     private Date transactionDate;
     private BigDecimal treasuryReceipt;
     private BigDecimal centralShare;
     private BigDecimal stateShare;
     private String apiResponse;
     private Date createdOn;

    public PfmsTreasuryreceiptDetaildata() {
    }

	
    public PfmsTreasuryreceiptDetaildata(int treasuryreceiptdetaildataId) {
        this.treasuryreceiptdetaildataId = treasuryreceiptdetaildataId;
    }
    public PfmsTreasuryreceiptDetaildata(int treasuryreceiptdetaildataId, IwmpState iwmpState, String financialYear, String transactionId, Date transactionDate, BigDecimal treasuryReceipt, BigDecimal centralShare, BigDecimal stateShare, String apiResponse, Date createdOn) {
       this.treasuryreceiptdetaildataId = treasuryreceiptdetaildataId;
       this.iwmpState = iwmpState;
       this.financialYear = financialYear;
       this.transactionId = transactionId;
       this.transactionDate = transactionDate;
       this.treasuryReceipt = treasuryReceipt;
       this.centralShare = centralShare;
       this.stateShare = stateShare;
       this.apiResponse = apiResponse;
       this.createdOn = createdOn;
    }
   
     @Id 

    
    @Column(name="treasuryreceiptdetaildata_id", unique=true, nullable=false)
    public int getTreasuryreceiptdetaildataId() {
        return this.treasuryreceiptdetaildataId;
    }
    
    public void setTreasuryreceiptdetaildataId(int treasuryreceiptdetaildataId) {
        this.treasuryreceiptdetaildataId = treasuryreceiptdetaildataId;
    }

@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="state_lgd_code")
    public IwmpState getIwmpState() {
        return this.iwmpState;
    }
    
    public void setIwmpState(IwmpState iwmpState) {
        this.iwmpState = iwmpState;
    }

    
    @Column(name="financial_year", length=10)
    public String getFinancialYear() {
        return this.financialYear;
    }
    
    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    
    @Column(name="transaction_id", length=50)
    public String getTransactionId() {
        return this.transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="transaction_date", length=29)
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    
    @Column(name="treasury_receipt", precision=20, scale=4)
    public BigDecimal getTreasuryReceipt() {
        return this.treasuryReceipt;
    }
    
    public void setTreasuryReceipt(BigDecimal treasuryReceipt) {
        this.treasuryReceipt = treasuryReceipt;
    }

    
    @Column(name="central_share", precision=20, scale=4)
    public BigDecimal getCentralShare() {
        return this.centralShare;
    }
    
    public void setCentralShare(BigDecimal centralShare) {
        this.centralShare = centralShare;
    }

    
    @Column(name="state_share", precision=20, scale=4)
    public BigDecimal getStateShare() {
        return this.stateShare;
    }
    
    public void setStateShare(BigDecimal stateShare) {
        this.stateShare = stateShare;
    }

    
    @Column(name="api_response", length=20)
    public String getApiResponse() {
        return this.apiResponse;
    }
    
    public void setApiResponse(String apiResponse) {
        this.apiResponse = apiResponse;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_on", length=29)
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }




}


