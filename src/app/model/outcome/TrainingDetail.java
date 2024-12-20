package app.model.outcome;
// Generated 9 Dec, 2021 12:49:07 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TrainingDetail generated by hbm2java
 */
@Entity
@Table(name="training_detail"
    ,schema="public"
)
public class TrainingDetail  implements java.io.Serializable {


     private int trainingDetailId;
     private TrainingMain trainingMain;
     private Integer personNo;
     private Integer trainingDays;
     private Date createdOn;
     private Date updatedOn;
     private String requestIp;
     private String createdBy;
     private Date startDate;
     private Date endDate;
     private TrainingSubjectDetail trainingSubjectDetail;

    public TrainingDetail() {
    }

	
    public TrainingDetail(int trainingDetailId) {
        this.trainingDetailId = trainingDetailId;
    }
    public TrainingDetail(int trainingDetailId, TrainingMain trainingMain, Integer personNo, Integer trainingDays, Date createdOn, Date updatedOn, String requestIp, String createdBy, Date startDate, Date endDate, TrainingSubjectDetail trainingSubjectDetail) {
       this.trainingDetailId = trainingDetailId;
       this.trainingMain = trainingMain;
       this.personNo = personNo;
       this.trainingDays = trainingDays;
       this.createdOn = createdOn;
       this.updatedOn = updatedOn;
       this.requestIp = requestIp;
       this.createdBy = createdBy;
       this.startDate = startDate;
       this.endDate = endDate;		   
       this.trainingSubjectDetail = trainingSubjectDetail;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="training_detail_id", unique=true, nullable=false)
    public int getTrainingDetailId() {
        return this.trainingDetailId;
    }
    
    public void setTrainingDetailId(int trainingDetailId) {
        this.trainingDetailId = trainingDetailId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="training_id")
    public TrainingMain getTrainingMain() {
        return this.trainingMain;
    }
    
    public void setTrainingMain(TrainingMain trainingMain) {
        this.trainingMain = trainingMain;
    }

    
    @Column(name="person_no")
    public Integer getPersonNo() {
        return this.personNo;
    }
    
    public void setPersonNo(Integer personNo) {
        this.personNo = personNo;
    }

    
    @Column(name="training_days")
    public Integer getTrainingDays() {
        return this.trainingDays;
    }
    
    public void setTrainingDays(Integer trainingDays) {
        this.trainingDays = trainingDays;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="created_on", length=13)
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
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

    
    @Column(name="created_by", length=20)
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @OneToOne(fetch=FetchType.LAZY, mappedBy="trainingDetail")
    public TrainingSubjectDetail getTrainingSubjectDetail() {
        return this.trainingSubjectDetail;
    }
    
    public void setTrainingSubjectDetail(TrainingSubjectDetail trainingSubjectDetail) {
        this.trainingSubjectDetail = trainingSubjectDetail;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="start_date", length=13)
	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="end_date", length=13)
	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}




}


