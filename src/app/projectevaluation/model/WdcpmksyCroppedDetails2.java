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
@Table(name="wdcpmksy_cropped_details_2" ,schema="public")
public class WdcpmksyCroppedDetails2 implements java.io.Serializable{
	
	
	private int croppedDetails2Id;								//  cropped_details_2_id
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;					// project_profile_id
	private BigDecimal projectDiversifiedChange;				// project_diversified_change
	private BigDecimal controlDiversifiedChange;				// control_diversified_change
	private String remarkDiversifiedChange;						// remark_diversified_change
	private Integer projectWhsConstructedRejuvenated;			// project_whs_constructed_rejuvenated
	private Integer controlWhsConstructedRejuvenated;			// control_whs_constructed_rejuvenated
    private String remarkWhsConstructedRejuvenated;				// remark_whs_constructed_rejuvenated
    private BigDecimal projectSoilMoisture;						// project_soil_moisture
    private BigDecimal controlSoilMoisture;						// control_soil_moisture
    private String remarkSoilMoisture;							// remark_soil_moisture
    private BigDecimal projectDegradedRainfed;					// project_degraded_rainfed
    private BigDecimal controlDegradedRainfed;					// control_degraded_rainfed
    private String remarkDegradedRainfed;						// remark_degraded_rainfed
    private BigDecimal projectNillSingle;						// project_nill_single
    private BigDecimal controlNillSingle;						// control_nill_single
    private String remarkNillSingle;							// remark_nill_single
    private BigDecimal projectSingleDoublemore;					// project_single_doublemore
    private BigDecimal controlSingleDoublemore;					// control_single_doublemore
    private String remarkSingleDoublemore;						// remark_single_doublemore
	private Date createdOn ;                    				//created_on ;
	private String createdBy ;         							//created_by ;
	private Date updatedOn ;                 					//updated_on ;
	private String requestIp; 									//request_ip
	
	public WdcpmksyCroppedDetails2() {
 	}

	
    public WdcpmksyCroppedDetails2(int croppedDetails2Id) {
        this.croppedDetails2Id = croppedDetails2Id;
    }
	
    public WdcpmksyCroppedDetails2(int croppedDetails2Id, WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation, BigDecimal projectDiversifiedChange, 
    		BigDecimal controlDiversifiedChange, String remarkDiversifiedChange, Integer projectWhsConstructedRejuvenated, Integer controlWhsConstructedRejuvenated, 
    		String remarkWhsConstructedRejuvenated, BigDecimal projectSoilMoisture, BigDecimal controlSoilMoisture, String remarkSoilMoisture, 
    		BigDecimal projectDegradedRainfed, BigDecimal controlDegradedRainfed, String remarkDegradedRainfed, BigDecimal projectNillSingle, 
    		BigDecimal controlNillSingle, String remarkNillSingle, BigDecimal projectSingleDoublemore, BigDecimal controlSingleDoublemore, String remarkSingleDoublemore,  
    		Date createdOn, String createdBy, Date updatedOn, String requestIp) {
    	
        this.croppedDetails2Id = croppedDetails2Id;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
        this.projectDiversifiedChange = projectDiversifiedChange;
    	this.controlDiversifiedChange = controlDiversifiedChange;
    	this.remarkDiversifiedChange = remarkDiversifiedChange;
    	this.projectWhsConstructedRejuvenated = projectWhsConstructedRejuvenated;
    	this.controlWhsConstructedRejuvenated = controlWhsConstructedRejuvenated;
    	this.remarkWhsConstructedRejuvenated = remarkWhsConstructedRejuvenated;
    	this.projectSoilMoisture = projectSoilMoisture;
    	this.controlSoilMoisture = controlSoilMoisture;
    	this.remarkSoilMoisture = remarkSoilMoisture;
    	this.projectDegradedRainfed = projectDegradedRainfed;
    	this.controlDegradedRainfed = controlDegradedRainfed;
    	this.remarkDegradedRainfed = remarkDegradedRainfed;
    	this.projectNillSingle = projectNillSingle;
    	this.controlNillSingle = controlNillSingle;
    	this.remarkNillSingle = remarkNillSingle;
    	this.projectSingleDoublemore = projectSingleDoublemore;
    	this.controlSingleDoublemore = controlSingleDoublemore;
    	this.remarkSingleDoublemore = remarkSingleDoublemore;
        this.createdOn=createdOn;
    	this.createdBy=createdBy;
    	this.updatedOn=updatedOn;
    	this.requestIp=requestIp;
        
    }

    
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="cropped_details_2_id", unique=true, nullable=false)
	public int getCroppedDetails2Id() {
		return croppedDetails2Id;
	}

	public void setCroppedDetails2Id(int croppedDetails2Id) {
		this.croppedDetails2Id = croppedDetails2Id;
	}

	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_profile_id")
	public WdcpmksyProjectProfileEvaluation getWdcpmksyProjectProfileEvaluation() {
		return wdcpmksyProjectProfileEvaluation;
	}

	public void setWdcpmksyProjectProfileEvaluation(WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation) {
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
	}


	@Column(name="project_diversified_change", precision=20)
	public BigDecimal getProjectDiversifiedChange() {
		return projectDiversifiedChange;
	}

	public void setProjectDiversifiedChange(BigDecimal projectDiversifiedChange) {
		this.projectDiversifiedChange = projectDiversifiedChange;
	}


	@Column(name="control_diversified_change", precision=20)
	public BigDecimal getControlDiversifiedChange() {
		return controlDiversifiedChange;
	}

	public void setControlDiversifiedChange(BigDecimal controlDiversifiedChange) {
		this.controlDiversifiedChange = controlDiversifiedChange;
	}


	@Column(name="remark_diversified_change", length=200)
	public String getRemarkDiversifiedChange() {
		return remarkDiversifiedChange;
	}

	public void setRemarkDiversifiedChange(String remarkDiversifiedChange) {
		this.remarkDiversifiedChange = remarkDiversifiedChange;
	}


	@Column(name="project_whs_constructed_rejuvenated")
	public Integer getProjectWhsConstructedRejuvenated() {
		return projectWhsConstructedRejuvenated;
	}

	public void setProjectWhsConstructedRejuvenated(Integer projectWhsConstructedRejuvenated) {
		this.projectWhsConstructedRejuvenated = projectWhsConstructedRejuvenated;
	}


	@Column(name="control_whs_constructed_rejuvenated")
	public Integer getControlWhsConstructedRejuvenated() {
		return controlWhsConstructedRejuvenated;
	}

	public void setControlWhsConstructedRejuvenated(Integer controlWhsConstructedRejuvenated) {
		this.controlWhsConstructedRejuvenated = controlWhsConstructedRejuvenated;
	}

	
	@Column(name="remark_whs_constructed_rejuvenated", length=200)
	public String getRemarkWhsConstructedRejuvenated() {
		return remarkWhsConstructedRejuvenated;
	}

	public void setRemarkWhsConstructedRejuvenated(String remarkWhsConstructedRejuvenated) {
		this.remarkWhsConstructedRejuvenated = remarkWhsConstructedRejuvenated;
	}


	@Column(name="project_soil_moisture", precision=20)
	public BigDecimal getProjectSoilMoisture() {
		return projectSoilMoisture;
	}

	public void setProjectSoilMoisture(BigDecimal projectSoilMoisture) {
		this.projectSoilMoisture = projectSoilMoisture;
	}


	@Column(name="control_soil_moisture", precision=20)
	public BigDecimal getControlSoilMoisture() {
		return controlSoilMoisture;
	}

	public void setControlSoilMoisture(BigDecimal controlSoilMoisture) {
		this.controlSoilMoisture = controlSoilMoisture;
	}


	@Column(name="remark_soil_moisture", length=200)
	public String getRemarkSoilMoisture() {
		return remarkSoilMoisture;
	}

	public void setRemarkSoilMoisture(String remarkSoilMoisture) {
		this.remarkSoilMoisture = remarkSoilMoisture;
	}


	@Column(name="project_degraded_rainfed", precision=20)
	public BigDecimal getProjectDegradedRainfed() {
		return projectDegradedRainfed;
	}

	public void setProjectDegradedRainfed(BigDecimal projectDegradedRainfed) {
		this.projectDegradedRainfed = projectDegradedRainfed;
	}


	@Column(name="control_degraded_rainfed", precision=20)
	public BigDecimal getControlDegradedRainfed() {
		return controlDegradedRainfed;
	}
	
	public void setControlDegradedRainfed(BigDecimal controlDegradedRainfed) {
		this.controlDegradedRainfed = controlDegradedRainfed;
	}


	@Column(name="remark_degraded_rainfed", length=200)
	public String getRemarkDegradedRainfed() {
		return remarkDegradedRainfed;
	}

	public void setRemarkDegradedRainfed(String remarkDegradedRainfed) {
		this.remarkDegradedRainfed = remarkDegradedRainfed;
	}


	@Column(name="project_nill_single", precision=20)
	public BigDecimal getProjectNillSingle() {
		return projectNillSingle;
	}

	public void setProjectNillSingle(BigDecimal projectNillSingle) {
		this.projectNillSingle = projectNillSingle;
	}


	@Column(name="control_nill_single", precision=20)
	public BigDecimal getControlNillSingle() {
		return controlNillSingle;
	}

	public void setControlNillSingle(BigDecimal controlNillSingle) {
		this.controlNillSingle = controlNillSingle;
	}


	@Column(name="remark_nill_single", length=200)
	public String getRemarkNillSingle() {
		return remarkNillSingle;
	}

	public void setRemarkNillSingle(String remarkNillSingle) {
		this.remarkNillSingle = remarkNillSingle;
	}


	@Column(name="project_single_doublemore", precision=20)
	public BigDecimal getProjectSingleDoublemore() {
		return projectSingleDoublemore;
	}

	public void setProjectSingleDoublemore(BigDecimal projectSingleDoublemore) {
		this.projectSingleDoublemore = projectSingleDoublemore;
	}


	@Column(name="control_single_doublemore", precision=20)
	public BigDecimal getControlSingleDoublemore() {
		return controlSingleDoublemore;
	}

	public void setControlSingleDoublemore(BigDecimal controlSingleDoublemore) {
		this.controlSingleDoublemore = controlSingleDoublemore;
	}


	@Column(name="remark_single_doublemore", length=200)
	public String getRemarkSingleDoublemore() {
		return remarkSingleDoublemore;
	}

	public void setRemarkSingleDoublemore(String remarkSingleDoublemore) {
		this.remarkSingleDoublemore = remarkSingleDoublemore;
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
