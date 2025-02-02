package app.model;
// Generated 13 Aug, 2021 2:32:44 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.model.project.IwmpProjectPhysicalAsset;

/**
 * IwmpAssetParameterAchievement generated by hbm2java
 */
@Entity
@Table(name="iwmp_activity_asset_addon_parameter_achievement"
    ,schema="public"
)
public class IwmpActivityAssetAddonParameterAchievement  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long assetAchievementid;
     private IwmpActivityAssetAddonParameter iwmpAssetParameter;
     private IwmpMFinYear iwmpMFinYear;
     private IwmpMMonth iwmpMMonth;
     private IwmpProjectAssetStatus iwmpProjectAssetStatus;
     private IwmpProjectPhysicalAsset iwmpProjectPhysicalAsset;
     private BigDecimal achievement;

    public IwmpActivityAssetAddonParameterAchievement() {
    }

	
    public IwmpActivityAssetAddonParameterAchievement(long assetAchievementid, IwmpProjectAssetStatus iwmpProjectAssetStatus) {
        this.assetAchievementid = assetAchievementid;
        this.iwmpProjectAssetStatus = iwmpProjectAssetStatus;
    }
    public IwmpActivityAssetAddonParameterAchievement(long assetAchievementid, IwmpActivityAssetAddonParameter iwmpAssetParameter, IwmpMFinYear iwmpMFinYear, IwmpMMonth iwmpMMonth, IwmpProjectAssetStatus iwmpProjectAssetStatus, IwmpProjectPhysicalAsset iwmpProjectPhysicalAsset, BigDecimal achievement) {
       this.assetAchievementid = assetAchievementid;
       this.iwmpAssetParameter = iwmpAssetParameter;
       this.iwmpMFinYear = iwmpMFinYear;
       this.iwmpMMonth = iwmpMMonth;
       this.iwmpProjectAssetStatus = iwmpProjectAssetStatus;
       this.iwmpProjectPhysicalAsset = iwmpProjectPhysicalAsset;
       this.achievement = achievement;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="asset_achievement_id", unique=true, nullable=false)
    public long getAssetAchievementid() {
        return this.assetAchievementid;
    }
    
    public void setAssetAchievementid(long assetAchievementid) {
        this.assetAchievementid = assetAchievementid;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="asset_parameter_id")
    public IwmpActivityAssetAddonParameter getIwmpAssetParameter() {
        return this.iwmpAssetParameter;
    }
    
    public void setIwmpAssetParameter(IwmpActivityAssetAddonParameter iwmpAssetParameter) {
        this.iwmpAssetParameter = iwmpAssetParameter;
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
    @JoinColumn(name="asset_status_id", nullable=false)
    public IwmpProjectAssetStatus getIwmpProjectAssetStatus() {
        return this.iwmpProjectAssetStatus;
    }
    
    public void setIwmpProjectAssetStatus(IwmpProjectAssetStatus iwmpProjectAssetStatus) {
        this.iwmpProjectAssetStatus = iwmpProjectAssetStatus;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="asset_id")
    public IwmpProjectPhysicalAsset getIwmpProjectPhysicalAsset() {
        return this.iwmpProjectPhysicalAsset;
    }
    
    public void setIwmpProjectPhysicalAsset(IwmpProjectPhysicalAsset iwmpProjectPhysicalAsset) {
        this.iwmpProjectPhysicalAsset = iwmpProjectPhysicalAsset;
    }

    
    @Column(name="achievement", precision=10)
    public BigDecimal getAchievement() {
        return this.achievement;
    }
    
    public void setAchievement(BigDecimal achievement) {
        this.achievement = achievement;
    }




}


