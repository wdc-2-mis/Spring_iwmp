package app.model.project;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.model.master.IwmpMPhyActivity;

@Entity
@Table(name="wdcpmksy_baselineupdate_achievement_detail" ,schema="public")
public class WdcpmksyBaselineupdateAchievementDetail {
	
	
	private Integer achievementDetailsId;
    private WdcpmksyBaselineupdateAchievement wdcpmksyBaselineupdateAchievement;
    private BigDecimal cultivableWasteland;
    private BigDecimal cultivableWastelandAchv;
    private BigDecimal degradedLand;
    private BigDecimal degradedLandAchv;
    private BigDecimal rainfed;
    private BigDecimal rainfedAchv;
    private BigDecimal other;
    private BigDecimal othersAchv;
    private BigDecimal totalIncome;
    private BigDecimal totalIncomeAchv;
    private BigDecimal grossCroppedArea;
    private BigDecimal grossCroppedAreaAchv;
    private BigDecimal protectiveIrrigation;
    private BigDecimal protectiveIrrigationAchv;
    private BigDecimal assuredIrrigation;
    private BigDecimal assuredIrrigationAchv;
    private BigDecimal noIrrigation;
    private BigDecimal noIrrigationAchv;
    private BigDecimal noCrop;
    private BigDecimal noCropAchv;
    private BigDecimal singleCrop;
    private BigDecimal singleCropAchv;
    private BigDecimal doubleCrop;
    private BigDecimal doubleCropAchv;
    private BigDecimal multipleCrop;
    private BigDecimal multipleCropAchv;
    
    
    public WdcpmksyBaselineupdateAchievementDetail() {
    }

 	
    public WdcpmksyBaselineupdateAchievementDetail(Integer achievementDetailsId) {
        this.achievementDetailsId = achievementDetailsId;
    }
    public WdcpmksyBaselineupdateAchievementDetail(Integer achievementDetailsId, WdcpmksyBaselineupdateAchievement wdcpmksyBaselineupdateAchievement, BigDecimal cultivableWasteland, BigDecimal cultivableWastelandAchv, BigDecimal degradedLand, BigDecimal degradedLandAchv, BigDecimal rainfed,BigDecimal rainfedAchv,BigDecimal other,BigDecimal othersAchv,BigDecimal totalIncome,BigDecimal totalIncomeAchv,BigDecimal grossCroppedArea,BigDecimal grossCroppedAreaAchv,BigDecimal protectiveIrrigation,BigDecimal protectiveIrrigationAchv,BigDecimal assuredIrrigation,BigDecimal assuredIrrigationAchv,BigDecimal noIrrigation,BigDecimal noIrrigationAchv,BigDecimal noCrop,BigDecimal noCropAchv,BigDecimal singleCrop,BigDecimal singleCropAchv,BigDecimal doubleCrop, BigDecimal doubleCropAchv,BigDecimal multipleCrop,BigDecimal multipleCropAchv) {
       this.achievementDetailsId = achievementDetailsId;
       this.wdcpmksyBaselineupdateAchievement = wdcpmksyBaselineupdateAchievement;
       this.cultivableWasteland = cultivableWasteland;
       this.cultivableWastelandAchv=cultivableWastelandAchv;
       this.degradedLand=degradedLand;
       this.degradedLandAchv=degradedLandAchv;
       this.rainfed=rainfed;
       this.rainfedAchv=rainfedAchv;
       this.other=other;
       this.othersAchv=othersAchv;
       this.totalIncome=totalIncome;
       this.totalIncomeAchv=totalIncomeAchv;
       this.grossCroppedArea=grossCroppedArea;
       this.grossCroppedAreaAchv=grossCroppedAreaAchv;
       this.protectiveIrrigation=protectiveIrrigation;
       this.protectiveIrrigationAchv=protectiveIrrigationAchv;
       this.assuredIrrigation=assuredIrrigation;
       this.assuredIrrigationAchv=assuredIrrigationAchv;
       this.noIrrigation=noIrrigation;
       this.noIrrigationAchv=noIrrigationAchv;
       this.noCrop=noCrop;
       this.noCropAchv=noCropAchv;
       this.singleCrop=singleCrop;
       this.singleCropAchv=singleCropAchv;
       this.doubleCrop=doubleCrop;
       this.doubleCropAchv=doubleCropAchv;
       this.multipleCrop=multipleCrop;
       this.multipleCropAchv=multipleCropAchv;
    }
   
    @Id 
    @Column(name="achievement_details_id", unique=true, nullable=false)
    public Integer getAchievementDetailsId() {
        return this.achievementDetailsId;
    }
    
    public void setAchievementDetailsId(Integer achievementDetailsId) {
        this.achievementDetailsId = achievementDetailsId;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="achievement_id")
    public WdcpmksyBaselineupdateAchievement getWdcpmksyBaselineupdateAchievement() {
        return this.wdcpmksyBaselineupdateAchievement;
    }
    
    public void setWdcpmksyBaselineupdateAchievement(WdcpmksyBaselineupdateAchievement wdcpmksyBaselineupdateAchievement) {
        this.wdcpmksyBaselineupdateAchievement = wdcpmksyBaselineupdateAchievement;
    }

    
    @Column(name="cultivable_wasteland", precision=20)
    public BigDecimal getCultivableWasteland() {
        return this.cultivableWasteland;
    }
    
    public void setCultivableWasteland(BigDecimal cultivableWasteland) {
        this.cultivableWasteland = cultivableWasteland;
    }

    @Column(name="cultivable_wasteland_achv", precision=20)
	public BigDecimal getCultivableWastelandAchv() {
		return cultivableWastelandAchv;
	}


	public void setCultivableWastelandAchv(BigDecimal cultivableWastelandAchv) {
		this.cultivableWastelandAchv = cultivableWastelandAchv;
	}

	@Column(name="degraded_land", precision=20)
	public BigDecimal getDegradedLand() {
		return degradedLand;
	}


	public void setDegradedLand(BigDecimal degradedLand) {
		this.degradedLand = degradedLand;
	}

	@Column(name="degraded_land_achv", precision=20)
	public BigDecimal getDegradedLandAchv() {
		return degradedLandAchv;
	}


	public void setDegradedLandAchv(BigDecimal degradedLandAchv) {
		this.degradedLandAchv = degradedLandAchv;
	}

	@Column(name="rainfed", precision=20)
	public BigDecimal getRainfed() {
		return rainfed;
	}


	public void setRainfed(BigDecimal rainfed) {
		this.rainfed = rainfed;
	}

	@Column(name="rainfed_achv", precision=20)
	public BigDecimal getRainfedAchv() {
		return rainfedAchv;
	}


	public void setRainfedAchv(BigDecimal rainfedAchv) {
		this.rainfedAchv = rainfedAchv;
	}

	@Column(name="other", precision=20)
	public BigDecimal getOther() {
		return other;
	}


	public void setOther(BigDecimal other) {
		this.other = other;
	}

	@Column(name="others_achv", precision=20)
	public BigDecimal getOthersAchv() {
		return othersAchv;
	}


	public void setOthersAchv(BigDecimal othersAchv) {
		this.othersAchv = othersAchv;
	}

	@Column(name="total_income ", precision=20)
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}


	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	@Column(name="total_income_achv", precision=20)
	public BigDecimal getTotalIncomeAchv() {
		return totalIncomeAchv;
	}


	public void setTotalIncomeAchv(BigDecimal totalIncomeAchv) {
		this.totalIncomeAchv = totalIncomeAchv;
	}

	@Column(name="gross_cropped_area", precision=20)
	public BigDecimal getGrossCroppedArea() {
		return grossCroppedArea;
	}


	public void setGrossCroppedArea(BigDecimal grossCroppedArea) {
		this.grossCroppedArea = grossCroppedArea;
	}

	@Column(name="gross_cropped_area_achv", precision=20)
	public BigDecimal getGrossCroppedAreaAchv() {
		return grossCroppedAreaAchv;
	}


	public void setGrossCroppedAreaAchv(BigDecimal grossCroppedAreaAchv) {
		this.grossCroppedAreaAchv = grossCroppedAreaAchv;
	}

	@Column(name="protective_irrigation", precision=20)
	public BigDecimal getProtectiveIrrigation() {
		return protectiveIrrigation;
	}


	public void setProtectiveIrrigation(BigDecimal protectiveIrrigation) {
		this.protectiveIrrigation = protectiveIrrigation;
	}

	@Column(name="protective_irrigation_achv", precision=20)
	public BigDecimal getProtectiveIrrigationAchv() {
		return protectiveIrrigationAchv;
	}


	public void setProtectiveIrrigationAchv(BigDecimal protectiveIrrigationAchv) {
		this.protectiveIrrigationAchv = protectiveIrrigationAchv;
	}

	@Column(name="assured_irrigation", precision=20)
	public BigDecimal getAssuredIrrigation() {
		return assuredIrrigation;
	}


	public void setAssuredIrrigation(BigDecimal assuredIrrigation) {
		this.assuredIrrigation = assuredIrrigation;
	}

	@Column(name="assured_irrigation_achv", precision=20)
	public BigDecimal getAssuredIrrigationAchv() {
		return assuredIrrigationAchv;
	}


	public void setAssuredIrrigationAchv(BigDecimal assuredIrrigationAchv) {
		this.assuredIrrigationAchv = assuredIrrigationAchv;
	}

	@Column(name="no_irrigation", precision=20)
	public BigDecimal getNoIrrigation() {
		return noIrrigation;
	}


	public void setNoIrrigation(BigDecimal noIrrigation) {
		this.noIrrigation = noIrrigation;
	}

	@Column(name="no_irrigation_achv", precision=20)
	public BigDecimal getNoIrrigationAchv() {
		return noIrrigationAchv;
	}


	public void setNoIrrigationAchv(BigDecimal noIrrigationAchv) {
		this.noIrrigationAchv = noIrrigationAchv;
	}

	@Column(name="no_crop", precision=20)
	public BigDecimal getNoCrop() {
		return noCrop;
	}


	public void setNoCrop(BigDecimal noCrop) {
		this.noCrop = noCrop;
	}

	@Column(name="no_crop_achv", precision=20)
	public BigDecimal getNoCropAchv() {
		return noCropAchv;
	}


	public void setNoCropAchv(BigDecimal noCropAchv) {
		this.noCropAchv = noCropAchv;
	}

	@Column(name="single_crop", precision=20)
	public BigDecimal getSingleCrop() {
		return singleCrop;
	}


	public void setSingleCrop(BigDecimal singleCrop) {
		this.singleCrop = singleCrop;
	}

	@Column(name="single_crop_achv", precision=20)
	public BigDecimal getSingleCropAchv() {
		return singleCropAchv;
	}


	public void setSingleCropAchv(BigDecimal singleCropAchv) {
		this.singleCropAchv = singleCropAchv;
	}

	@Column(name="double_crop", precision=20)
	public BigDecimal getDoubleCrop() {
		return doubleCrop;
	}


	public void setDoubleCrop(BigDecimal doubleCrop) {
		this.doubleCrop = doubleCrop;
	}

	@Column(name="double_crop_achv", precision=20)
	public BigDecimal getDoubleCropAchv() {
		return doubleCropAchv;
	}


	public void setDoubleCropAchv(BigDecimal doubleCropAchv) {
		this.doubleCropAchv = doubleCropAchv;
	}

	@Column(name="multiple_crop", precision=20)
	public BigDecimal getMultipleCrop() {
		return multipleCrop;
	}


	public void setMultipleCrop(BigDecimal multipleCrop) {
		this.multipleCrop = multipleCrop;
	}

	@Column(name="multiple_crop_achv", precision=20)
	public BigDecimal getMultipleCropAchv() {
		return multipleCropAchv;
	}


	public void setMultipleCropAchv(BigDecimal multipleCropAchv) {
		this.multipleCropAchv = multipleCropAchv;
	}


	
    
    



}
