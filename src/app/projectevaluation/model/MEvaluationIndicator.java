package app.projectevaluation.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.project.WdcpmksyProjectPhysicalAchievement;


@Entity
@Table(name="m_evaluation_indicator" ,schema="public")
public class MEvaluationIndicator implements java.io.Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int evaluationId;      //evaluation_id;
	private String evaluationDesc;       //evaluation_desc;
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ;
	private String requestIp;             //request_ip;
	private Set<WdcpmksyProjectProfileEvaluation> wdcpmksyProjectProfileEvaluation = new HashSet<WdcpmksyProjectProfileEvaluation>(0);
	
	
	 	public MEvaluationIndicator() {
	    }

		
	    public MEvaluationIndicator(int evaluationId) {
	        this.evaluationId = evaluationId;
	    }
	    public MEvaluationIndicator(int EvaluationId, String EvaluationDesc, Date CreatedOn, String CreatedBy, Date UpdatedOn, String RequestIp ) {
	    	
	    	this.evaluationId=EvaluationId;
	    	this.evaluationDesc=EvaluationDesc;
	    	this.createdOn=CreatedOn;
	    	this.createdBy=CreatedBy;
	    	this.updatedOn=UpdatedOn;
	    	this.requestIp=RequestIp;
	    	
	       
	    }

	    @Id 
	    @Column(name="evaluation_id", unique=true, nullable=false)
		public int getEvaluationId() {
			return evaluationId;
		}


		public void setEvaluationId(int evaluationId) {
			this.evaluationId = evaluationId;
		}

		@Column(name="evaluation_desc", length=500)
		public String getEvaluationDesc() {
			return evaluationDesc;
		}


		public void setEvaluationDesc(String evaluationDesc) {
			this.evaluationDesc = evaluationDesc;
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

		@OneToMany(fetch=FetchType.LAZY, mappedBy="mEvaluationIndicator", cascade=CascadeType.ALL)
		public Set<WdcpmksyProjectProfileEvaluation> getWdcpmksyProjectProfileEvaluation() {
			return wdcpmksyProjectProfileEvaluation;
		}


		public void setWdcpmksyProjectProfileEvaluation(
				Set<WdcpmksyProjectProfileEvaluation> wdcpmksyProjectProfileEvaluation) {
			this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
		}
	    
	    
	    
	    
	
	
}
