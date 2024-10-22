package app.bean;

import java.math.BigDecimal;

public class TargetAchDashboardBean {

	private Integer headcode;
	private String headname;
	private BigDecimal achievement;
	private BigDecimal target;
	private Integer stcode;
	private String stname;
	private String yrdesc;
	private Integer fin_yr_cd;
	private Integer month_id;
	private String month_name;
	private BigDecimal uptoachieve;
	private BigDecimal achievpercent;
	private BigDecimal cultivable_wasteland;
	private BigDecimal degraded_land;
	private BigDecimal rainfed;
	private BigDecimal forest_land;
	private BigDecimal others;
	
	private BigDecimal cultivable_wasteland_achv;
	private BigDecimal degraded_land_achv;
	private BigDecimal rainfed_achv;
	private BigDecimal forest_land_achv;
	private BigDecimal others_achv;
	private BigDecimal protective_irrigation;
	private BigDecimal assured_irrigation;
	private BigDecimal no_irrigation;
	private BigDecimal others_owned;
	
	private BigDecimal protective_irrigation_achv;
	private BigDecimal assured_irrigation_achv;
	private BigDecimal no_irrigation_achv;
	private BigDecimal others_owned_achv;
	
	private BigDecimal private_owner;
	private BigDecimal govt_owned;
	private BigDecimal community_owned;
	private BigDecimal private_owner_ach;
	private BigDecimal govt_owned_ach;
	private BigDecimal community_owned_ach;
	private BigDecimal others_owned_ach;
	private BigDecimal no_crop;
	private BigDecimal single_crop;
	private BigDecimal double_crop;
	private BigDecimal multiple_crop;
	private BigDecimal no_crop_ach;
	private BigDecimal single_crop_ach;
	private BigDecimal double_crop_ach;
	private BigDecimal multiple_crop_ach;
	
	
	private String fin_yr_desc;
	private Integer achiev_month;
	private String descr;
	private BigDecimal achiev;
	private BigDecimal progachieve;
	private String distname;
	private Integer dcode;
	private String unit;
	
	
	
	public Integer getHeadcode() {
		return headcode;
	}
	public void setHeadcode(Integer headcode) {
		this.headcode = headcode;
	}
	public String getHeadname() {
		return headname;
	}
	public void setHeadname(String headname) {
		this.headname = headname;
	}
	public BigDecimal getAchievement() {
		return achievement;
	}
	public void setAchievement(BigDecimal achievement) {
		this.achievement = achievement;
	}
	public BigDecimal getTarget() {
		return target;
	}
	public void setTarget(BigDecimal target) {
		this.target = target;
	}
	public Integer getStcode() {
		return stcode;
	}
	public void setStcode(Integer stcode) {
		this.stcode = stcode;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public String getYrdesc() {
		return yrdesc;
	}
	public void setYrdesc(String yrdesc) {
		this.yrdesc = yrdesc;
	}
	public Integer getFin_yr_cd() {
		return fin_yr_cd;
	}
	public void setFin_yr_cd(Integer fin_yr_cd) {
		this.fin_yr_cd = fin_yr_cd;
	}
	public Integer getMonth_id() {
		return month_id;
	}
	public void setMonth_id(Integer month_id) {
		this.month_id = month_id;
	}
	public String getMonth_name() {
		return month_name;
	}
	public void setMonth_name(String month_name) {
		this.month_name = month_name;
	}
	public BigDecimal getUptoachieve() {
		return uptoachieve;
	}
	public void setUptoachieve(BigDecimal uptoachieve) {
		this.uptoachieve = uptoachieve;
	}
	public BigDecimal getAchievpercent() {
		return achievpercent;
	}
	public void setAchievpercent(BigDecimal achievpercent) {
		this.achievpercent = achievpercent;
	}
	public BigDecimal getCultivable_wasteland() {
		return cultivable_wasteland;
	}
	public void setCultivable_wasteland(BigDecimal cultivable_wasteland) {
		this.cultivable_wasteland = cultivable_wasteland;
	}
	public BigDecimal getDegraded_land() {
		return degraded_land;
	}
	public void setDegraded_land(BigDecimal degraded_land) {
		this.degraded_land = degraded_land;
	}
	public BigDecimal getRainfed() {
		return rainfed;
	}
	public void setRainfed(BigDecimal rainfed) {
		this.rainfed = rainfed;
	}
	public BigDecimal getForest_land() {
		return forest_land;
	}
	public void setForest_land(BigDecimal forest_land) {
		this.forest_land = forest_land;
	}
	public BigDecimal getOthers() {
		return others;
	}
	public void setOthers(BigDecimal others) {
		this.others = others;
	}
	public BigDecimal getCultivable_wasteland_achv() {
		return cultivable_wasteland_achv;
	}
	public void setCultivable_wasteland_achv(BigDecimal cultivable_wasteland_achv) {
		this.cultivable_wasteland_achv = cultivable_wasteland_achv;
	}
	public BigDecimal getDegraded_land_achv() {
		return degraded_land_achv;
	}
	public void setDegraded_land_achv(BigDecimal degraded_land_achv) {
		this.degraded_land_achv = degraded_land_achv;
	}
	public BigDecimal getRainfed_achv() {
		return rainfed_achv;
	}
	public void setRainfed_achv(BigDecimal rainfed_achv) {
		this.rainfed_achv = rainfed_achv;
	}
	public BigDecimal getForest_land_achv() {
		return forest_land_achv;
	}
	public void setForest_land_achv(BigDecimal forest_land_achv) {
		this.forest_land_achv = forest_land_achv;
	}
	public BigDecimal getOthers_achv() {
		return others_achv;
	}
	public void setOthers_achv(BigDecimal others_achv) {
		this.others_achv = others_achv;
	}
	public BigDecimal getProtective_irrigation() {
		return protective_irrigation;
	}
	public void setProtective_irrigation(BigDecimal protective_irrigation) {
		this.protective_irrigation = protective_irrigation;
	}
	public BigDecimal getAssured_irrigation() {
		return assured_irrigation;
	}
	public void setAssured_irrigation(BigDecimal assured_irrigation) {
		this.assured_irrigation = assured_irrigation;
	}
	public BigDecimal getNo_irrigation() {
		return no_irrigation;
	}
	public void setNo_irrigation(BigDecimal no_irrigation) {
		this.no_irrigation = no_irrigation;
	}
	public BigDecimal getOthers_owned() {
		return others_owned;
	}
	public void setOthers_owned(BigDecimal others_owned) {
		this.others_owned = others_owned;
	}
	public BigDecimal getProtective_irrigation_achv() {
		return protective_irrigation_achv;
	}
	public void setProtective_irrigation_achv(BigDecimal protective_irrigation_achv) {
		this.protective_irrigation_achv = protective_irrigation_achv;
	}
	public BigDecimal getAssured_irrigation_achv() {
		return assured_irrigation_achv;
	}
	public void setAssured_irrigation_achv(BigDecimal assured_irrigation_achv) {
		this.assured_irrigation_achv = assured_irrigation_achv;
	}
	public BigDecimal getNo_irrigation_achv() {
		return no_irrigation_achv;
	}
	public void setNo_irrigation_achv(BigDecimal no_irrigation_achv) {
		this.no_irrigation_achv = no_irrigation_achv;
	}
	public BigDecimal getOthers_owned_achv() {
		return others_owned_achv;
	}
	public void setOthers_owned_achv(BigDecimal others_owned_achv) {
		this.others_owned_achv = others_owned_achv;
	}
	public String getFin_yr_desc() {
		return fin_yr_desc;
	}
	public void setFin_yr_desc(String fin_yr_desc) {
		this.fin_yr_desc = fin_yr_desc;
	}
	public Integer getAchiev_month() {
		return achiev_month;
	}
	public void setAchiev_month(Integer achiev_month) {
		this.achiev_month = achiev_month;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public BigDecimal getAchiev() {
		return achiev;
	}
	public void setAchiev(BigDecimal achiev) {
		this.achiev = achiev;
	}
	public BigDecimal getProgachieve() {
		return progachieve;
	}
	public void setProgachieve(BigDecimal progachieve) {
		this.progachieve = progachieve;
	}
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public Integer getDcode() {
		return dcode;
	}
	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public BigDecimal getPrivate_owner() {
		return private_owner;
	}
	public void setPrivate_owner(BigDecimal private_owner) {
		this.private_owner = private_owner;
	}
	public BigDecimal getGovt_owned() {
		return govt_owned;
	}
	public void setGovt_owned(BigDecimal govt_owned) {
		this.govt_owned = govt_owned;
	}
	public BigDecimal getCommunity_owned() {
		return community_owned;
	}
	public void setCommunity_owned(BigDecimal community_owned) {
		this.community_owned = community_owned;
	}
	public BigDecimal getPrivate_owner_ach() {
		return private_owner_ach;
	}
	public void setPrivate_owner_ach(BigDecimal private_owner_ach) {
		this.private_owner_ach = private_owner_ach;
	}
	public BigDecimal getGovt_owned_ach() {
		return govt_owned_ach;
	}
	public void setGovt_owned_ach(BigDecimal govt_owned_ach) {
		this.govt_owned_ach = govt_owned_ach;
	}
	public BigDecimal getCommunity_owned_ach() {
		return community_owned_ach;
	}
	public void setCommunity_owned_ach(BigDecimal community_owned_ach) {
		this.community_owned_ach = community_owned_ach;
	}
	public BigDecimal getOthers_owned_ach() {
		return others_owned_ach;
	}
	public void setOthers_owned_ach(BigDecimal others_owned_ach) {
		this.others_owned_ach = others_owned_ach;
	}
	public BigDecimal getNo_crop() {
		return no_crop;
	}
	public void setNo_crop(BigDecimal no_crop) {
		this.no_crop = no_crop;
	}
	public BigDecimal getSingle_crop() {
		return single_crop;
	}
	public void setSingle_crop(BigDecimal single_crop) {
		this.single_crop = single_crop;
	}
	public BigDecimal getDouble_crop() {
		return double_crop;
	}
	public void setDouble_crop(BigDecimal double_crop) {
		this.double_crop = double_crop;
	}
	public BigDecimal getMultiple_crop() {
		return multiple_crop;
	}
	public void setMultiple_crop(BigDecimal multiple_crop) {
		this.multiple_crop = multiple_crop;
	}
	public BigDecimal getNo_crop_ach() {
		return no_crop_ach;
	}
	public void setNo_crop_ach(BigDecimal no_crop_ach) {
		this.no_crop_ach = no_crop_ach;
	}
	public BigDecimal getSingle_crop_ach() {
		return single_crop_ach;
	}
	public void setSingle_crop_ach(BigDecimal single_crop_ach) {
		this.single_crop_ach = single_crop_ach;
	}
	public BigDecimal getDouble_crop_ach() {
		return double_crop_ach;
	}
	public void setDouble_crop_ach(BigDecimal double_crop_ach) {
		this.double_crop_ach = double_crop_ach;
	}
	public BigDecimal getMultiple_crop_ach() {
		return multiple_crop_ach;
	}
	public void setMultiple_crop_ach(BigDecimal multiple_crop_ach) {
		this.multiple_crop_ach = multiple_crop_ach;
	}
	
	
	
}
