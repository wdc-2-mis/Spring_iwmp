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
@Table(name="wdcpmksy_execution_planned_work" ,schema="public")
public class WdcpmksyExecutionPlannedWork implements java.io.Serializable{
	
	private int plannedWorkId;						//planned_work_id ;
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;   //   project_profile_id
	private int createdWork;						//	created_work
	private String createdWorkRemark; 			//	created_work_remark 
	private int completedWork; 								//	completed_work
	private String completedWorkRemark;					//		completed_work_remark
	private int ongoingWork;							//		ongoing_work
	private String ongoingWorkRemark;						//	ongoing_work_remark
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ; 
	private String requestIp; 
	
	public WdcpmksyExecutionPlannedWork() {
    }

	
    public WdcpmksyExecutionPlannedWork(int plannedWorkId) {
        this.plannedWorkId = plannedWorkId;
    }
	
    public WdcpmksyExecutionPlannedWork(int plannedWorkId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, int createdWork, String createdWorkRemark, int completedWork, String completedWorkRemark, int ongoingWork, 
    		String ongoingWorkRemark, Date createdOn, String createdBy, Date updatedOn, String requestIp ) {
        this.plannedWorkId = plannedWorkId;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
        this.createdWork=createdWork;
        this.createdWorkRemark=createdWorkRemark;
        this.completedWork=completedWork;
        this.completedWorkRemark=completedWorkRemark;
        this.ongoingWork=ongoingWork;
        this.ongoingWorkRemark=ongoingWorkRemark;
        this.createdOn=createdOn;
        this.createdBy=createdBy;
        this.updatedOn=updatedOn;
        this.requestIp=requestIp;
    }


    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="planned_work_id", unique=true, nullable=false)
	public int getPlannedWorkId() {
		return plannedWorkId;
	}


	public void setPlannedWorkId(int plannedWorkId) {
		this.plannedWorkId = plannedWorkId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_profile_id")
	public WdcpmksyProjectProfileEvaluation getWdcpmksyProjectProfileEvaluation() {
		return wdcpmksyProjectProfileEvaluation;
	}


	public void setWdcpmksyProjectProfileEvaluation(WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation) {
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
	}

	@Column(name="created_work")
	public int getCreatedWork() {
		return createdWork;
	}


	public void setCreatedWork(int createdWork) {
		this.createdWork = createdWork;
	}

	@Column(name="created_work_remark", length=200)
	public String getCreatedWorkRemark() {
		return createdWorkRemark;
	}


	public void setCreatedWorkRemark(String createdWorkRemark) {
		this.createdWorkRemark = createdWorkRemark;
	}

	@Column(name="completed_work")
	public int getCompletedWork() {
		return completedWork;
	}


	public void setCompletedWork(int completedWork) {
		this.completedWork = completedWork;
	}

	@Column(name="completed_work_remark", length=200)
	public String getCompletedWorkRemark() {
		return completedWorkRemark;
	}


	public void setCompletedWorkRemark(String completedWorkRemark) {
		this.completedWorkRemark = completedWorkRemark;
	}

	@Column(name="ongoing_work")
	public int getOngoingWork() {
		return ongoingWork;
	}


	public void setOngoingWork(int ongoingWork) {
		this.ongoingWork = ongoingWork;
	}

	@Column(name="ongoing_work_remark", length=200)
	public String getOngoingWorkRemark() {
		return ongoingWorkRemark;
	}


	public void setOngoingWorkRemark(String ongoingWorkRemark) {
		this.ongoingWorkRemark = ongoingWorkRemark;
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
