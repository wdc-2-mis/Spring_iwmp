package app.bean;

import java.math.BigDecimal;

import app.model.MBlsOutcome;

public class NewBaseLineSurveyBean {
	
	private Integer bls_out_main_id_pk;
	private Integer proj_id;
	private Integer vcode;
	private String block_name;
	private String block_name_achv;
	private String gram_panchayat_name;
	private String gram_panchayat_name_achv;
	private String village_name;
	private String village_name_achv;
	private String plot_no;
	private String plot_no_achv;
	private BigDecimal area;
	private BigDecimal area_achv;
	private String owner_name;
	private String owner_name_achv;
	private String land_sub_classification;
	private Integer irrigation_status_id;
	private String irrigation;
	private String irrigation_achv;
	private Character status;
	private Character status_achv;
	private Integer bls_out_detail_id_pk;
	private Integer classification_land_id;
	private String classification_land;
	private String classification_land_achv;
	private Integer no_of_crop_id;
	private String no_crop;
	private String no_crop_achv;
	private Integer forest_land_type_id;
	private String forest_land;
	private String forest_land_achv;
	private Integer bls_out_detail_tranx_id_pk;
	private Integer crop_type_id;
	private String crop_type;
	private String crop_type_achv;
	private BigDecimal crop_production;
	private BigDecimal crop_production_achv;
	private BigDecimal avg_income;
	private BigDecimal avg_income_achv;
	private BigDecimal crop_area;
	private BigDecimal crop_area_achv;
	private Integer season_id;
	private MBlsOutcome MBlsOutcomeBySeasonId;
	private Integer bls_out_detail_crop_id_pk;
	private BigDecimal micro_irrigation;
	private Character del_status;
	
	public MBlsOutcome getMBlsOutcomeBySeasonId() {
		return MBlsOutcomeBySeasonId;
	}
	public void setMBlsOutcomeBySeasonId(MBlsOutcome mBlsOutcomeBySeasonId) {
		MBlsOutcomeBySeasonId = mBlsOutcomeBySeasonId;
	}
	private String season;
	public Integer getSeason_id() {
		return season_id;
	}
	public void setSeason_id(Integer season_id) {
		this.season_id = season_id;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	private String season_achv;
	private Integer ownership_id;
	private String ownership;
	private String ownership_achv;
	private MBlsOutcome cropid;
	public Integer getBls_out_main_id_pk() {
		return bls_out_main_id_pk;
	}
	public void setBls_out_main_id_pk(Integer bls_out_main_id_pk) {
		this.bls_out_main_id_pk = bls_out_main_id_pk;
	}
	public Integer getProj_id() {
		return proj_id;
	}
	public void setProj_id(Integer proj_id) {
		this.proj_id = proj_id;
	}
	public Integer getVcode() {
		return vcode;
	}
	public void setVcode(Integer vcode) {
		this.vcode = vcode;
	}
	public String getBlock_name() {
		return block_name;
	}
	public void setBlock_name(String block_name) {
		this.block_name = block_name;
	}
	public String getGram_panchayat_name() {
		return gram_panchayat_name;
	}
	public void setGram_panchayat_name(String gram_panchayat_name) {
		this.gram_panchayat_name = gram_panchayat_name;
	}
	public String getVillage_name() {
		return village_name;
	}
	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}
	public String getPlot_no() {
		return plot_no;
	}
	public void setPlot_no(String plot_no) {
		this.plot_no = plot_no;
	}
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getLand_sub_classification() {
		return land_sub_classification;
	}
	public void setLand_sub_classification(String land_sub_classification) {
		this.land_sub_classification = land_sub_classification;
	}
	public Integer getIrrigation_status_id() {
		return irrigation_status_id;
	}
	public void setIrrigation_status_id(Integer irrigation_status_id) {
		this.irrigation_status_id = irrigation_status_id;
	}
	public String getIrrigation() {
		return irrigation;
	}
	public void setIrrigation(String irrigation) {
		this.irrigation = irrigation;
	}
	
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public Integer getBls_out_detail_id_pk() {
		return bls_out_detail_id_pk;
	}
	public void setBls_out_detail_id_pk(Integer bls_out_detail_id_pk) {
		this.bls_out_detail_id_pk = bls_out_detail_id_pk;
	}
	public Integer getClassification_land_id() {
		return classification_land_id;
	}
	public void setClassification_land_id(Integer classification_land_id) {
		this.classification_land_id = classification_land_id;
	}
	public String getClassification_land() {
		return classification_land;
	}
	public void setClassification_land(String classification_land) {
		this.classification_land = classification_land;
	}
	public Integer getNo_of_crop_id() {
		return no_of_crop_id;
	}
	public void setNo_of_crop_id(Integer no_of_crop_id) {
		this.no_of_crop_id = no_of_crop_id;
	}
	public String getNo_crop() {
		return no_crop;
	}
	public void setNo_crop(String no_crop) {
		this.no_crop = no_crop;
	}
	public Integer getForest_land_type_id() {
		return forest_land_type_id;
	}
	public void setForest_land_type_id(Integer forest_land_type_id) {
		this.forest_land_type_id = forest_land_type_id;
	}
	public String getForest_land() {
		return forest_land;
	}
	public void setForest_land(String forest_land) {
		this.forest_land = forest_land;
	}
	public Integer getBls_out_detail_tranx_id_pk() {
		return bls_out_detail_tranx_id_pk;
	}
	public void setBls_out_detail_tranx_id_pk(Integer bls_out_detail_tranx_id_pk) {
		this.bls_out_detail_tranx_id_pk = bls_out_detail_tranx_id_pk;
	}
	public Integer getCrop_type_id() {
		return crop_type_id;
	}
	public void setCrop_type_id(Integer crop_type_id) {
		this.crop_type_id = crop_type_id;
	}
	public String getCrop_type() {
		return crop_type;
	}
	public void setCrop_type(String crop_type) {
		this.crop_type = crop_type;
	}
	public BigDecimal getCrop_production() {
		return crop_production;
	}
	public void setCrop_production(BigDecimal crop_production) {
		this.crop_production = crop_production;
	}
	public BigDecimal getAvg_income() {
		return avg_income;
	}
	public void setAvg_income(BigDecimal avg_income) {
		this.avg_income = avg_income;
	}
	public BigDecimal getCrop_area() {
		return crop_area;
	}
	public void setCrop_area(BigDecimal crop_area) {
		this.crop_area = crop_area;
	}
	
	public Integer getOwnership_id() {
		return ownership_id;
	}
	public void setOwnership_id(Integer ownership_id) {
		this.ownership_id = ownership_id;
	}
	public String getOwnership() {
		return ownership;
	}
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	public MBlsOutcome getCropid() {
		return cropid;
	}
	public void setCropid(MBlsOutcome mBlsOutcome) {
		this.cropid = mBlsOutcome;
	}
	public Integer getBls_out_detail_crop_id_pk() {
		return bls_out_detail_crop_id_pk;
	}
	public void setBls_out_detail_crop_id_pk(Integer bls_out_detail_crop_id_pk) {
		this.bls_out_detail_crop_id_pk = bls_out_detail_crop_id_pk;
	}
	
	public String getBlock_name_achv() {
		return block_name_achv;
	}
	public void setBlock_name_achv(String block_name_achv) {
		this.block_name_achv = block_name_achv;
	}
	public String getGram_panchayat_name_achv() {
		return gram_panchayat_name_achv;
	}
	public void setGram_panchayat_name_achv(String gram_panchayat_name_achv) {
		this.gram_panchayat_name_achv = gram_panchayat_name_achv;
	}
	public String getVillage_name_achv() {
		return village_name_achv;
	}
	public void setVillage_name_achv(String village_name_achv) {
		this.village_name_achv = village_name_achv;
	}
	public BigDecimal getArea_achv() {
		return area_achv;
	}
	public void setArea_achv(BigDecimal area_achv) {
		this.area_achv = area_achv;
	}
	public String getOwner_name_achv() {
		return owner_name_achv;
	}
	public void setOwner_name_achv(String owner_name_achv) {
		this.owner_name_achv = owner_name_achv;
	}
	public String getIrrigation_achv() {
		return irrigation_achv;
	}
	public void setIrrigation_achv(String irrigation_achv) {
		this.irrigation_achv = irrigation_achv;
	}
	public Character getStatus_achv() {
		return status_achv;
	}
	public void setStatus_achv(Character status_achv) {
		this.status_achv = status_achv;
	}
	public String getClassification_land_achv() {
		return classification_land_achv;
	}
	public void setClassification_land_achv(String classification_land_achv) {
		this.classification_land_achv = classification_land_achv;
	}
	public String getNo_crop_achv() {
		return no_crop_achv;
	}
	public void setNo_crop_achv(String no_crop_achv) {
		this.no_crop_achv = no_crop_achv;
	}
	public String getForest_land_achv() {
		return forest_land_achv;
	}
	public void setForest_land_achv(String forest_land_achv) {
		this.forest_land_achv = forest_land_achv;
	}
	public String getCrop_type_achv() {
		return crop_type_achv;
	}
	public void setCrop_type_achv(String crop_type_achv) {
		this.crop_type_achv = crop_type_achv;
	}
	public BigDecimal getCrop_production_achv() {
		return crop_production_achv;
	}
	public void setCrop_production_achv(BigDecimal crop_production_achv) {
		this.crop_production_achv = crop_production_achv;
	}
	public BigDecimal getAvg_income_achv() {
		return avg_income_achv;
	}
	public void setAvg_income_achv(BigDecimal avg_income_achv) {
		this.avg_income_achv = avg_income_achv;
	}
	public BigDecimal getCrop_area_achv() {
		return crop_area_achv;
	}
	public void setCrop_area_achv(BigDecimal crop_area_achv) {
		this.crop_area_achv = crop_area_achv;
	}
	public String getSeason_achv() {
		return season_achv;
	}
	public void setSeason_achv(String season_achv) {
		this.season_achv = season_achv;
	}
	public String getOwnership_achv() {
		return ownership_achv;
	}
	public void setOwnership_achv(String ownership_achv) {
		this.ownership_achv = ownership_achv;
	}
	public String getPlot_no_achv() {
		return plot_no_achv;
	}
	public void setPlot_no_achv(String plot_no_achv) {
		this.plot_no_achv = plot_no_achv;
	}
	public BigDecimal getMicro_irrigation() {
		return micro_irrigation;
	}
	public void setMicro_irrigation(BigDecimal micro_irrigation) {
		this.micro_irrigation = micro_irrigation;
	}
	public Character getDel_status() {
		return del_status;
	}
	public void setDel_status(Character del_status) {
		this.del_status = del_status;
	}
	
}
