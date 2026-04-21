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
    private Integer project;
    private Integer cactus;
    private BigDecimal cactus_est;
    private BigDecimal cactus_exp;
    private Integer springshed;
    private BigDecimal springshed_est;
    private BigDecimal springshed_exp;
    private Integer watershed;
    private BigDecimal watershed_est;
    private BigDecimal watershed_exp;
    private Integer waterbodies;
    private BigDecimal waterbodies_est;
    private BigDecimal waterbodies_exp;
    private Integer janbhagidari;
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

	
	
}
