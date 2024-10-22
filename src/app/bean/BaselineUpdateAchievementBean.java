package app.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BaselineUpdateAchievementBean {
	
	private String projname;
	private Integer projid;
	private BigDecimal project_area;
	private BigDecimal treatable_area;
	private BigDecimal cultivable_wasteland;
	private BigDecimal cultivable_wasteland_achv;
	private BigDecimal degraded_land;
	private BigDecimal degraded_land_achv;
	private BigDecimal rainfed;
	private BigDecimal rainfed_achv;
	private BigDecimal forest_land;
	private BigDecimal forest_land_achv;
	private BigDecimal others;
	private BigDecimal others_achv;
	private BigDecimal gross_cropped_area;
	private BigDecimal gross_cropped_area_achv;
	private BigDecimal total_income;
	private BigDecimal total_income_achv;
	
	private BigDecimal protective_irrigation;
	private BigDecimal assured_irrigation;
	private BigDecimal no_irrigation;
	private BigDecimal no_crop;
	private BigDecimal single_crop;
	private BigDecimal double_crop;
	private BigDecimal multiple_crop;
	private BigDecimal protective_irrigation_achv;
	private BigDecimal assured_irrigation_achv;
	private BigDecimal no_irrigation_achv;
	private BigDecimal no_crop_achv;
	private BigDecimal single_crop_achv;
	private BigDecimal double_crop_achv;
	private BigDecimal multiple_crop_achv;
	
	private String startdate;
	private String enddate;
	private Integer finyr;
	private Integer startmonth;
	private Integer endmonth;
	private Integer projectcd;
	private String projectdesc;
	private Integer monthid;
	private String monthdesc;
	private Integer finyrcd;
	private String finyrdesc;
	private String status;
	private String currentlyat;
	private String remarks;
	private Integer achievementid;
	private Character action;
	private String usertype;
	private Boolean show;
	private String sentfrom;
	private String sentto;
	private Date senton;
	private String fin_yr_desc;
	public String getProjname() {
		return projname;
	}
	public void setProjname(String projname) {
		this.projname = projname;
	}
	public Integer getProjid() {
		return projid;
	}
	public void setProjid(Integer projid) {
		this.projid = projid;
	}
	public BigDecimal getProject_area() {
		return project_area;
	}
	public void setProject_area(BigDecimal project_area) {
		this.project_area = project_area;
	}
	public BigDecimal getTreatable_area() {
		return treatable_area;
	}
	public void setTreatable_area(BigDecimal treatable_area) {
		this.treatable_area = treatable_area;
	}
	public BigDecimal getCultivable_wasteland() {
		return cultivable_wasteland;
	}
	public void setCultivable_wasteland(BigDecimal cultivable_wasteland) {
		this.cultivable_wasteland = cultivable_wasteland;
	}
	public BigDecimal getCultivable_wasteland_achv() {
		return cultivable_wasteland_achv;
	}
	public void setCultivable_wasteland_achv(BigDecimal cultivable_wasteland_achv) {
		this.cultivable_wasteland_achv = cultivable_wasteland_achv;
	}
	public BigDecimal getDegraded_land() {
		return degraded_land;
	}
	public void setDegraded_land(BigDecimal degraded_land) {
		this.degraded_land = degraded_land;
	}
	public BigDecimal getDegraded_land_achv() {
		return degraded_land_achv;
	}
	public void setDegraded_land_achv(BigDecimal degraded_land_achv) {
		this.degraded_land_achv = degraded_land_achv;
	}
	public BigDecimal getRainfed() {
		return rainfed;
	}
	public void setRainfed(BigDecimal rainfed) {
		this.rainfed = rainfed;
	}
	public BigDecimal getRainfed_achv() {
		return rainfed_achv;
	}
	public void setRainfed_achv(BigDecimal rainfed_achv) {
		this.rainfed_achv = rainfed_achv;
	}
	public BigDecimal getForest_land() {
		return forest_land;
	}
	public void setForest_land(BigDecimal forest_land) {
		this.forest_land = forest_land;
	}
	public BigDecimal getForest_land_achv() {
		return forest_land_achv;
	}
	public void setForest_land_achv(BigDecimal forest_land_achv) {
		this.forest_land_achv = forest_land_achv;
	}
	public BigDecimal getOthers() {
		return others;
	}
	public void setOthers(BigDecimal others) {
		this.others = others;
	}
	public BigDecimal getOthers_achv() {
		return others_achv;
	}
	public void setOthers_achv(BigDecimal others_achv) {
		this.others_achv = others_achv;
	}
	public BigDecimal getGross_cropped_area() {
		return gross_cropped_area;
	}
	public void setGross_cropped_area(BigDecimal gross_cropped_area) {
		this.gross_cropped_area = gross_cropped_area;
	}
	public BigDecimal getGross_cropped_area_achv() {
		return gross_cropped_area_achv;
	}
	public void setGross_cropped_area_achv(BigDecimal gross_cropped_area_achv) {
		this.gross_cropped_area_achv = gross_cropped_area_achv;
	}
	public BigDecimal getTotal_income() {
		return total_income;
	}
	public void setTotal_income(BigDecimal total_income) {
		this.total_income = total_income;
	}
	public BigDecimal getTotal_income_achv() {
		return total_income_achv;
	}
	public void setTotal_income_achv(BigDecimal total_income_achv) {
		this.total_income_achv = total_income_achv;
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
	public BigDecimal getNo_crop_achv() {
		return no_crop_achv;
	}
	public void setNo_crop_achv(BigDecimal no_crop_achv) {
		this.no_crop_achv = no_crop_achv;
	}
	public BigDecimal getSingle_crop_achv() {
		return single_crop_achv;
	}
	public void setSingle_crop_achv(BigDecimal single_crop_achv) {
		this.single_crop_achv = single_crop_achv;
	}
	public BigDecimal getDouble_crop_achv() {
		return double_crop_achv;
	}
	public void setDouble_crop_achv(BigDecimal double_crop_achv) {
		this.double_crop_achv = double_crop_achv;
	}
	public BigDecimal getMultiple_crop_achv() {
		return multiple_crop_achv;
	}
	public void setMultiple_crop_achv(BigDecimal multiple_crop_achv) {
		this.multiple_crop_achv = multiple_crop_achv;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public Integer getFinyr() {
		return finyr;
	}
	public void setFinyr(Integer finyr) {
		this.finyr = finyr;
	}
	public Integer getStartmonth() {
		return startmonth;
	}
	public void setStartmonth(Integer startmonth) {
		this.startmonth = startmonth;
	}
	public Integer getEndmonth() {
		return endmonth;
	}
	public void setEndmonth(Integer endmonth) {
		this.endmonth = endmonth;
	}
	public Integer getProjectcd() {
		return projectcd;
	}
	public void setProjectcd(Integer projectcd) {
		this.projectcd = projectcd;
	}
	public String getProjectdesc() {
		return projectdesc;
	}
	public void setProjectdesc(String projectdesc) {
		this.projectdesc = projectdesc;
	}
	public Integer getMonthid() {
		return monthid;
	}
	public void setMonthid(Integer monthid) {
		this.monthid = monthid;
	}
	public String getMonthdesc() {
		return monthdesc;
	}
	public void setMonthdesc(String monthdesc) {
		this.monthdesc = monthdesc;
	}
	public Integer getFinyrcd() {
		return finyrcd;
	}
	public void setFinyrcd(Integer finyrcd) {
		this.finyrcd = finyrcd;
	}
	public String getFinyrdesc() {
		return finyrdesc;
	}
	public void setFinyrdesc(String finyrdesc) {
		this.finyrdesc = finyrdesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCurrentlyat() {
		return currentlyat;
	}
	public void setCurrentlyat(String currentlyat) {
		this.currentlyat = currentlyat;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getAchievementid() {
		return achievementid;
	}
	public void setAchievementid(Integer achievementid) {
		this.achievementid = achievementid;
	}
	public Character getAction() {
		return action;
	}
	public void setAction(Character action) {
		this.action = action;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public Boolean getShow() {
		return show;
	}
	public void setShow(Boolean show) {
		this.show = show;
	}
	public String getSentfrom() {
		return sentfrom;
	}
	public void setSentfrom(String sentfrom) {
		this.sentfrom = sentfrom;
	}
	public String getSentto() {
		return sentto;
	}
	public void setSentto(String sentto) {
		this.sentto = sentto;
	}
	public Date getSenton() {
		return senton;
	}
	public void setSenton(Date senton) {
		this.senton = senton;
	}
	public String getFin_yr_desc() {
		return fin_yr_desc;
	}
	public void setFin_yr_desc(String fin_yr_desc) {
		this.fin_yr_desc = fin_yr_desc;
	}
	
	
	
	
	
	
	

}
