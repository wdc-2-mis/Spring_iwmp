package app.model.project;

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

import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpMProject;

@Entity
@Table(name="wdcpmksy_additional_brought_farmer_crop" ,schema="public")
public class WdcpmksyAdditionalBroughtFarmerCrop implements java.io.Serializable{
	
	private int additionalBroughtId;
    private IwmpMFinYear iwmpMFinYear;
    private IwmpMMonth iwmpMMonth;
    private IwmpMProject iwmpMProject;
    private BigDecimal diversified;
    private BigDecimal chnagesingle;
    private BigDecimal farmerIncome;
    private BigDecimal changeCorp;
    private Character status;
    private Date createdOn;
    private Date updatedOn;
    private String requestIp;
    private String createdBy;
    private String updatedBy;
    private String achievType;
   
    
   public WdcpmksyAdditionalBroughtFarmerCrop() {
   }
   public WdcpmksyAdditionalBroughtFarmerCrop(int additionalBroughtId) {
       this.additionalBroughtId = additionalBroughtId;
   }
   public WdcpmksyAdditionalBroughtFarmerCrop(int additionalBroughtId, IwmpMFinYear iwmpMFinYear, IwmpMMonth iwmpMMonth, IwmpMProject iwmpMProject, BigDecimal diversified,  BigDecimal chnagesingle, BigDecimal farmerIncome, BigDecimal changeCorp, Character status, Date createdOn, Date updatedOn, String requestIp, String createdBy, String updatedBy, String achievType) {
      this.additionalBroughtId = additionalBroughtId;
      this.iwmpMFinYear = iwmpMFinYear;
      this.iwmpMMonth = iwmpMMonth;
      this.iwmpMProject = iwmpMProject;
      this.diversified = diversified;
      this.chnagesingle = chnagesingle;
      this.farmerIncome = farmerIncome;
      this.changeCorp = changeCorp;
      this.status = status;
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.requestIp = requestIp;
      this.createdBy = createdBy;
      this.updatedBy = updatedBy;
      this.achievType=achievType;
   }

   @Id 
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="additional_brought_id", unique=true, nullable=false)
	public int getAdditionalBroughtId() {
		return additionalBroughtId;
	}
	public void setAdditionalBroughtId(int additionalBroughtId) {
		this.additionalBroughtId = additionalBroughtId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fin_yr_cd")
	public IwmpMFinYear getIwmpMFinYear() {
		return iwmpMFinYear;
	}
	public void setIwmpMFinYear(IwmpMFinYear iwmpMFinYear) {
		this.iwmpMFinYear = iwmpMFinYear;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="month_id")
	public IwmpMMonth getIwmpMMonth() {
		return iwmpMMonth;
	}
	public void setIwmpMMonth(IwmpMMonth iwmpMMonth) {
		this.iwmpMMonth = iwmpMMonth;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="proj_id")
	public IwmpMProject getIwmpMProject() {
		return iwmpMProject;
	}
	public void setIwmpMProject(IwmpMProject iwmpMProject) {
		this.iwmpMProject = iwmpMProject;
	}
	
	@Column(name="diversified", precision=20, scale=4)
	public BigDecimal getDiversified() {
		return diversified;
	}
	public void setDiversified(BigDecimal diversified) {
		this.diversified = diversified;
	}
	
	@Column(name="chnagesingle", precision=20, scale=4)
	public BigDecimal getChnagesingle() {
		return chnagesingle;
	}
	public void setChnagesingle(BigDecimal chnagesingle) {
		this.chnagesingle = chnagesingle;
	}
	
	@Column(name="farmer_income", precision=20, scale=4)	
	public BigDecimal getFarmerIncome() {
		return farmerIncome;
	}
	public void setFarmerIncome(BigDecimal farmerIncome) {
		this.farmerIncome = farmerIncome;
	}
	
	@Column(name="change_corp", precision=20, scale=4)
	public BigDecimal getChangeCorp() {
		return changeCorp;
	}
	public void setChangeCorp(BigDecimal changeCorp) {
		this.changeCorp = changeCorp;
	}

	@Column(name="status", length=1)
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.DATE)
    @Column(name="created_on", length=13)
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	@Temporal(TemporalType.DATE)
    @Column(name="updated_on", length=13)
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
	
	@Column(name="created_by", length=20)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="updated_by", length=20)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name="achiev_type", length=20)
	public String getAchievType() {
		return achievType;
	}
	public void setAchievType(String achievType) {
		this.achievType = achievType;
	}
	
	
	
	

}
