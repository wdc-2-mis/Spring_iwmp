package app.projectevaluation.model;

import java.math.BigDecimal;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpMProject;


@Entity
@Table(name="wdcpmksy_project_profile_evaluation" ,schema="public")
public class WdcpmksyProjectProfileEvaluation implements java.io.Serializable{
	
	
	private int  projectProfileId;                 //    project_profile_id
	private IwmpMProject iwmpMProject;                             //proj_id 
	private IwmpMFinYear iwmpMFinYear;           //fin_yr_cd integer,
    private IwmpMMonth iwmpMMonth;					//month_id integer,			
    private MEvaluationIndicator mEvaluationIndicator;	//  evaluation_id 				
    private BigDecimal projectCost;                 //    project_cost numeric(20,4),
    private BigDecimal centralShare	;						//	central_share numeric(20,4),
    private BigDecimal stateShare;					//state_share numeric(20,4),
    private BigDecimal projectArea;								// project_area numeric(20,4),
    private int villageCovered;								//	village_covered integer,
    private int watershedCommittee;					//    watershedCommittee
    private int memberWatershedCommittee;	      			//	member_watershed_committee integer,
    private int household;								//		household integer,
    private Character status;
    private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ; 
	private String requestIp; 
	private Set<IndicatorEvaluation> indicatorEvaluation = new HashSet<IndicatorEvaluation>(0);
	private Set<FundUtilization> fundUtilization = new HashSet<FundUtilization>(0);
	private Set<WdcpmksyMandaysDetails> wdcpmksyMandaysDetails = new HashSet<WdcpmksyMandaysDetails>(0);
	private Set<WdcpmksyCroppedDetails1> wdcpmksyCroppedDetails1 = new HashSet<WdcpmksyCroppedDetails1>(0);
	private Set<WdcpmksyCroppedDetails2> wdcpmksyCroppedDetails2 = new HashSet<WdcpmksyCroppedDetails2>(0);
	private Set<WdcpmksyExecutionPlannedWork> wdcpmksyExecutionPlannedWork = new HashSet<WdcpmksyExecutionPlannedWork>(0);
	private Set<WdcpmksyQualityShapeFiles> wdcpmksyQualityShapeFiles = new HashSet<WdcpmksyQualityShapeFiles>(0);
	private Set<WdcpmksyStatusGeotaggWork> wdcpmksyStatusGeotaggWork = new HashSet<WdcpmksyStatusGeotaggWork>(0);
	private Set<WdcpmksyProductionDetails> wdcpmksyProductionDetails = new HashSet<WdcpmksyProductionDetails>(0);
	private Set<WdcpmksyEcologicalPerspective> wdcpmksyEcologicalPerspective = new HashSet<WdcpmksyEcologicalPerspective>(0);
	private Set<WdcpmksyEquityAspect> wdcpmksyEquityAspect = new HashSet<WdcpmksyEquityAspect>(0);
    
	 public WdcpmksyProjectProfileEvaluation() {
	    }

		
	    public WdcpmksyProjectProfileEvaluation(int projectProfileId) {
	        this.projectProfileId = projectProfileId;
	    }
	    public WdcpmksyProjectProfileEvaluation(int projectProfileId, IwmpMProject iwmpMProject, IwmpMFinYear iwmpMFinYear, IwmpMMonth iwmpMMonth, MEvaluationIndicator mEvaluationIndicator, BigDecimal projectCost, BigDecimal centralShare, BigDecimal stateShare, BigDecimal projectArea, int villageCovered, int watershedCommittee, int memberWatershedCommittee, int household, Character status, Date CreatedOn, String CreatedBy, Date UpdatedOn, String RequestIp) {
	       this.projectProfileId = projectProfileId;
	       this.iwmpMProject = iwmpMProject;
	       this.iwmpMFinYear = iwmpMFinYear;
	       this.iwmpMMonth=iwmpMMonth;
	       this.mEvaluationIndicator = mEvaluationIndicator;
	       this.projectCost = projectCost;
	       this.centralShare = centralShare;
	       this.stateShare = stateShare;
	       this.projectArea = projectArea;
	       this.villageCovered = villageCovered;
	       this.watershedCommittee = watershedCommittee;
	       this.memberWatershedCommittee = memberWatershedCommittee;
	       this.household = household;
	       this.status = status;
	       this.createdOn=CreatedOn;
	       this.createdBy=CreatedBy;
	       this.updatedOn=UpdatedOn;
	       this.requestIp=RequestIp;
	    }


	    @Id 
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    @Column(name="project_profile_id", unique=true, nullable=false)
		public int getProjectProfileId() {
			return projectProfileId;
		}


		public void setProjectProfileId(int projectProfileId) {
			this.projectProfileId = projectProfileId;
		}

		@ManyToOne(fetch=FetchType.EAGER)
	    @JoinColumn(name="proj_id")
		public IwmpMProject getIwmpMProject() {
			return iwmpMProject;
		}


		public void setIwmpMProject(IwmpMProject iwmpMProject) {
			this.iwmpMProject = iwmpMProject;
		}

		@ManyToOne(fetch=FetchType.EAGER)
	    @JoinColumn(name="fin_yr_cd")
		public IwmpMFinYear getIwmpMFinYear() {
			return iwmpMFinYear;
		}


		public void setIwmpMFinYear(IwmpMFinYear iwmpMFinYear) {
			this.iwmpMFinYear = iwmpMFinYear;
		}

		@ManyToOne(fetch=FetchType.EAGER)
	    @JoinColumn(name="month_id", referencedColumnName = "month_id")
		public IwmpMMonth getIwmpMMonth() {
			return iwmpMMonth;
		}


		public void setIwmpMMonth(IwmpMMonth iwmpMMonth) {
			this.iwmpMMonth = iwmpMMonth;
		}

		@ManyToOne(fetch=FetchType.EAGER)
		@JoinColumn(name="evaluation_id")
		public MEvaluationIndicator getmEvaluationIndicator() {
			return mEvaluationIndicator;
		}


		public void setmEvaluationIndicator(MEvaluationIndicator mEvaluationIndicator) {
			this.mEvaluationIndicator = mEvaluationIndicator;
		}

		@Column(name="project_cost", precision=20)
		public BigDecimal getProjectCost() {
			return projectCost;
		}


		public void setProjectCost(BigDecimal projectCost) {
			this.projectCost = projectCost;
		}

		@Column(name="central_share", precision=20)
		public BigDecimal getCentralShare() {
			return centralShare;
		}


		public void setCentralShare(BigDecimal centralShare) {
			this.centralShare = centralShare;
		}

		@Column(name="state_share", precision=20)
		public BigDecimal getStateShare() {
			return stateShare;
		}


		public void setStateShare(BigDecimal stateShare) {
			this.stateShare = stateShare;
		}

		@Column(name="project_area", precision=20)
		public BigDecimal getProjectArea() {
			return projectArea;
		}


		public void setProjectArea(BigDecimal projectArea) {
			this.projectArea = projectArea;
		}

		@Column(name="village_covered")
		public int getVillageCovered() {
			return villageCovered;
		}


		public void setVillageCovered(int villageCovered) {
			this.villageCovered = villageCovered;
		}

		@Column(name="watershed_committee")
		public int getWatershedCommittee() {
			return watershedCommittee;
		}


		public void setWatershedCommittee(int watershedCommittee) {
			this.watershedCommittee = watershedCommittee;
		}

		@Column(name="member_watershed_committee")
		public int getMemberWatershedCommittee() {
			return memberWatershedCommittee;
		}


		public void setMemberWatershedCommittee(int memberWatershedCommittee) {
			this.memberWatershedCommittee = memberWatershedCommittee;
		}

		@Column(name="household")
		public int getHousehold() {
			return household;
		}


		public void setHousehold(int household) {
			this.household = household;
		}

		@Column(name="status", length=1)
		public Character getStatus() {
			return status;
		}


		public void setStatus(Character status) {
			this.status = status;
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

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<IndicatorEvaluation> getIndicatorEvaluation() {
			return indicatorEvaluation;
		}


		public void setIndicatorEvaluation(Set<IndicatorEvaluation> indicatorEvaluation) {
			this.indicatorEvaluation = indicatorEvaluation;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<FundUtilization> getFundUtilization() {
			return fundUtilization;
		}


		public void setFundUtilization(Set<FundUtilization> fundUtilization) {
			this.fundUtilization = fundUtilization;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<WdcpmksyMandaysDetails> getWdcpmksyMandaysDetails() {
			return wdcpmksyMandaysDetails;
		}


		public void setWdcpmksyMandaysDetails(Set<WdcpmksyMandaysDetails> wdcpmksyMandaysDetails) {
			this.wdcpmksyMandaysDetails = wdcpmksyMandaysDetails;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<WdcpmksyCroppedDetails1> getWdcpmksyCroppedDetails1() {
			return wdcpmksyCroppedDetails1;
		}


		public void setWdcpmksyCroppedDetails1(Set<WdcpmksyCroppedDetails1> wdcpmksyCroppedDetails1) {
			this.wdcpmksyCroppedDetails1 = wdcpmksyCroppedDetails1;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<WdcpmksyExecutionPlannedWork> getWdcpmksyExecutionPlannedWork() {
			return wdcpmksyExecutionPlannedWork;
		}


		public void setWdcpmksyExecutionPlannedWork(Set<WdcpmksyExecutionPlannedWork> wdcpmksyExecutionPlannedWork) {
			this.wdcpmksyExecutionPlannedWork = wdcpmksyExecutionPlannedWork;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<WdcpmksyQualityShapeFiles> getWdcpmksyQualityShapeFiles() {
			return wdcpmksyQualityShapeFiles;
		}


		public void setWdcpmksyQualityShapeFiles(Set<WdcpmksyQualityShapeFiles> wdcpmksyQualityShapeFiles) {
			this.wdcpmksyQualityShapeFiles = wdcpmksyQualityShapeFiles;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<WdcpmksyStatusGeotaggWork> getWdcpmksyStatusGeotaggWork() {
			return wdcpmksyStatusGeotaggWork;
		}


		public void setWdcpmksyStatusGeotaggWork(Set<WdcpmksyStatusGeotaggWork> wdcpmksyStatusGeotaggWork) {
			this.wdcpmksyStatusGeotaggWork = wdcpmksyStatusGeotaggWork;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<WdcpmksyCroppedDetails2> getWdcpmksyCroppedDetails2() {
			return wdcpmksyCroppedDetails2;
		}


		public void setWdcpmksyCroppedDetails2(Set<WdcpmksyCroppedDetails2> wdcpmksyCroppedDetails2) {
			this.wdcpmksyCroppedDetails2 = wdcpmksyCroppedDetails2;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<WdcpmksyProductionDetails> getWdcpmksyProductionDetails() {
			return wdcpmksyProductionDetails;
		}


		public void setWdcpmksyProductionDetails(Set<WdcpmksyProductionDetails> wdcpmksyProductionDetails) {
			this.wdcpmksyProductionDetails = wdcpmksyProductionDetails;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<WdcpmksyEcologicalPerspective> getWdcpmksyEcologicalPerspective() {
			return wdcpmksyEcologicalPerspective;
		}


		public void setWdcpmksyEcologicalPerspective(Set<WdcpmksyEcologicalPerspective> wdcpmksyEcologicalPerspective) {
			this.wdcpmksyEcologicalPerspective = wdcpmksyEcologicalPerspective;
		}

		@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyProjectProfileEvaluation", cascade=CascadeType.ALL)
		public Set<WdcpmksyEquityAspect> getWdcpmksyEquityAspect() {
			return wdcpmksyEquityAspect;
		}


		public void setWdcpmksyEquityAspect(Set<WdcpmksyEquityAspect> wdcpmksyEquityAspect) {
			this.wdcpmksyEquityAspect = wdcpmksyEquityAspect;
		}
	    
	    
	    
	    
	
	

}
