package app.bean;

import java.math.BigDecimal;
import java.util.Date;

public class FlexiFundMActivityBean {

	private Integer act_id;
    private String act_name;
    private String updatedby;
    private Date updateddate;
    private String requestid;
    
    private String st_name;
    private Integer st_code;
    private Integer dcode;           
    private String dist_name;
    private Integer proj_id;           
    private String proj_name;
    private Integer gcode;           
    private String gp_name;
    private Integer grampanchayat;
    private Integer project;
    private Integer ff_id;
    private BigDecimal exp_cost;
    private Character exp_status;
    private String expdate;
    private Integer cactus;
    private Integer cactus_id;
    private String cactus_photos;
    private String cactus_desc;
    private BigDecimal cactus_est;
    private BigDecimal cactus_exp;
    private Integer springshed;
    private Integer springshed_id;
    private String springshed_photos;
    private String springshed_desc;
    private BigDecimal springshed_est;
    private BigDecimal springshed_exp;
    private Integer watershed;
    private Integer watershed_id;
    private String watershed_photos;
    private String watershed_desc;
    private BigDecimal watershed_est;
    private BigDecimal watershed_exp;
    private Integer waterbodies;
    private Integer waterbodies_id;
    private String waterbodies_photos;
    private String waterbodies_desc;
    private BigDecimal waterbodies_est;
    private BigDecimal waterbodies_exp;
    private Integer janbhagidari;
    private Integer janbhagidari_id;
    private String janbhagidari_photos;
    private String janbhagidari_desc;
    private BigDecimal janbhagidari_est;
    private BigDecimal janbhagidari_exp;
    private BigDecimal total_estimation;
    private BigDecimal total_expenditure;
    
    public FlexiFundMActivityBean() {
        super();
    }

	public FlexiFundMActivityBean(Integer act_id, String act_name, String updatedby, Date updateddate,
			String requestid) {
		super();
		this.act_id = act_id;
		this.act_name = act_name;
		this.updatedby = updatedby;
		this.updateddate = updateddate;
		this.requestid = requestid;
	}

	public Integer getAct_id() {
		return act_id;
	}

	public void setAct_id(Integer act_id) {
		this.act_id = act_id;
	}

	public String getAct_name() {
		return act_name;
	}

	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
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

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public Integer getCactus() {
		return cactus;
	}

	public void setCactus(Integer cactus) {
		this.cactus = cactus;
	}

	public BigDecimal getCactus_est() {
		return cactus_est;
	}

	public void setCactus_est(BigDecimal cactus_est) {
		this.cactus_est = cactus_est;
	}

	public BigDecimal getCactus_exp() {
		return cactus_exp;
	}

	public void setCactus_exp(BigDecimal cactus_exp) {
		this.cactus_exp = cactus_exp;
	}

	public Integer getSpringshed() {
		return springshed;
	}

	public void setSpringshed(Integer springshed) {
		this.springshed = springshed;
	}

	public BigDecimal getSpringshed_est() {
		return springshed_est;
	}

	public void setSpringshed_est(BigDecimal springshed_est) {
		this.springshed_est = springshed_est;
	}

	public BigDecimal getSpringshed_exp() {
		return springshed_exp;
	}

	public void setSpringshed_exp(BigDecimal springshed_exp) {
		this.springshed_exp = springshed_exp;
	}

	public Integer getWatershed() {
		return watershed;
	}

	public void setWatershed(Integer watershed) {
		this.watershed = watershed;
	}

	public BigDecimal getWatershed_est() {
		return watershed_est;
	}

	public void setWatershed_est(BigDecimal watershed_est) {
		this.watershed_est = watershed_est;
	}

	public BigDecimal getWatershed_exp() {
		return watershed_exp;
	}

	public void setWatershed_exp(BigDecimal watershed_exp) {
		this.watershed_exp = watershed_exp;
	}

	public Integer getWaterbodies() {
		return waterbodies;
	}

	public void setWaterbodies(Integer waterbodies) {
		this.waterbodies = waterbodies;
	}

	public BigDecimal getWaterbodies_est() {
		return waterbodies_est;
	}

	public void setWaterbodies_est(BigDecimal waterbodies_est) {
		this.waterbodies_est = waterbodies_est;
	}

	public BigDecimal getWaterbodies_exp() {
		return waterbodies_exp;
	}

	public void setWaterbodies_exp(BigDecimal waterbodies_exp) {
		this.waterbodies_exp = waterbodies_exp;
	}

	public Integer getJanbhagidari() {
		return janbhagidari;
	}

	public void setJanbhagidari(Integer janbhagidari) {
		this.janbhagidari = janbhagidari;
	}

	public BigDecimal getJanbhagidari_est() {
		return janbhagidari_est;
	}

	public void setJanbhagidari_est(BigDecimal janbhagidari_est) {
		this.janbhagidari_est = janbhagidari_est;
	}

	public BigDecimal getJanbhagidari_exp() {
		return janbhagidari_exp;
	}

	public void setJanbhagidari_exp(BigDecimal janbhagidari_exp) {
		this.janbhagidari_exp = janbhagidari_exp;
	}

	public BigDecimal getTotal_estimation() {
		return total_estimation;
	}

	public void setTotal_estimation(BigDecimal total_estimation) {
		this.total_estimation = total_estimation;
	}

	public BigDecimal getTotal_expenditure() {
		return total_expenditure;
	}

	public void setTotal_expenditure(BigDecimal total_expenditure) {
		this.total_expenditure = total_expenditure;
	}

	public Integer getDcode() {
		return dcode;
	}

	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}

	public String getDist_name() {
		return dist_name;
	}

	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
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

	public Integer getGrampanchayat() {
		return grampanchayat;
	}

	public void setGrampanchayat(Integer grampanchayat) {
		this.grampanchayat = grampanchayat;
	}

	public Integer getGcode() {
		return gcode;
	}

	public void setGcode(Integer gcode) {
		this.gcode = gcode;
	}

	public String getGp_name() {
		return gp_name;
	}

	public void setGp_name(String gp_name) {
		this.gp_name = gp_name;
	}

	public String getCactus_desc() {
		return cactus_desc;
	}

	public void setCactus_desc(String cactus_desc) {
		this.cactus_desc = cactus_desc;
	}

	public String getSpringshed_desc() {
		return springshed_desc;
	}

	public void setSpringshed_desc(String springshed_desc) {
		this.springshed_desc = springshed_desc;
	}

	public String getWatershed_desc() {
		return watershed_desc;
	}

	public void setWatershed_desc(String watershed_desc) {
		this.watershed_desc = watershed_desc;
	}

	public String getWaterbodies_desc() {
		return waterbodies_desc;
	}

	public void setWaterbodies_desc(String waterbodies_desc) {
		this.waterbodies_desc = waterbodies_desc;
	}

	public String getJanbhagidari_desc() {
		return janbhagidari_desc;
	}

	public void setJanbhagidari_desc(String janbhagidari_desc) {
		this.janbhagidari_desc = janbhagidari_desc;
	}

	public String getCactus_photos() {
		return cactus_photos;
	}

	public void setCactus_photos(String cactus_photos) {
		this.cactus_photos = cactus_photos;
	}

	public String getSpringshed_photos() {
		return springshed_photos;
	}

	public void setSpringshed_photos(String springshed_photos) {
		this.springshed_photos = springshed_photos;
	}

	public String getWatershed_photos() {
		return watershed_photos;
	}

	public void setWatershed_photos(String watershed_photos) {
		this.watershed_photos = watershed_photos;
	}

	public String getWaterbodies_photos() {
		return waterbodies_photos;
	}

	public void setWaterbodies_photos(String waterbodies_photos) {
		this.waterbodies_photos = waterbodies_photos;
	}

	public String getJanbhagidari_photos() {
		return janbhagidari_photos;
	}

	public void setJanbhagidari_photos(String janbhagidari_photos) {
		this.janbhagidari_photos = janbhagidari_photos;
	}

	public Integer getCactus_id() {
		return cactus_id;
	}

	public void setCactus_id(Integer cactus_id) {
		this.cactus_id = cactus_id;
	}

	public Integer getSpringshed_id() {
		return springshed_id;
	}

	public void setSpringshed_id(Integer springshed_id) {
		this.springshed_id = springshed_id;
	}

	public Integer getWatershed_id() {
		return watershed_id;
	}

	public void setWatershed_id(Integer watershed_id) {
		this.watershed_id = watershed_id;
	}

	public Integer getWaterbodies_id() {
		return waterbodies_id;
	}

	public void setWaterbodies_id(Integer waterbodies_id) {
		this.waterbodies_id = waterbodies_id;
	}

	public Integer getJanbhagidari_id() {
		return janbhagidari_id;
	}

	public void setJanbhagidari_id(Integer janbhagidari_id) {
		this.janbhagidari_id = janbhagidari_id;
	}

	public Integer getFf_id() {
		return ff_id;
	}

	public void setFf_id(Integer ff_id) {
		this.ff_id = ff_id;
	}

	public BigDecimal getExp_cost() {
		return exp_cost;
	}

	public void setExp_cost(BigDecimal exp_cost) {
		this.exp_cost = exp_cost;
	}

	public Character getExp_status() {
		return exp_status;
	}

	public void setExp_status(Character exp_status) {
		this.exp_status = exp_status;
	}

	public String getExpdate() {
		return expdate;
	}

	public void setExpdate(String expdate) {
		this.expdate = expdate;
	}

	
	
}
