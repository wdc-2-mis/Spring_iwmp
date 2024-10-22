package app.bean;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class BaseLineSurveyBean {
	
	private Integer bls_id;
	private Integer proj_id;
	private String proj_name;
	private BigDecimal total_geo_area;
	private String area_covering_type;
	private BigDecimal total_gross_cropped_area;
	private BigDecimal total_net_sown_area;
	private Integer total_sc;
	private Integer total_st;
	private Integer total_others;
	private Integer total_population;
	private Integer no_of_landless_household;
	private Integer no_of_bpl_household;
	private Integer small_farmer_household;
	
	
	private Integer marginal_farmer_household;
	private Integer person_days_migration;
	private Character status;
	private Integer bls_activity_details_id;
	private BigDecimal area_of_activity;
	private Integer outcome_id;
	private String outcome_desc;
	private Integer outcome_detail_id;
	private String outcome_detail_desc;
	private Integer phy_head;
	private String head_desc;
	private Integer phy_activity;
	private String activity_desc;
	
	private String userType;
	
	private Integer dist_code;
	private String dist_name;
	private String stname;
	private Integer stcode;
	private BigDecimal sanctioned_area;
	private BigDecimal treatable_area;
	private BigDecimal area_covered_under_diversified_crops;
	private BigDecimal area_brought_from_nil_single;
	private BigDecimal area_brought_under_afforestation;
	private BigDecimal area_brought_under_horticulture;
	private BigDecimal water_harvesting_structure;
	private BigDecimal area_brought_under_protective_irrigation;
	private BigDecimal area_covered_with_soil_and_moisture_conservation;
	private Integer no_of_household;
	private Integer totalproject;
	public Integer getPopulation_marginal_farmers() {
		return population_marginal_farmers;
	}
	public void setPopulation_marginal_farmers(Integer population_marginal_farmers) {
		this.population_marginal_farmers = population_marginal_farmers;
	}
	private Integer household_total;
	private Integer population_sc;
	private Integer population_st;
	private Integer population_other;
	private Integer population_total;
	private Integer population_landless_people;
	private Integer population_bpl;
	private Integer population_small_farmers;
	private Integer whs_farm_pond;
	private Integer whs_check_dam;
	private Integer whs_nallah_bund;
	private Integer whs_percolation_tank;
	private Integer whs_gwrs;
	private Integer whs_gully_plug;
	private Integer whs_other;
	private Integer pi_farm_pond;
	private Integer pi_check_dam;
	private Integer pi_nallah_bund;
	private Integer pi_other;
	
	private Integer population_marginal_farmers;
	
	


	public Integer getHousehold_total() {
		return household_total;
	}
	public void setHousehold_total(Integer household_total) {
		this.household_total = household_total;
	}
	public Integer getPopulation_sc() {
		return population_sc;
	}
	public void setPopulation_sc(Integer population_sc) {
		this.population_sc = population_sc;
	}
	public Integer getPopulation_st() {
		return population_st;
	}
	public void setPopulation_st(Integer population_st) {
		this.population_st = population_st;
	}
	public Integer getPopulation_other() {
		return population_other;
	}
	public void setPopulation_other(Integer population_other) {
		this.population_other = population_other;
	}
	public Integer getPopulation_total() {
		return population_total;
	}
	public void setPopulation_total(Integer population_total) {
		this.population_total = population_total;
	}
	public Integer getPopulation_landless_people() {
		return population_landless_people;
	}
	public void setPopulation_landless_people(Integer population_landless_people) {
		this.population_landless_people = population_landless_people;
	}
	public Integer getPopulation_bpl() {
		return population_bpl;
	}
	public void setPopulation_bpl(Integer population_bpl) {
		this.population_bpl = population_bpl;
	}
	public Integer getPopulation_small_farmers() {
		return population_small_farmers;
	}
	public void setPopulation_small_farmers(Integer population_small_farmers) {
		this.population_small_farmers = population_small_farmers;
	}
	public Integer getWhs_farm_pond() {
		return whs_farm_pond;
	}
	public void setWhs_farm_pond(Integer whs_farm_pond) {
		this.whs_farm_pond = whs_farm_pond;
	}
	public Integer getWhs_check_dam() {
		return whs_check_dam;
	}
	public void setWhs_check_dam(Integer whs_check_dam) {
		this.whs_check_dam = whs_check_dam;
	}
	public Integer getWhs_nallah_bund() {
		return whs_nallah_bund;
	}
	public void setWhs_nallah_bund(Integer whs_nallah_bund) {
		this.whs_nallah_bund = whs_nallah_bund;
	}
	public Integer getWhs_percolation_tank() {
		return whs_percolation_tank;
	}
	public void setWhs_percolation_tank(Integer whs_percolation_tank) {
		this.whs_percolation_tank = whs_percolation_tank;
	}
	public Integer getWhs_gwrs() {
		return whs_gwrs;
	}
	public void setWhs_gwrs(Integer whs_gwrs) {
		this.whs_gwrs = whs_gwrs;
	}
	public Integer getWhs_gully_plug() {
		return whs_gully_plug;
	}
	public void setWhs_gully_plug(Integer whs_gully_plug) {
		this.whs_gully_plug = whs_gully_plug;
	}
	public Integer getWhs_other() {
		return whs_other;
	}
	public void setWhs_other(Integer whs_other) {
		this.whs_other = whs_other;
	}
	public Integer getPi_farm_pond() {
		return pi_farm_pond;
	}
	public void setPi_farm_pond(Integer pi_farm_pond) {
		this.pi_farm_pond = pi_farm_pond;
	}
	public Integer getPi_check_dam() {
		return pi_check_dam;
	}
	public void setPi_check_dam(Integer pi_check_dam) {
		this.pi_check_dam = pi_check_dam;
	}
	public Integer getPi_nallah_bund() {
		return pi_nallah_bund;
	}
	public void setPi_nallah_bund(Integer pi_nallah_bund) {
		this.pi_nallah_bund = pi_nallah_bund;
	}
	public Integer getPi_other() {
		return pi_other;
	}
	public void setPi_other(Integer pi_other) {
		this.pi_other = pi_other;
	}
	public Integer getBls_id() {
		return bls_id;
	}
	public void setBls_id(Integer bls_id) {
		this.bls_id = bls_id;
	}
	public Integer getProj_id() {
		return proj_id;
	}
	public void setProj_id(Integer proj_id) {
		this.proj_id = proj_id;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public BigDecimal getTotal_geo_area() {
		return total_geo_area;
	}
	public void setTotal_geo_area(BigDecimal total_geo_area) {
		this.total_geo_area = total_geo_area;
	}
	public String getArea_covering_type() {
		return area_covering_type;
	}
	public void setArea_covering_type(String area_covering_type) {
		this.area_covering_type = area_covering_type;
	}
	public BigDecimal getTotal_gross_cropped_area() {
		return total_gross_cropped_area;
	}
	public void setTotal_gross_cropped_area(BigDecimal total_gross_cropped_area) {
		this.total_gross_cropped_area = total_gross_cropped_area;
	}
	public BigDecimal getTotal_net_sown_area() {
		return total_net_sown_area;
	}
	public void setTotal_net_sown_area(BigDecimal total_net_sown_area) {
		this.total_net_sown_area = total_net_sown_area;
	}
	public Integer getTotal_sc() {
		return total_sc;
	}
	public void setTotal_sc(Integer total_sc) {
		this.total_sc = total_sc;
	}
	public Integer getTotal_st() {
		return total_st;
	}
	public void setTotal_st(Integer total_st) {
		this.total_st = total_st;
	}
	public Integer getTotal_others() {
		return total_others;
	}
	public void setTotal_others(Integer total_others) {
		this.total_others = total_others;
	}
	public Integer getTotal_population() {
		return total_population;
	}
	public void setTotal_population(Integer total_population) {
		this.total_population = total_population;
	}
	public Integer getNo_of_landless_household() {
		return no_of_landless_household;
	}
	public void setNo_of_landless_household(Integer no_of_landless_household) {
		this.no_of_landless_household = no_of_landless_household;
	}
	public Integer getNo_of_bpl_household() {
		return no_of_bpl_household;
	}
	public void setNo_of_bpl_household(Integer no_of_bpl_household) {
		this.no_of_bpl_household = no_of_bpl_household;
	}
	public Integer getSmall_farmer_household() {
		return small_farmer_household;
	}
	public void setSmall_farmer_household(Integer small_farmer_household) {
		this.small_farmer_household = small_farmer_household;
	}
	public Integer getMarginal_farmer_household() {
		return marginal_farmer_household;
	}
	public void setMarginal_farmer_household(Integer marginal_farmer_household) {
		this.marginal_farmer_household = marginal_farmer_household;
	}
	public Integer getPerson_days_migration() {
		return person_days_migration;
	}
	public void setPerson_days_migration(Integer person_days_migration) {
		this.person_days_migration = person_days_migration;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public Integer getBls_activity_details_id() {
		return bls_activity_details_id;
	}
	public void setBls_activity_details_id(Integer bls_activity_details_id) {
		this.bls_activity_details_id = bls_activity_details_id;
	}
	public BigDecimal getArea_of_activity() {
		return area_of_activity;
	}
	public void setArea_of_activity(BigDecimal area_of_activity) {
		this.area_of_activity = area_of_activity;
	}
	public Integer getOutcome_id() {
		return outcome_id;
	}
	public void setOutcome_id(Integer outcome_id) {
		this.outcome_id = outcome_id;
	}
	public String getOutcome_desc() {
		return outcome_desc;
	}
	public void setOutcome_desc(String outcome_desc) {
		this.outcome_desc = outcome_desc;
	}
	public Integer getOutcome_detail_id() {
		return outcome_detail_id;
	}
	public void setOutcome_detail_id(Integer outcome_detail_id) {
		this.outcome_detail_id = outcome_detail_id;
	}
	public String getOutcome_detail_desc() {
		return outcome_detail_desc;
	}
	public void setOutcome_detail_desc(String outcome_detail_desc) {
		this.outcome_detail_desc = outcome_detail_desc;
	}
	public Integer getPhy_head() {
		return phy_head;
	}
	public void setPhy_head(Integer phy_head) {
		this.phy_head = phy_head;
	}
	public String getHead_desc() {
		return head_desc;
	}
	public void setHead_desc(String head_desc) {
		this.head_desc = head_desc;
	}
	public Integer getPhy_activity() {
		return phy_activity;
	}
	public void setPhy_activity(Integer phy_activity) {
		this.phy_activity = phy_activity;
	}
	public String getActivity_desc() {
		return activity_desc;
	}
	public void setActivity_desc(String activity_desc) {
		this.activity_desc = activity_desc;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getDist_code() {
		return dist_code;
	}
	public void setDist_code(Integer dist_code) {
		this.dist_code = dist_code;
	}
	public String getDist_name() {
		return dist_name;
	}
	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}
	public BigDecimal getSanctioned_area() {
		return sanctioned_area;
	}
	public void setSanctioned_area(BigDecimal sanctioned_area) {
		this.sanctioned_area = sanctioned_area;
	}
	public BigDecimal getTreatable_area() {
		return treatable_area;
	}
	public void setTreatable_area(BigDecimal treatable_area) {
		this.treatable_area = treatable_area;
	}
	public BigDecimal getArea_covered_under_diversified_crops() {
		return area_covered_under_diversified_crops;
	}
	public void setArea_covered_under_diversified_crops(BigDecimal area_covered_under_diversified_crops) {
		this.area_covered_under_diversified_crops = area_covered_under_diversified_crops;
	}
	public BigDecimal getArea_brought_from_nil_single() {
		return area_brought_from_nil_single;
	}
	public void setArea_brought_from_nil_single(BigDecimal area_brought_from_nil_single) {
		this.area_brought_from_nil_single = area_brought_from_nil_single;
	}
	public BigDecimal getArea_brought_under_afforestation() {
		return area_brought_under_afforestation;
	}
	public void setArea_brought_under_afforestation(BigDecimal area_brought_under_afforestation) {
		this.area_brought_under_afforestation = area_brought_under_afforestation;
	}
	public BigDecimal getArea_brought_under_horticulture() {
		return area_brought_under_horticulture;
	}
	public void setArea_brought_under_horticulture(BigDecimal area_brought_under_horticulture) {
		this.area_brought_under_horticulture = area_brought_under_horticulture;
	}
	public BigDecimal getWater_harvesting_structure() {
		return water_harvesting_structure;
	}
	public void setWater_harvesting_structure(BigDecimal water_harvesting_structure) {
		this.water_harvesting_structure = water_harvesting_structure;
	}
	public BigDecimal getArea_brought_under_protective_irrigation() {
		return area_brought_under_protective_irrigation;
	}
	public void setArea_brought_under_protective_irrigation(BigDecimal area_brought_under_protective_irrigation) {
		this.area_brought_under_protective_irrigation = area_brought_under_protective_irrigation;
	}
	public BigDecimal getArea_covered_with_soil_and_moisture_conservation() {
		return area_covered_with_soil_and_moisture_conservation;
	}
	public void setArea_covered_with_soil_and_moisture_conservation(
			BigDecimal area_covered_with_soil_and_moisture_conservation) {
		this.area_covered_with_soil_and_moisture_conservation = area_covered_with_soil_and_moisture_conservation;
	}
	public Integer getNo_of_household() {
		return no_of_household;
	}
	public void setNo_of_household(Integer no_of_household) {
		this.no_of_household = no_of_household;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public Integer getStcode() {
		return stcode;
	}
	public void setStcode(Integer stcode) {
		this.stcode = stcode;
	}
	public Integer getTotalproject() {
		return totalproject;
	}
	public void setTotalproject(Integer totalproject) {
		this.totalproject = totalproject;
	}
	
	
	

}
