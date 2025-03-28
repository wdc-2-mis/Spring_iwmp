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
@Table(name="wdcpmksy_indicator_evaluation" ,schema="public")
public class IndicatorEvaluation implements java.io.Serializable{
	
	
	
	private int  indicatorEvaluationId;						//indicator_evaluation_id 
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;				//project_profile_id integer,
	private String adminMechanism;					//admin_mechanism 
	private String adminMechanismRemark;			//admin_mechanism_remark 
	private Character dprSlna;						//dpr_slna 
	private String dprSlnaRemark;					// dpr_slna_remark 
	private Character allManpower;					//  all_manpower
	private String 	allManpowerRemark;							// all_manpower_remark 
	private Integer wcdc ;
	private String 	wcdcRemark; 					//  wcdc_remark
	private Integer pia ;
	private String piaRemark ;						//   pia_remark
	private Integer wc ;
	private String wcRemark ;						//   wc_remark;
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ;
	private String requestIp; 
	
	
	 	public IndicatorEvaluation() {
	 	}

		
	    public IndicatorEvaluation(int indicatorEvaluationId) {
	        this.indicatorEvaluationId = indicatorEvaluationId;
	    }
	
	
	    public IndicatorEvaluation(int indicatorEvaluationId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, String adminMechanism, String adminMechanismRemark, Character dprSlna, String dprSlnaRemark, Character allManpower, String allManpowerRemark, Integer wcdc, String wcdcRemark, Integer pia, String piaRemark, Integer wc, String wcRemark, Date CreatedOn, String CreatedBy, Date UpdatedOn, String RequestIp ) {
	        this.indicatorEvaluationId = indicatorEvaluationId;
		    this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
		    this.adminMechanism = adminMechanism;
		    this.adminMechanismRemark=adminMechanismRemark;
		    this.dprSlna = dprSlna;
		    this.dprSlnaRemark = dprSlnaRemark;
		    this.allManpower = allManpower;
		    this.allManpowerRemark = allManpowerRemark;
		    this.wcdc = wcdc;
		    this.wcdcRemark = wcdcRemark;
		    this.pia = pia;
		    this.piaRemark = piaRemark;
		    this.wc = wc;
		    this.wcRemark=wcRemark;
		    this.createdOn=CreatedOn;
		    this.createdBy=CreatedBy;
		    this.updatedOn=UpdatedOn;
		    this.requestIp=RequestIp;
	    }

	    @Id 
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    @Column(name="indicator_evaluation_id", unique=true, nullable=false)
		public int getIndicatorEvaluationId() {
			return indicatorEvaluationId;
		}


		public void setIndicatorEvaluationId(int indicatorEvaluationId) {
			this.indicatorEvaluationId = indicatorEvaluationId;
		}

		@ManyToOne(fetch=FetchType.LAZY)
	    @JoinColumn(name="project_profile_id")
		public WdcpmksyProjectProfileEvaluation getWdcpmksyProjectProfileEvaluation() {
			return wdcpmksyProjectProfileEvaluation;
		}


		public void setWdcpmksyProjectProfileEvaluation(WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation) {
			this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
		}

		@Column(name="admin_mechanism", length=200)
		public String getAdminMechanism() {
			return adminMechanism;
		}


		public void setAdminMechanism(String adminMechanism) {
			this.adminMechanism = adminMechanism;
		}

		@Column(name="admin_mechanism_remark", length=200)
		public String getAdminMechanismRemark() {
			return adminMechanismRemark;
		}


		public void setAdminMechanismRemark(String adminMechanismRemark) {
			this.adminMechanismRemark = adminMechanismRemark;
		}

		@Column(name="dpr_slna")
		public Character getDprSlna() {
			return dprSlna;
		}


		public void setDprSlna(Character dprSlna) {
			this.dprSlna = dprSlna;
		}

		@Column(name="dpr_slna_remark", length=200)
		public String getDprSlnaRemark() {
			return dprSlnaRemark;
		}


		public void setDprSlnaRemark(String dprSlnaRemark) {
			this.dprSlnaRemark = dprSlnaRemark;
		}

		@Column(name="all_manpower")
		public Character getAllManpower() {
			return allManpower;
		}


		public void setAllManpower(Character allManpower) {
			this.allManpower = allManpower;
		}

		@Column(name="all_manpower_remark", length=200)
		public String getAllManpowerRemark() {
			return allManpowerRemark;
		}


		public void setAllManpowerRemark(String allManpowerRemark) {
			this.allManpowerRemark = allManpowerRemark;
		}

		@Column(name="wcdc")
		public Integer getWcdc() {
			return wcdc;
		}


		public void setWcdc(Integer wcdc) {
			this.wcdc = wcdc;
		}

		@Column(name="wcdc_remark", length=200)
		public String getWcdcRemark() {
			return wcdcRemark;
		}


		public void setWcdcRemark(String wcdcRemark) {
			this.wcdcRemark = wcdcRemark;
		}

		@Column(name="pia")
		public Integer getPia() {
			return pia;
		}


		public void setPia(Integer pia) {
			this.pia = pia;
		}

		@Column(name="pia_remark", length=200)
		public String getPiaRemark() {
			return piaRemark;
		}


		public void setPiaRemark(String piaRemark) {
			this.piaRemark = piaRemark;
		}

		@Column(name="wc")
		public Integer getWc() {
			return wc;
		}


		public void setWc(Integer wc) {
			this.wc = wc;
		}

		@Column(name="wc_remark", length=200)
		public String getWcRemark() {
			return wcRemark;
		}


		public void setWcRemark(String wcRemark) {
			this.wcRemark = wcRemark;
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
