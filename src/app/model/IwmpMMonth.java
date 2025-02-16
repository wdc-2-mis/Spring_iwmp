package app.model;
// Generated 13 Aug, 2021 2:32:44 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.project.WdcpmksyBaselineupdateAchievement;
import app.model.project.WdcpmksyProjectPhyAssetAchievement;
import app.model.project.WdcpmksyProjectPhysicalAchievement;
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.projectevaluation.model.WdcpmksyProjectProfileEvaluation;
import app.model.master.WdcpmksyMOutcome;

/**
 * IwmpMMonth generated by hbm2java
 */
@Entity
@Table(name="iwmp_m_month" ,schema="public")
public class IwmpMMonth  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer monthId;
     private String monthName;
     private String lastUpdatedBy;
     private Date lastUpdatedDate;
     private String requestIp;
     private Integer finmonthId;
     private Integer achievmonthId;
     private Character achievStatus;
     private Set<Outcome2Data> outcome2Datas = new HashSet<Outcome2Data>(0);
     private Set<IwmpActivityAssetAddonParameterAchievement> iwmpAssetParameterAchievements = new HashSet<IwmpActivityAssetAddonParameterAchievement>(0);
     //private Set<WdcpmksyProjectPhysicalAchievementDetails> wdcpmksyProjectPhysicalAchievementDetailses = new HashSet<WdcpmksyProjectPhysicalAchievementDetails>(0);
     private Set<WdcpmksyProjectPhysicalAchievement> wdcpmksyProjectPhysicalAchievements = new HashSet<WdcpmksyProjectPhysicalAchievement>(0);
     private Set<WdcpmksyProjectPhyAssetAchievement> wdcpmksyProjectPhyAssetAchievements = new HashSet<WdcpmksyProjectPhyAssetAchievement>(0);
     private Set<WdcpmksyMOutcome> wdcpmksyMOutcomes = new HashSet<WdcpmksyMOutcome>(0);
     private Set<WdcpmksyBaselineupdateAchievement> wdcpmksyBaselineupdateAchievements = new HashSet<WdcpmksyBaselineupdateAchievement>(0);
     private Set<WdcpmksyProjectProfileEvaluation> wdcpmksyProjectProfileEvaluation = new HashSet<WdcpmksyProjectProfileEvaluation>(0);
     
    public IwmpMMonth() {
    }

	
    public IwmpMMonth(Integer monthId, String monthName) {
        this.monthId = monthId;
        this.monthName = monthName;
    }
    public IwmpMMonth(Integer monthId, String monthName, String lastUpdatedBy, Date lastUpdatedDate, String requestIp, Set<Outcome2Data> outcome2Datas, Set<WdcpmksyProjectPhysicalAchievement> wdcpmksyProjectPhysicalAchievements, Set<IwmpActivityAssetAddonParameterAchievement> iwmpAssetParameterAchievements, Integer finmonthId, Integer achievmonthId, Set<WdcpmksyBaselineupdateAchievement> wdcpmksyBaselineupdateAchievements, Character achievStatus) {
       this.monthId = monthId;
       this.monthName = monthName;
       this.lastUpdatedBy = lastUpdatedBy;
       this.lastUpdatedDate = lastUpdatedDate;
       this.requestIp = requestIp;
       this.outcome2Datas = outcome2Datas;
       this.wdcpmksyProjectPhysicalAchievements = wdcpmksyProjectPhysicalAchievements;
       this.iwmpAssetParameterAchievements = iwmpAssetParameterAchievements;
       this.wdcpmksyMOutcomes = wdcpmksyMOutcomes;
       this.finmonthId=finmonthId;
       this.achievmonthId=achievmonthId;
       this.wdcpmksyBaselineupdateAchievements=wdcpmksyBaselineupdateAchievements;
       this.achievStatus=achievStatus;
    }
   
    @Id 
    @Column(name="month_id", unique=true, nullable=false, precision=2, scale=0)
    public Integer getMonthId() {
        return this.monthId;
    }
    
    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    
    @Column(name="month_name", nullable=false, length=10)
    public String getMonthName() {
        return this.monthName;
    }
    
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    
    @Column(name="last_updated_by", length=20)
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="last_updated_date", length=13)
    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }
    
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    
    @Column(name="request_ip", length=20)
    public String getRequestIp() {
        return this.requestIp;
    }
    
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMMonth")
    public Set<Outcome2Data> getOutcome2Datas() {
        return this.outcome2Datas;
    }
    
    public void setOutcome2Datas(Set<Outcome2Data> outcome2Datas) {
        this.outcome2Datas = outcome2Datas;
    }
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMMonth")
    public Set<IwmpActivityAssetAddonParameterAchievement> getIwmpAssetParameterAchievements() {
        return this.iwmpAssetParameterAchievements;
    }
    
    public void setIwmpAssetParameterAchievements(Set<IwmpActivityAssetAddonParameterAchievement> iwmpAssetParameterAchievements) {
        this.iwmpAssetParameterAchievements = iwmpAssetParameterAchievements;
    }
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMMonth")
    public Set<WdcpmksyMOutcome> getWdcpmksyMOutcomes() {
        return this.wdcpmksyMOutcomes;
    }
    
    public void setWdcpmksyMOutcomes(Set<WdcpmksyMOutcome> wdcpmksyMOutcomes) {
        this.wdcpmksyMOutcomes = wdcpmksyMOutcomes;
    }
    
	/*
	 * @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMMonth") public
	 * Set<WdcpmksyProjectPhysicalAchievementDetails>
	 * getWdcpmksyProjectPhysicalAchievementDetailses() { return
	 * this.wdcpmksyProjectPhysicalAchievementDetailses; }
	 * 
	 * public void setWdcpmksyProjectPhysicalAchievementDetailses(Set<
	 * WdcpmksyProjectPhysicalAchievementDetails>
	 * wdcpmksyProjectPhysicalAchievementDetailses) {
	 * this.wdcpmksyProjectPhysicalAchievementDetailses =
	 * wdcpmksyProjectPhysicalAchievementDetailses; }
	 */

    @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMMonth")
    public Set<WdcpmksyProjectPhysicalAchievement> getWdcpmksyProjectPhysicalAchievements() {
        return this.wdcpmksyProjectPhysicalAchievements;
    }
    
    public void setWdcpmksyProjectPhysicalAchievements(Set<WdcpmksyProjectPhysicalAchievement> wdcpmksyProjectPhysicalAchievements) {
        this.wdcpmksyProjectPhysicalAchievements = wdcpmksyProjectPhysicalAchievements;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMMonth")
    public Set<WdcpmksyProjectPhyAssetAchievement> getWdcpmksyProjectPhyAssetAchievements() {
        return this.wdcpmksyProjectPhyAssetAchievements;
    }
    
    public void setWdcpmksyProjectPhyAssetAchievements(Set<WdcpmksyProjectPhyAssetAchievement> wdcpmksyProjectPhyAssetAchievements) {
        this.wdcpmksyProjectPhyAssetAchievements = wdcpmksyProjectPhyAssetAchievements;
    }

    @Column(name = "fin_month_id", unique = true, nullable = false)
	public Integer getFinmonthId() {
		return finmonthId;
	}


	public void setFinmonthId(Integer finmonthId) {
		this.finmonthId = finmonthId;
	}

	@Column(name = "achiev_month", unique = true, nullable = false)
	public Integer getAchievmonthId() {
		return achievmonthId;
	}


	public void setAchievmonthId(Integer achievmonthId) {
		this.achievmonthId = achievmonthId;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMMonth")
	public Set<WdcpmksyBaselineupdateAchievement> getWdcpmksyBaselineupdateAchievements() {
		return wdcpmksyBaselineupdateAchievements;
	}


	public void setWdcpmksyBaselineupdateAchievements(
			Set<WdcpmksyBaselineupdateAchievement> wdcpmksyBaselineupdateAchievements) {
		this.wdcpmksyBaselineupdateAchievements = wdcpmksyBaselineupdateAchievements;
	}

	@Column(name="achiev_status", length=1)
	public Character getAchievStatus() {
		return achievStatus;
	}


	public void setAchievStatus(Character achievStatus) {
		this.achievStatus = achievStatus;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMMonth")
	public Set<WdcpmksyProjectProfileEvaluation> getWdcpmksyProjectProfileEvaluation() {
		return wdcpmksyProjectProfileEvaluation;
	}


	public void setWdcpmksyProjectProfileEvaluation(
			Set<WdcpmksyProjectProfileEvaluation> wdcpmksyProjectProfileEvaluation) {
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
	}

	
	

}


