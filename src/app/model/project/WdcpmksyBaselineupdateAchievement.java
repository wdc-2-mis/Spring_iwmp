package app.model.project;

import java.math.BigInteger;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpMProject;

@Entity
@Table(name="wdcpmksy_baselineupdate_achievement" ,schema="public" )
public class WdcpmksyBaselineupdateAchievement implements java.io.Serializable {
	
	
	private Integer achievementId;
    private IwmpMFinYear iwmpMFinYear;
    private IwmpMMonth iwmpMMonth;
    private IwmpMProject iwmpMProject;
    private Character status;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
    private String requestIp;
    private Boolean blsconf;
    private Set<WdcpmksyBaselineupdateAchievementTranx> wdcpmksyBaselineupdateAchievementTranxes = new HashSet<WdcpmksyBaselineupdateAchievementTranx>(0);
    private Set<WdcpmksyBaselineupdateAchievementDetail> wdcpmksyBaselineupdateAchievementDetailse = new HashSet<WdcpmksyBaselineupdateAchievementDetail>(0);

   public WdcpmksyBaselineupdateAchievement() {
   }

	
   public WdcpmksyBaselineupdateAchievement(Integer achievementId) {
       this.achievementId = achievementId;
   }
   public WdcpmksyBaselineupdateAchievement(Integer achievementId, IwmpMFinYear iwmpMFinYear, IwmpMMonth iwmpMMonth, IwmpMProject iwmpMProject, Character status, String createdBy, Date createdOn, String updatedBy, Date updatedOn, String requestIp, Boolean blsconf, Set<WdcpmksyBaselineupdateAchievementTranx> wdcpmksyBaselineupdateAchievementTranxes, Set<WdcpmksyBaselineupdateAchievementDetail> wdcpmksyBaselineupdateAchievementDetailse) {
      this.achievementId = achievementId;
      this.iwmpMFinYear = iwmpMFinYear;
      this.iwmpMMonth = iwmpMMonth;
      this.iwmpMProject = iwmpMProject;
      this.status = status;
      this.createdBy = createdBy;
      this.createdOn = createdOn;
      this.updatedBy = updatedBy;
      this.updatedOn = updatedOn;
      this.requestIp = requestIp;
      this.blsconf=blsconf;
      this.wdcpmksyBaselineupdateAchievementTranxes = wdcpmksyBaselineupdateAchievementTranxes;
      this.wdcpmksyBaselineupdateAchievementDetailse = wdcpmksyBaselineupdateAchievementDetailse;
   }
  
   @Id 
   @GeneratedValue(strategy=GenerationType.AUTO)
   @Column(name="achievement_id", unique=true, nullable=false)
   public Integer getAchievementId() {
       return this.achievementId;
   }
   
   public void setAchievementId(Integer achievementId) {
       this.achievementId = achievementId;
   }

   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(name="fin_yr_cd")
   public IwmpMFinYear getIwmpMFinYear() {
       return this.iwmpMFinYear;
   }
   
   public void setIwmpMFinYear(IwmpMFinYear iwmpMFinYear) {
       this.iwmpMFinYear = iwmpMFinYear;
   }

   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(name="month_id")
   public IwmpMMonth getIwmpMMonth() {
       return this.iwmpMMonth;
   }
   
   public void setIwmpMMonth(IwmpMMonth iwmpMMonth) {
       this.iwmpMMonth = iwmpMMonth;
   }

   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(name="proj_id")
   public IwmpMProject getIwmpMProject() {
       return this.iwmpMProject;
   }
   
   public void setIwmpMProject(IwmpMProject iwmpMProject) {
       this.iwmpMProject = iwmpMProject;
   }

   
   @Column(name="status", length=1)
   public Character getStatus() {
       return this.status;
   }
   
   public void setStatus(Character status) {
       this.status = status;
   }

   
   @Column(name="created_by", length=20)
   public String getCreatedBy() {
       return this.createdBy;
   }
   
   public void setCreatedBy(String createdBy) {
       this.createdBy = createdBy;
   }

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="created_on", length=29)
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

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="updated_on", length=29)
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
   
   @Column(name="blsconf")
   public Boolean getBlsconf() {
       return this.blsconf;
   }
   
   public void setBlsconf(Boolean blsconf) {
       this.blsconf = blsconf;
   }

 @OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyBaselineupdateAchievement")
public Set<WdcpmksyBaselineupdateAchievementTranx> getWdcpmksyBaselineupdateAchievementTranxes() {
	return wdcpmksyBaselineupdateAchievementTranxes;
}


public void setWdcpmksyBaselineupdateAchievementTranxes(
		Set<WdcpmksyBaselineupdateAchievementTranx> wdcpmksyBaselineupdateAchievementTranxes) {
	this.wdcpmksyBaselineupdateAchievementTranxes = wdcpmksyBaselineupdateAchievementTranxes;
}

@OneToMany(fetch=FetchType.LAZY, mappedBy="wdcpmksyBaselineupdateAchievement")
public Set<WdcpmksyBaselineupdateAchievementDetail> getWdcpmksyBaselineupdateAchievementDetailse() {
	return wdcpmksyBaselineupdateAchievementDetailse;
}


public void setWdcpmksyBaselineupdateAchievementDetailse(
		Set<WdcpmksyBaselineupdateAchievementDetail> wdcpmksyBaselineupdateAchievementDetailse) {
	this.wdcpmksyBaselineupdateAchievementDetailse = wdcpmksyBaselineupdateAchievementDetailse;
}
   
   

}
