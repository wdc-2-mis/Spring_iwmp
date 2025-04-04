package app.model.master;
// Generated 15 Sep, 2021 12:11:49 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.BaseLineSurveyActivityDetails;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.project.WdcpmksyOutcomeTargetDetails;

/**
 * WdcpmksyMOutcome generated by hbm2java
 */
@Entity
@Table(name="wdcpmksy_m_outcome"
    ,schema="public"
)
public class WdcpmksyMOutcome  implements java.io.Serializable {


     private Integer outcomeId;
     private IwmpMFinYear iwmpMFinYear;
     private IwmpMMonth iwmpMMonth;
     private String outcomeDesc;
     private Character detailexist;
     private Integer seqNo;
     private String createdBy;
     private Date createdOn;
     private String updatedBy;
     private Date updatedOn;
     private String requestIp;
     private Set<WdcpmksyMOutcomeDetail> wdcpmksyMOutcomeDetails = new HashSet<WdcpmksyMOutcomeDetail>(0);
     private Set<WdcpmksyOutcomeTargetDetails> wdcpmksyOutcomeTargetDetailses = new HashSet<WdcpmksyOutcomeTargetDetails>(0);
     private Set<BaseLineSurveyActivityDetails> baseLineSurveyActivityDetailses = new HashSet<BaseLineSurveyActivityDetails>(0);

    public WdcpmksyMOutcome() {
    }
	
    public WdcpmksyMOutcome(Integer outcomeId) {
        this.outcomeId = outcomeId;
    }
    public WdcpmksyMOutcome(Integer outcomeId, IwmpMFinYear iwmpMFinYear, IwmpMMonth iwmpMMonth, String outcomeDesc, Character detailexist, int seqNo, String createdBy, Date createdOn, String updatedBy, Date updatedOn, String requestIp,Set<WdcpmksyMOutcomeDetail> wdcpmksyMOutcomeDetails) {
       this.outcomeId = outcomeId;
       this.iwmpMFinYear = iwmpMFinYear;
       this.iwmpMMonth = iwmpMMonth;
       this.outcomeDesc = outcomeDesc;
       this.detailexist = detailexist;
       this.seqNo = seqNo;
       this.createdBy = createdBy;
       this.createdOn = createdOn;
       this.updatedBy = updatedBy;
       this.updatedOn = updatedOn;
       this.requestIp = requestIp;
       this.wdcpmksyMOutcomeDetails = wdcpmksyMOutcomeDetails;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="outcome_id", unique=true, nullable=false)
    public Integer getOutcomeId() {
        return this.outcomeId;
    }
    
    public void setOutcomeId(Integer outcomeId) {
        this.outcomeId = outcomeId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fin_yr_cd")
    public IwmpMFinYear getIwmpMFinYear() {
        return this.iwmpMFinYear;
    }
    
    public void setIwmpMFinYear(IwmpMFinYear iwmpMFinYear) {
        this.iwmpMFinYear = iwmpMFinYear;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="month_id")
    public IwmpMMonth getIwmpMMonth() {
        return this.iwmpMMonth;
    }
    
    public void setIwmpMMonth(IwmpMMonth iwmpMMonth) {
        this.iwmpMMonth = iwmpMMonth;
    }

    
    @Column(name="outcome_desc", length=200)
    public String getOutcomeDesc() {
        return this.outcomeDesc;
    }
    
    public void setOutcomeDesc(String outcomeDesc) {
        this.outcomeDesc = outcomeDesc;
    }
    
    @Column(name="detail_exist", length=1)
    public Character getDetailexist() {
        return this.detailexist;
    }
    
    public void setDetailexist(Character detailexist) {
        this.detailexist = detailexist;
    }
    
    @Column(name="seq_no")
    public Integer getSeqNo() {
        return this.seqNo;
    }
    
    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }
    
    @Column(name="created_by", length=20)
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="created_on", length=13)
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    @Column(name="updated_by", length=20)
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="updated_on", length=13)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }
    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
    
    @Column(name="request_ip", length=20)
    public String getRequestIp() {
        return this.requestIp;
    }
    
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
    @OrderBy("seqNo ASC")
@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyMOutcome")
    public Set<WdcpmksyMOutcomeDetail> getWdcpmksyMOutcomeDetails() {
        return this.wdcpmksyMOutcomeDetails;
    }
    
    public void setWdcpmksyMOutcomeDetails(Set<WdcpmksyMOutcomeDetail> wdcpmksyMOutcomeDetails) {
        this.wdcpmksyMOutcomeDetails = wdcpmksyMOutcomeDetails;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyMOutcome")
    public Set<WdcpmksyOutcomeTargetDetails> getWdcpmksyOutcomeTargetDetailses() {
        return this.wdcpmksyOutcomeTargetDetailses;
    }
    
    public void setWdcpmksyOutcomeTargetDetailses(Set<WdcpmksyOutcomeTargetDetails> wdcpmksyOutcomeTargetDetailses) {
        this.wdcpmksyOutcomeTargetDetailses = wdcpmksyOutcomeTargetDetailses;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyMOutcome")
    public Set<BaseLineSurveyActivityDetails> getBaseLineSurveyActivityDetailses() {
        return this.baseLineSurveyActivityDetailses;
    }
    
    public void setBaseLineSurveyActivityDetailses(Set<BaseLineSurveyActivityDetails> baseLineSurveyActivityDetailses) {
        this.baseLineSurveyActivityDetailses = baseLineSurveyActivityDetailses;
    }


}


