package app.bean.reports;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DolrDashboardBean {
	
	int stateCode;
	int statecode;
	int finyear;
	Integer projcount;
	BigDecimal prcntge_area;
	BigDecimal proj_loc_prcntge;
	String stname;
	int st_code;
	BigDecimal headerdesc;
	String dist_name;
	BigDecimal cultivable_land_prcntg;
	BigDecimal degraded_land_prcntg;
	BigDecimal rainfed_land_prcntg;
	BigDecimal forest_land_prcntg;
	BigDecimal other_land_prcntg;
	BigDecimal protective_irrigation_prcntg;
	BigDecimal assured_irrigation_prcntg;
	BigDecimal no_irrigation_prcntg;
	BigDecimal no_crop;
	BigDecimal single_crop;
	BigDecimal double_crop;
	BigDecimal multiple_crop;
	
	BigDecimal cultivable_land_prcntg_ach;
	BigDecimal degraded_land_prcntg_ach;
	BigDecimal rainfed_land_prcntg_ach;
	BigDecimal forest_land_prcntg_ach;
	BigDecimal other_land_prcntg_ach;
	BigDecimal protective_irrigation_prcntg_ach;
	BigDecimal assured_irrigation_prcntg_ach;
	BigDecimal no_irrigation_prcntg_ach;
	BigDecimal no_crop_ach;
	BigDecimal single_crop_ach;
	BigDecimal double_crop_ach;
	BigDecimal multiple_crop_ach;
	
	BigDecimal total_expen_prcnt;
	BigDecimal treasury_receipt_prcnt;
	BigDecimal sanctioned_amt;
	BigDecimal total_expen;
	BigDecimal treasury_receipt;
	BigDecimal dashboardtardec;
	private String state;
	private Integer dashboardtar;
	private String headdesc;
	public BigDecimal getHeaderdesc() {
		return headerdesc;
	}
	public void setHeaderdesc(BigDecimal headerdesc) {
		this.headerdesc = headerdesc;
	}
	public int getStatecode() {
		return statecode;
	}
	public void setStatecode(int statecode) {
		this.statecode = statecode;
	}
	public int getStateCode() {
		return stateCode;
	}
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public int getFinyear() {
		return finyear;
	}
	public void setFinyear(int finyear) {
		this.finyear = finyear;
	}
	
	public Integer getProjcount() {
		return projcount;
	}
	public void setProjcount(Integer projcount) {
		this.projcount = projcount;
	}
	public BigDecimal getPrcntge_area() {
		return prcntge_area;
	}
	public void setPrcntge_area(BigDecimal prcntge_area) {
		this.prcntge_area = prcntge_area;
	}
	public BigDecimal getProj_loc_prcntge() {
		return proj_loc_prcntge;
	}
	public void setProj_loc_prcntge(BigDecimal proj_loc_prcntge) {
		this.proj_loc_prcntge = proj_loc_prcntge;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public BigDecimal getCultivable_land_prcntg() {
		return cultivable_land_prcntg;
	}
	public void setCultivable_land_prcntg(BigDecimal cultivable_land_prcntg) {
		this.cultivable_land_prcntg = cultivable_land_prcntg;
	}
	public BigDecimal getDegraded_land_prcntg() {
		return degraded_land_prcntg;
	}
	public void setDegraded_land_prcntg(BigDecimal degraded_land_prcntg) {
		this.degraded_land_prcntg = degraded_land_prcntg;
	}
	public BigDecimal getRainfed_land_prcntg() {
		return rainfed_land_prcntg;
	}
	public void setRainfed_land_prcntg(BigDecimal rainfed_land_prcntg) {
		this.rainfed_land_prcntg = rainfed_land_prcntg;
	}
	public BigDecimal getForest_land_prcntg() {
		return forest_land_prcntg;
	}
	public void setForest_land_prcntg(BigDecimal forest_land_prcntg) {
		this.forest_land_prcntg = forest_land_prcntg;
	}
	public BigDecimal getOther_land_prcntg() {
		return other_land_prcntg;
	}
	public void setOther_land_prcntg(BigDecimal other_land_prcntg) {
		this.other_land_prcntg = other_land_prcntg;
	}
	public BigDecimal getProtective_irrigation_prcntg() {
		return protective_irrigation_prcntg;
	}
	public void setProtective_irrigation_prcntg(BigDecimal protective_irrigation_prcntg) {
		this.protective_irrigation_prcntg = protective_irrigation_prcntg;
	}
	public BigDecimal getAssured_irrigation_prcntg() {
		return assured_irrigation_prcntg;
	}
	public void setAssured_irrigation_prcntg(BigDecimal assured_irrigation_prcntg) {
		this.assured_irrigation_prcntg = assured_irrigation_prcntg;
	}
	public BigDecimal getNo_irrigation_prcntg() {
		return no_irrigation_prcntg;
	}
	public void setNo_irrigation_prcntg(BigDecimal no_irrigation_prcntg) {
		this.no_irrigation_prcntg = no_irrigation_prcntg;
	}
	public BigDecimal getCultivable_land_prcntg_ach() {
		return cultivable_land_prcntg_ach;
	}
	public void setCultivable_land_prcntg_ach(BigDecimal cultivable_land_prcntg_ach) {
		this.cultivable_land_prcntg_ach = cultivable_land_prcntg_ach;
	}
	public BigDecimal getDegraded_land_prcntg_ach() {
		return degraded_land_prcntg_ach;
	}
	public void setDegraded_land_prcntg_ach(BigDecimal degraded_land_prcntg_ach) {
		this.degraded_land_prcntg_ach = degraded_land_prcntg_ach;
	}
	public BigDecimal getRainfed_land_prcntg_ach() {
		return rainfed_land_prcntg_ach;
	}
	public void setRainfed_land_prcntg_ach(BigDecimal rainfed_land_prcntg_ach) {
		this.rainfed_land_prcntg_ach = rainfed_land_prcntg_ach;
	}
	public BigDecimal getForest_land_prcntg_ach() {
		return forest_land_prcntg_ach;
	}
	public void setForest_land_prcntg_ach(BigDecimal forest_land_prcntg_ach) {
		this.forest_land_prcntg_ach = forest_land_prcntg_ach;
	}
	public BigDecimal getOther_land_prcntg_ach() {
		return other_land_prcntg_ach;
	}
	public void setOther_land_prcntg_ach(BigDecimal other_land_prcntg_ach) {
		this.other_land_prcntg_ach = other_land_prcntg_ach;
	}
	public BigDecimal getProtective_irrigation_prcntg_ach() {
		return protective_irrigation_prcntg_ach;
	}
	public void setProtective_irrigation_prcntg_ach(BigDecimal protective_irrigation_prcntg_ach) {
		this.protective_irrigation_prcntg_ach = protective_irrigation_prcntg_ach;
	}
	public BigDecimal getAssured_irrigation_prcntg_ach() {
		return assured_irrigation_prcntg_ach;
	}
	public void setAssured_irrigation_prcntg_ach(BigDecimal assured_irrigation_prcntg_ach) {
		this.assured_irrigation_prcntg_ach = assured_irrigation_prcntg_ach;
	}
	public BigDecimal getNo_irrigation_prcntg_ach() {
		return no_irrigation_prcntg_ach;
	}
	public void setNo_irrigation_prcntg_ach(BigDecimal no_irrigation_prcntg_ach) {
		this.no_irrigation_prcntg_ach = no_irrigation_prcntg_ach;
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
	public BigDecimal getTotal_expen_prcnt() {
		return total_expen_prcnt;
	}
	public void setTotal_expen_prcnt(BigDecimal total_expen_prcnt) {
		this.total_expen_prcnt = total_expen_prcnt;
	}
	public BigDecimal getTreasury_receipt_prcnt() {
		return treasury_receipt_prcnt;
	}
	public void setTreasury_receipt_prcnt(BigDecimal treasury_receipt_prcnt) {
		this.treasury_receipt_prcnt = treasury_receipt_prcnt;
	}
	public BigDecimal getSanctioned_amt() {
		return sanctioned_amt;
	}
	public void setSanctioned_amt(BigDecimal sanctioned_amt) {
		this.sanctioned_amt = sanctioned_amt;
	}
	public BigDecimal getTotal_expen() {
		return total_expen;
	}
	public void setTotal_expen(BigDecimal total_expen) {
		this.total_expen = total_expen;
	}
	public BigDecimal getTreasury_receipt() {
		return treasury_receipt;
	}
	public void setTreasury_receipt(BigDecimal treasury_receipt) {
		this.treasury_receipt = treasury_receipt;
	}
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getDashboardtar() {
		return dashboardtar;
	}
	public void setDashboardtar(Integer dashboardtar) {
		this.dashboardtar = dashboardtar;
	}
	
	
	public String getHeaddesc() {
		return headdesc;
	}
	public void setHeaddesc(String headdesc) {
		this.headdesc = headdesc;
	}
	
	
	
	public int getSt_code() {
		return st_code;
	}
	public void setSt_code(int st_code) {
		this.st_code = st_code;
	}
	
	public BigDecimal getDashboardtardec() {
		return dashboardtardec;
	}
	public void setDashboardtardec(BigDecimal dashboardtardec) {
		this.dashboardtardec = dashboardtardec;
	}
	
	public String getDist_name() {
		return dist_name;
	}
	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}
	@Override
	public String toString() {
		return "DolrDashboardBean [stateCode=" + stateCode + ", statecode=" + statecode + ", finyear=" + finyear
				+ ", projcount=" + projcount + ", prcntge_area=" + prcntge_area + ", proj_loc_prcntge="
				+ proj_loc_prcntge + ", stname=" + stname + ", cultivable_land_prcntg=" + cultivable_land_prcntg
				+ ", degraded_land_prcntg=" + degraded_land_prcntg + ", rainfed_land_prcntg=" + rainfed_land_prcntg
				+ ", forest_land_prcntg=" + forest_land_prcntg + ", other_land_prcntg=" + other_land_prcntg
				+ ", protective_irrigation_prcntg=" + protective_irrigation_prcntg + ", assured_irrigation_prcntg="
				+ assured_irrigation_prcntg + ", no_irrigation_prcntg=" + no_irrigation_prcntg + ", no_crop=" + no_crop
				+ ", single_crop=" + single_crop + ", double_crop=" + double_crop + ", multiple_crop=" + multiple_crop
				+ ", cultivable_land_prcntg_ach=" + cultivable_land_prcntg_ach + ", degraded_land_prcntg_ach="
				+ degraded_land_prcntg_ach + ", rainfed_land_prcntg_ach=" + rainfed_land_prcntg_ach
				+ ", forest_land_prcntg_ach=" + forest_land_prcntg_ach + ", other_land_prcntg_ach="
				+ other_land_prcntg_ach + ", protective_irrigation_prcntg_ach=" + protective_irrigation_prcntg_ach
				+ ", assured_irrigation_prcntg_ach=" + assured_irrigation_prcntg_ach + ", no_irrigation_prcntg_ach="
				+ no_irrigation_prcntg_ach + ", no_crop_ach=" + no_crop_ach + ", single_crop_ach=" + single_crop_ach
				+ ", double_crop_ach=" + double_crop_ach + ", multiple_crop_ach=" + multiple_crop_ach
				+ ", total_expen_prcnt=" + total_expen_prcnt + ", treasury_receipt_prcnt=" + treasury_receipt_prcnt
				+ ", sanctioned_amt=" + sanctioned_amt + ", total_expen=" + total_expen + ", treasury_receipt="
				+ treasury_receipt + ", dashboardtardec=" + dashboardtardec + ", state=" + state + ", dashboardtar="
				+ dashboardtar + ", headdesc=" + headdesc + "]";
	}
	

	
}
