package app.projectevaluation.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ProjectEvaluationBean {

	
	private String st_name;
	private Integer st_code;
	private Integer dcode;
	private Integer district;
	private Integer project;
	private Integer block;
	private Integer gp;
	private Integer village;
	private Integer completed;
	private Integer process;
	private Integer not_entered;
	private Integer total_dist;
	private Integer total_project;
	
	private BigDecimal sanctioned_cost;
	private BigDecimal central;
	private BigDecimal state;
	private BigDecimal sanctioned_area;
	private Integer no_wc;
	private Integer no_vc;
	private Integer monthid;
	private String monthname;
	private Integer fin_cd;
	private String fin_yr;
	private Integer member_wc;
	private Integer household;
	private Integer pro_profileid;
	private Integer projcount;
	private String projname;
    private Character project_controlled;
	private BigDecimal culturable_wasteland;
	private Integer whs_constructed_rejuvenated;
	private BigDecimal soil_moisture;
	private BigDecimal protective_irrigation;
	private BigDecimal degraded_rainfed;
	private BigDecimal farmer_income;
	private Integer farmer_benefited;
	private Integer mandays_generated;
	private BigDecimal dug_well;
	private BigDecimal tube_well;
	
	private BigDecimal control_culturable_wasteland;
	private Integer control_whs_constructed_rejuvenated;
	private BigDecimal control_soil_moisture;
	private BigDecimal control_protective_irrigation;
	private BigDecimal control_degraded_rainfed;
	private BigDecimal control_farmer_income;
	private Integer control_farmer_benefited;
	private Integer control_mandays_generated;
	private BigDecimal control_dug_well;
	private BigDecimal control_tube_well;
	
	private Integer created_work;
	private String created_work_remark;
	private Integer completed_work;
	private String completed_work_remark;
	private Integer ongoing_work;
	private String ongoing_work_remark;
	private BigDecimal shape_file_area;
	private String shape_file_area_remark;
	private BigDecimal variation_area;
	private String variation_area_remark;
	
	private Integer geo_tagg_work;
	private String geo_tagg_work_remark;

	private BigDecimal central_share;
	private String central_share_remark;
	private BigDecimal state_share;
	private String state_share_remark;
	private BigDecimal total_fund;
	private String total_fund_remark;
	private BigDecimal total_fund_planned;
	private String total_fund_planned_remark;
	private BigDecimal total_expenditure;
	private String total_expenditure_remark;
	private BigDecimal total_wdf;
	private String total_wdf_remark;
		
	private String admin_mechanism;
	private String admin_mechanism_remark;
	private Character dpr_slna;
	private String dpr_slna_remark;
	private Character all_manpower;
	private String all_manpower_remark;
	private String wcdc_remark;
	private String pia_remark;
	private String wc_remark;

	private Integer wcdc;
	private Integer pia;
	private Integer wc;
	
	private Boolean natural_resource;
	private String natural_resource_remark;
	private Boolean norms_relating;
	private String norms_relating_remark;
	private Boolean antural_asset;
	private String antural_asset_remark;
	private Boolean control_natural_resource;
	private Boolean control_norms_relating;
	private Boolean control_antural_asset;
    private String distname;
    private Character status;
    
    private BigDecimal pre_farmer_income;
	private BigDecimal mid_farmer_income;
	private String remark_farmer_income;
    private String remark_farmer_benefited; 
    private String remark_mandays_generated;
    
    private BigDecimal pre_dug_well;
    private  BigDecimal mid_dug_well;
    private String remark_dug_well;
    
    private BigDecimal pre_tube_well;
    private BigDecimal mid_tube_well;
    private String remark_tube_well;
    private String summary;
    private Character grade;
    
	private BigInteger workcompleted;
    private BigInteger workongoing;
    private BigInteger createdwork;
    private BigInteger geotag;
    
    private BigDecimal pre_kharif;
    private BigDecimal mid_kharif;
    private BigDecimal ctl_kharif;
    private BigDecimal pre_rabi;
    private BigDecimal mid_rabi;
    private BigDecimal ctl_rabi;
    private BigDecimal pre_thrdcrp;
    private BigDecimal mid_thrdcrp;
    private BigDecimal ctl_thrdcrp;
    private BigDecimal pre_total;
    private BigDecimal mid_total;
    private BigDecimal ctl_total;
    private BigDecimal pre_plt;
    private BigDecimal mid_plt;
    private BigDecimal ctl_plt;
    private BigDecimal pre_clt;
    private BigDecimal mid_clt;
    private BigDecimal ctl_clt;
    
	public BigInteger getWorkcompleted() {
		return workcompleted;
	}
	public void setWorkcompleted(BigInteger workcompleted) {
		this.workcompleted = workcompleted;
	}
	public BigInteger getWorkongoing() {
		return workongoing;
	}
	public void setWorkongoing(BigInteger workongoing) {
		this.workongoing = workongoing;
	}
	public BigInteger getCreatedwork() {
		return createdwork;
	}
	public void setCreatedwork(BigInteger createdwork) {
		this.createdwork = createdwork;
	}
	public BigInteger getGeotag() {
		return geotag;
	}
	public void setGeotag(BigInteger geotag) {
		this.geotag = geotag;
	}
	public BigDecimal getPre_farmer_income() {
		return pre_farmer_income;
	}
	public void setPre_farmer_income(BigDecimal pre_farmer_income) {
		this.pre_farmer_income = pre_farmer_income;
	}
	public BigDecimal getMid_farmer_income() {
		return mid_farmer_income;
	}
	public void setMid_farmer_income(BigDecimal mid_farmer_income) {
		this.mid_farmer_income = mid_farmer_income;
	}
	public String getRemark_farmer_income() {
		return remark_farmer_income;
	}
	public void setRemark_farmer_income(String remark_farmer_income) {
		this.remark_farmer_income = remark_farmer_income;
	}
	public String getRemark_farmer_benefited() {
		return remark_farmer_benefited;
	}
	public void setRemark_farmer_benefited(String remark_farmer_benefited) {
		this.remark_farmer_benefited = remark_farmer_benefited;
	}
	public String getRemark_mandays_generated() {
		return remark_mandays_generated;
	}
	public void setRemark_mandays_generated(String remark_mandays_generated) {
		this.remark_mandays_generated = remark_mandays_generated;
	}
	public BigDecimal getPre_dug_well() {
		return pre_dug_well;
	}
	public void setPre_dug_well(BigDecimal pre_dug_well) {
		this.pre_dug_well = pre_dug_well;
	}
	public BigDecimal getMid_dug_well() {
		return mid_dug_well;
	}
	public void setMid_dug_well(BigDecimal mid_dug_well) {
		this.mid_dug_well = mid_dug_well;
	}
	public String getRemark_dug_well() {
		return remark_dug_well;
	}
	public void setRemark_dug_well(String remark_dug_well) {
		this.remark_dug_well = remark_dug_well;
	}
	public BigDecimal getPre_tube_well() {
		return pre_tube_well;
	}
	public void setPre_tube_well(BigDecimal pre_tube_well) {
		this.pre_tube_well = pre_tube_well;
	}
	public BigDecimal getMid_tube_well() {
		return mid_tube_well;
	}
	public void setMid_tube_well(BigDecimal mid_tube_well) {
		this.mid_tube_well = mid_tube_well;
	}
	public String getRemark_tube_well() {
		return remark_tube_well;
	}
	public void setRemark_tube_well(String remark_tube_well) {
		this.remark_tube_well = remark_tube_well;
	}
    
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public Integer getSt_code() {
		return st_code;
	}
	public void setSt_code(Integer st_code) {
		this.st_code = st_code;
	}
	public Integer getDcode() {
		return dcode;
	}
	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
	public Integer getBlock() {
		return block;
	}
	public void setBlock(Integer block) {
		this.block = block;
	}
	public Integer getGp() {
		return gp;
	}
	public void setGp(Integer gp) {
		this.gp = gp;
	}
	public Integer getVillage() {
		return village;
	}
	public void setVillage(Integer village) {
		this.village = village;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getProcess() {
		return process;
	}
	public void setProcess(Integer process) {
		this.process = process;
	}
	public Integer getNot_entered() {
		return not_entered;
	}
	public void setNot_entered(Integer not_entered) {
		this.not_entered = not_entered;
	}
	public Integer getTotal_dist() {
		return total_dist;
	}
	public void setTotal_dist(Integer total_dist) {
		this.total_dist = total_dist;
	}
	public Integer getTotal_project() {
		return total_project;
	}
	public void setTotal_project(Integer total_project) {
		this.total_project = total_project;
	}
	
	public BigDecimal getSanctioned_cost() {
		return sanctioned_cost;
	}
	public void setSanctioned_cost(BigDecimal sanctioned_cost) {
		this.sanctioned_cost = sanctioned_cost;
	}
	public BigDecimal getCentral() {
		return central;
	}
	public void setCentral(BigDecimal central) {
		this.central = central;
	}
	public BigDecimal getState() {
		return state;
	}
	public void setState(BigDecimal state) {
		this.state = state;
	}
	public BigDecimal getSanctioned_area() {
		return sanctioned_area;
	}
	public void setSanctioned_area(BigDecimal sanctioned_area) {
		this.sanctioned_area = sanctioned_area;
	}
	public Integer getNo_wc() {
		return no_wc;
	}
	public void setNo_wc(Integer no_wc) {
		this.no_wc = no_wc;
	}
	public Integer getMonthid() {
		return monthid;
	}
	public void setMonthid(Integer monthid) {
		this.monthid = monthid;
	}
	public String getMonthname() {
		return monthname;
	}
	public void setMonthname(String monthname) {
		this.monthname = monthname;
	}
	public Integer getFin_cd() {
		return fin_cd;
	}
	public void setFin_cd(Integer fin_cd) {
		this.fin_cd = fin_cd;
	}
	public String getFin_yr() {
		return fin_yr;
	}
	public void setFin_yr(String fin_yr) {
		this.fin_yr = fin_yr;
	}
	public Integer getNo_vc() {
		return no_vc;
	}
	public void setNo_vc(Integer no_vc) {
		this.no_vc = no_vc;
	}
	public Integer getMember_wc() {
		return member_wc;
	}
	public void setMember_wc(Integer member_wc) {
		this.member_wc = member_wc;
	}
	public Integer getHousehold() {
		return household;
	}
	public void setHousehold(Integer household) {
		this.household = household;
	}
	public Integer getPro_profileid() {
		return pro_profileid;
	}
	public void setPro_profileid(Integer pro_profileid) {
		this.pro_profileid = pro_profileid;
	}
	
	public String getAdmin_mechanism() {
		return admin_mechanism;
	}
	public void setAdmin_mechanism(String admin_mechanism) {
		this.admin_mechanism = admin_mechanism;
	}
	public String getAdmin_mechanism_remark() {
		return admin_mechanism_remark;
	}
	public void setAdmin_mechanism_remark(String admin_mechanism_remark) {
		this.admin_mechanism_remark = admin_mechanism_remark;
	}
	

	public String getDpr_slna_remark() {
		return dpr_slna_remark;
	}
	public void setDpr_slna_remark(String dpr_slna_remark) {
		this.dpr_slna_remark = dpr_slna_remark;
	}
	public String getAll_manpower_remark() {
		return all_manpower_remark;
	}
	public void setAll_manpower_remark(String all_manpower_remark) {
		this.all_manpower_remark = all_manpower_remark;
	}
	public String getWcdc_remark() {
		return wcdc_remark;
	}
	public void setWcdc_remark(String wcdc_remark) {
		this.wcdc_remark = wcdc_remark;
	}
	public String getPia_remark() {
		return pia_remark;
	}
	public void setPia_remark(String pia_remark) {
		this.pia_remark = pia_remark;
	}
	public String getWc_remark() {
		return wc_remark;
	}
	public void setWc_remark(String wc_remark) {
		this.wc_remark = wc_remark;
	}
	public Integer getWcdc() {
		return wcdc;
	}
	public void setWcdc(Integer wcdc) {
		this.wcdc = wcdc;
	}
	public Integer getPia() {
		return pia;
	}
	public void setPia(Integer pia) {
		this.pia = pia;
	}
	public Integer getWc() {
		return wc;
	}
	public void setWc(Integer wc) {
		this.wc = wc;
	}
	
	
	public Character getProject_controlled() {
		return project_controlled;
	}
	public void setProject_controlled(Character project_controlled) {
		this.project_controlled = project_controlled;
	}
	public BigDecimal getCulturable_wasteland() {
		return culturable_wasteland;
	}
	public void setCulturable_wasteland(BigDecimal culturable_wasteland) {
		this.culturable_wasteland = culturable_wasteland;
	}
	public Integer getWhs_constructed_rejuvenated() {
		return whs_constructed_rejuvenated;
	}
	public void setWhs_constructed_rejuvenated(Integer whs_constructed_rejuvenated) {
		this.whs_constructed_rejuvenated = whs_constructed_rejuvenated;
	}
	public BigDecimal getSoil_moisture() {
		return soil_moisture;
	}
	public void setSoil_moisture(BigDecimal soil_moisture) {
		this.soil_moisture = soil_moisture;
	}
	public BigDecimal getProtective_irrigation() {
		return protective_irrigation;
	}
	public void setProtective_irrigation(BigDecimal protective_irrigation) {
		this.protective_irrigation = protective_irrigation;
	}
	public BigDecimal getDegraded_rainfed() {
		return degraded_rainfed;
	}
	public void setDegraded_rainfed(BigDecimal degraded_rainfed) {
		this.degraded_rainfed = degraded_rainfed;
	}
	public BigDecimal getFarmer_income() {
		return farmer_income;
	}
	public void setFarmer_income(BigDecimal farmer_income) {
		this.farmer_income = farmer_income;
	}
	public Integer getFarmer_benefited() {
		return farmer_benefited;
	}
	public void setFarmer_benefited(Integer farmer_benefited) {
		this.farmer_benefited = farmer_benefited;
	}
	public Integer getMandays_generated() {
		return mandays_generated;
	}
	public void setMandays_generated(Integer mandays_generated) {
		this.mandays_generated = mandays_generated;
	}
	public BigDecimal getDug_well() {
		return dug_well;
	}
	public void setDug_well(BigDecimal dug_well) {
		this.dug_well = dug_well;
	}
	public BigDecimal getTube_well() {
		return tube_well;
	}
	public void setTube_well(BigDecimal tube_well) {
		this.tube_well = tube_well;
	}
	public BigDecimal getControl_culturable_wasteland() {
		return control_culturable_wasteland;
	}
	public void setControl_culturable_wasteland(BigDecimal control_culturable_wasteland) {
		this.control_culturable_wasteland = control_culturable_wasteland;
	}
	public Integer getControl_whs_constructed_rejuvenated() {
		return control_whs_constructed_rejuvenated;
	}
	public void setControl_whs_constructed_rejuvenated(Integer control_whs_constructed_rejuvenated) {
		this.control_whs_constructed_rejuvenated = control_whs_constructed_rejuvenated;
	}
	public BigDecimal getControl_soil_moisture() {
		return control_soil_moisture;
	}
	public void setControl_soil_moisture(BigDecimal control_soil_moisture) {
		this.control_soil_moisture = control_soil_moisture;
	}
	public BigDecimal getControl_protective_irrigation() {
		return control_protective_irrigation;
	}
	public void setControl_protective_irrigation(BigDecimal control_protective_irrigation) {
		this.control_protective_irrigation = control_protective_irrigation;
	}
	public BigDecimal getControl_degraded_rainfed() {
		return control_degraded_rainfed;
	}
	public void setControl_degraded_rainfed(BigDecimal control_degraded_rainfed) {
		this.control_degraded_rainfed = control_degraded_rainfed;
	}
	public BigDecimal getControl_farmer_income() {
		return control_farmer_income;
	}
	public void setControl_farmer_income(BigDecimal control_farmer_income) {
		this.control_farmer_income = control_farmer_income;
	}
	public Integer getControl_farmer_benefited() {
		return control_farmer_benefited;
	}
	public void setControl_farmer_benefited(Integer control_farmer_benefited) {
		this.control_farmer_benefited = control_farmer_benefited;
	}
	public Integer getControl_mandays_generated() {
		return control_mandays_generated;
	}
	public void setControl_mandays_generated(Integer control_mandays_generated) {
		this.control_mandays_generated = control_mandays_generated;
	}
	public BigDecimal getControl_dug_well() {
		return control_dug_well;
	}
	public void setControl_dug_well(BigDecimal control_dug_well) {
		this.control_dug_well = control_dug_well;
	}
	public BigDecimal getControl_tube_well() {
		return control_tube_well;
	}
	public void setControl_tube_well(BigDecimal control_tube_well) {
		this.control_tube_well = control_tube_well;
	}
	public Integer getCreated_work() {
		return created_work;
	}
	public void setCreated_work(Integer created_work) {
		this.created_work = created_work;
	}
	public String getCreated_work_remark() {
		return created_work_remark;
	}
	public void setCreated_work_remark(String created_work_remark) {
		this.created_work_remark = created_work_remark;
	}
	public Integer getCompleted_work() {
		return completed_work;
	}
	public void setCompleted_work(Integer completed_work) {
		this.completed_work = completed_work;
	}
	public String getCompleted_work_remark() {
		return completed_work_remark;
	}
	public void setCompleted_work_remark(String completed_work_remark) {
		this.completed_work_remark = completed_work_remark;
	}
	public Integer getOngoing_work() {
		return ongoing_work;
	}
	public void setOngoing_work(Integer ongoing_work) {
		this.ongoing_work = ongoing_work;
	}
	public String getOngoing_work_remark() {
		return ongoing_work_remark;
	}
	public void setOngoing_work_remark(String ongoing_work_remark) {
		this.ongoing_work_remark = ongoing_work_remark;
	}
	public Integer getProjcount() {
		return projcount;
	}
	public void setProjcount(Integer projcount) {
		this.projcount = projcount;
	}
	public String getProjname() {
		return projname;
	}
	public void setProjname(String projname) {
		this.projname = projname;
	}
	public Character getDpr_slna() {
		return dpr_slna;
	}
	public void setDpr_slna(Character dpr_slna) {
		this.dpr_slna = dpr_slna;
	}
	public Character getAll_manpower() {
		return all_manpower;
	}
	public void setAll_manpower(Character all_manpower) {
		this.all_manpower = all_manpower;
	}
	public BigDecimal getShape_file_area() {
		return shape_file_area;
	}
	public void setShape_file_area(BigDecimal shape_file_area) {
		this.shape_file_area = shape_file_area;
	}
	public String getShape_file_area_remark() {
		return shape_file_area_remark;
	}
	public void setShape_file_area_remark(String shape_file_area_remark) {
		this.shape_file_area_remark = shape_file_area_remark;
	}
	public BigDecimal getVariation_area() {
		return variation_area;
	}
	public void setVariation_area(BigDecimal variation_area) {
		this.variation_area = variation_area;
	}
	public String getVariation_area_remark() {
		return variation_area_remark;
	}
	public void setVariation_area_remark(String variation_area_remark) {
		this.variation_area_remark = variation_area_remark;
	}
	public Integer getGeo_tagg_work() {
		return geo_tagg_work;
	}
	public void setGeo_tagg_work(Integer geo_tagg_work) {
		this.geo_tagg_work = geo_tagg_work;
	}
	public String getGeo_tagg_work_remark() {
		return geo_tagg_work_remark;
	}
	public void setGeo_tagg_work_remark(String geo_tagg_work_remark) {
		this.geo_tagg_work_remark = geo_tagg_work_remark;
	}
	public BigDecimal getCentral_share() {
		return central_share;
	}
	public void setCentral_share(BigDecimal central_share) {
		this.central_share = central_share;
	}
	public String getCentral_share_remark() {
		return central_share_remark;
	}
	public void setCentral_share_remark(String central_share_remark) {
		this.central_share_remark = central_share_remark;
	}
	public BigDecimal getState_share() {
		return state_share;
	}
	public void setState_share(BigDecimal state_share) {
		this.state_share = state_share;
	}
	public String getState_share_remark() {
		return state_share_remark;
	}
	public void setState_share_remark(String state_share_remark) {
		this.state_share_remark = state_share_remark;
	}
	public BigDecimal getTotal_fund() {
		return total_fund;
	}
	public void setTotal_fund(BigDecimal total_fund) {
		this.total_fund = total_fund;
	}
	public String getTotal_fund_remark() {
		return total_fund_remark;
	}
	public void setTotal_fund_remark(String total_fund_remark) {
		this.total_fund_remark = total_fund_remark;
	}
	public BigDecimal getTotal_fund_planned() {
		return total_fund_planned;
	}
	public void setTotal_fund_planned(BigDecimal total_fund_planned) {
		this.total_fund_planned = total_fund_planned;
	}
	public String getTotal_fund_planned_remark() {
		return total_fund_planned_remark;
	}
	public void setTotal_fund_planned_remark(String total_fund_planned_remark) {
		this.total_fund_planned_remark = total_fund_planned_remark;
	}
	public BigDecimal getTotal_expenditure() {
		return total_expenditure;
	}
	public void setTotal_expenditure(BigDecimal total_expenditure) {
		this.total_expenditure = total_expenditure;
	}
	public String getTotal_expenditure_remark() {
		return total_expenditure_remark;
	}
	public void setTotal_expenditure_remark(String total_expenditure_remark) {
		this.total_expenditure_remark = total_expenditure_remark;
	}
	public BigDecimal getTotal_wdf() {
		return total_wdf;
	}
	public void setTotal_wdf(BigDecimal total_wdf) {
		this.total_wdf = total_wdf;
	}
	public String getTotal_wdf_remark() {
		return total_wdf_remark;
	}
	public void setTotal_wdf_remark(String total_wdf_remark) {
		this.total_wdf_remark = total_wdf_remark;
	}
	public Boolean getNatural_resource() {
		return natural_resource;
	}
	public void setNatural_resource(Boolean natural_resource) {
		this.natural_resource = natural_resource;
	}
	public String getNatural_resource_remark() {
		return natural_resource_remark;
	}
	public void setNatural_resource_remark(String natural_resource_remark) {
		this.natural_resource_remark = natural_resource_remark;
	}
	public Boolean getNorms_relating() {
		return norms_relating;
	}
	public void setNorms_relating(Boolean norms_relating) {
		this.norms_relating = norms_relating;
	}
	public String getNorms_relating_remark() {
		return norms_relating_remark;
	}
	public void setNorms_relating_remark(String norms_relating_remark) {
		this.norms_relating_remark = norms_relating_remark;
	}
	public Boolean getAntural_asset() {
		return antural_asset;
	}
	public void setAntural_asset(Boolean antural_asset) {
		this.antural_asset = antural_asset;
	}
	public String getAntural_asset_remark() {
		return antural_asset_remark;
	}
	public void setAntural_asset_remark(String antural_asset_remark) {
		this.antural_asset_remark = antural_asset_remark;
	}
	public Boolean getControl_natural_resource() {
		return control_natural_resource;
	}
	public void setControl_natural_resource(Boolean control_natural_resource) {
		this.control_natural_resource = control_natural_resource;
	}
	public Boolean getControl_norms_relating() {
		return control_norms_relating;
	}
	public void setControl_norms_relating(Boolean control_norms_relating) {
		this.control_norms_relating = control_norms_relating;
	}
	public Boolean getControl_antural_asset() {
		return control_antural_asset;
	}
	public void setControl_antural_asset(Boolean control_antural_asset) {
		this.control_antural_asset = control_antural_asset;
	}
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Character getGrade() {
		return grade;
	}
	public void setGrade(Character grade) {
		this.grade = grade;
	}
	public BigDecimal getPre_kharif() {
		return pre_kharif;
	}
	public void setPre_kharif(BigDecimal pre_kharif) {
		this.pre_kharif = pre_kharif;
	}
	public BigDecimal getMid_kharif() {
		return mid_kharif;
	}
	public void setMid_kharif(BigDecimal mid_kharif) {
		this.mid_kharif = mid_kharif;
	}
	public BigDecimal getCtl_kharif() {
		return ctl_kharif;
	}
	public void setCtl_kharif(BigDecimal ctl_kharif) {
		this.ctl_kharif = ctl_kharif;
	}
	public BigDecimal getPre_rabi() {
		return pre_rabi;
	}
	public void setPre_rabi(BigDecimal pre_rabi) {
		this.pre_rabi = pre_rabi;
	}
	public BigDecimal getMid_rabi() {
		return mid_rabi;
	}
	public void setMid_rabi(BigDecimal mid_rabi) {
		this.mid_rabi = mid_rabi;
	}
	public BigDecimal getCtl_rabi() {
		return ctl_rabi;
	}
	public void setCtl_rabi(BigDecimal ctl_rabi) {
		this.ctl_rabi = ctl_rabi;
	}
	public BigDecimal getPre_thrdcrp() {
		return pre_thrdcrp;
	}
	public void setPre_thrdcrp(BigDecimal pre_thrdcrp) {
		this.pre_thrdcrp = pre_thrdcrp;
	}
	public BigDecimal getMid_thrdcrp() {
		return mid_thrdcrp;
	}
	public void setMid_thrdcrp(BigDecimal mid_thrdcrp) {
		this.mid_thrdcrp = mid_thrdcrp;
	}
	public BigDecimal getCtl_thrdcrp() {
		return ctl_thrdcrp;
	}
	public void setCtl_thrdcrp(BigDecimal ctl_thrdcrp) {
		this.ctl_thrdcrp = ctl_thrdcrp;
	}
	public BigDecimal getPre_total() {
		return pre_total;
	}
	public void setPre_total(BigDecimal pre_total) {
		this.pre_total = pre_total;
	}
	public BigDecimal getMid_total() {
		return mid_total;
	}
	public void setMid_total(BigDecimal mid_total) {
		this.mid_total = mid_total;
	}
	public BigDecimal getCtl_total() {
		return ctl_total;
	}
	public void setCtl_total(BigDecimal ctl_total) {
		this.ctl_total = ctl_total;
	}
	public BigDecimal getPre_plt() {
		return pre_plt;
	}
	public void setPre_plt(BigDecimal pre_plt) {
		this.pre_plt = pre_plt;
	}
	public BigDecimal getMid_plt() {
		return mid_plt;
	}
	public void setMid_plt(BigDecimal mid_plt) {
		this.mid_plt = mid_plt;
	}
	public BigDecimal getCtl_plt() {
		return ctl_plt;
	}
	public void setCtl_plt(BigDecimal ctl_plt) {
		this.ctl_plt = ctl_plt;
	}
	public BigDecimal getPre_clt() {
		return pre_clt;
	}
	public void setPre_clt(BigDecimal pre_clt) {
		this.pre_clt = pre_clt;
	}
	public BigDecimal getMid_clt() {
		return mid_clt;
	}
	public void setMid_clt(BigDecimal mid_clt) {
		this.mid_clt = mid_clt;
	}
	public BigDecimal getCtl_clt() {
		return ctl_clt;
	}
	public void setCtl_clt(BigDecimal ctl_clt) {
		this.ctl_clt = ctl_clt;
	}
	

}
