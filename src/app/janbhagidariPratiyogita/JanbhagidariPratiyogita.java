package app.janbhagidariPratiyogita;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.IwmpState;

@Entity
@Table(name = "janbhagidari_pratiyogita", schema = "public")
public class JanbhagidariPratiyogita {
	
	private Integer pratiyogita_id;
	private IwmpState iwmpState;
    private IwmpDistrict iwmpDistrict;
    private IwmpMProject iwmpMProject;
	private Date proj_inception;
	private Date proj_completion;
	private Integer no_gp;
	private Integer no_village;
	private BigDecimal proj_area;
	private BigDecimal proj_outlay;
	private Boolean national_bank;
	private BigDecimal fund_outlay;
	private BigDecimal fund_expenditure;
	private BigDecimal fund_per_exp;
	private Character status;
	private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    private Set<JanbhagidariPratiyogitaNgoname> janbhagidariPratiyogitaNgoname = new HashSet<JanbhagidariPratiyogitaNgoname>(0);
    
	
    public JanbhagidariPratiyogita() {
		
	}
    
	public JanbhagidariPratiyogita(Integer pratiyogita_id) {
		
		this.pratiyogita_id=pratiyogita_id;
	}
	
	public JanbhagidariPratiyogita(Integer pratiyogita_id, IwmpState iwmpState, IwmpDistrict iwmpDistrict, IwmpMProject iwmpMProject, Date proj_inception, 
			Date proj_completion, Integer no_gp, Integer no_village, BigDecimal proj_area, BigDecimal proj_outlay, Boolean national_bank, BigDecimal fund_outlay, 
			BigDecimal fund_expenditure, BigDecimal fund_per_exp, Character status, String requestedIp, String updatedBy,
			Date updatedDate, String createdBy, Date createdDate, Set<JanbhagidariPratiyogitaNgoname> janbhagidariPratiyogitaNgoname) {
		
		this.pratiyogita_id=pratiyogita_id;
		this.iwmpState=iwmpState;
		this.iwmpDistrict=iwmpDistrict;
		this.iwmpMProject=iwmpMProject;
		this.proj_inception=proj_inception;
		this.proj_completion=proj_completion;
		this.no_gp=no_gp;
		this.no_village=no_village;
		this.proj_area=proj_area;
		this.proj_outlay=proj_outlay;
		this.national_bank=national_bank;
		this.fund_outlay=fund_outlay;
		this.fund_expenditure=fund_expenditure;
		this.fund_per_exp=fund_per_exp;
		this.status=status;
		this.requestedIp=requestedIp;
		this.updatedBy=updatedBy;
		this.updatedDate=updatedDate;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.janbhagidariPratiyogitaNgoname=janbhagidariPratiyogitaNgoname;
		
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pratiyogita_id", unique=true, nullable=false)
	public Integer getPratiyogita_id() {
		return pratiyogita_id;
	}

	public void setPratiyogita_id(Integer pratiyogita_id) {
		this.pratiyogita_id = pratiyogita_id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "st_code")
	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dcode")
	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="proj_id")
	public IwmpMProject getIwmpMProject() {
		return iwmpMProject;
	}

	public void setIwmpMProject(IwmpMProject iwmpMProject) {
		this.iwmpMProject = iwmpMProject;
	}

	@Column(name = "proj_inception")
	public Date getProj_inception() {
		return proj_inception;
	}

	public void setProj_inception(Date proj_inception) {
		this.proj_inception = proj_inception;
	}

	@Column(name = "proj_completion")
	public Date getProj_completion() {
		return proj_completion;
	}

	public void setProj_completion(Date proj_completion) {
		this.proj_completion = proj_completion;
	}

	@Column(name = "no_gp")
	public Integer getNo_gp() {
		return no_gp;
	}

	public void setNo_gp(Integer no_gp) {
		this.no_gp = no_gp;
	}

	@Column(name = "no_village")
	public Integer getNo_village() {
		return no_village;
	}

	public void setNo_village(Integer no_village) {
		this.no_village = no_village;
	}

	@Column(name = "proj_area")
	public BigDecimal getProj_area() {
		return proj_area;
	}

	public void setProj_area(BigDecimal proj_area) {
		this.proj_area = proj_area;
	}

	@Column(name = "proj_outlay")
	public BigDecimal getProj_outlay() {
		return proj_outlay;
	}

	public void setProj_outlay(BigDecimal proj_outlay) {
		this.proj_outlay = proj_outlay;
	}

	@Column(name = "national_bank")
	public Boolean getNational_bank() {
		return national_bank;
	}

	public void setNational_bank(Boolean national_bank) {
		this.national_bank = national_bank;
	}

	@Column(name = "fund_outlay")
	public BigDecimal getFund_outlay() {
		return fund_outlay;
	}

	public void setFund_outlay(BigDecimal fund_outlay) {
		this.fund_outlay = fund_outlay;
	}
	@Column(name = "fund_expenditure")
	public BigDecimal getFund_expenditure() {
		return fund_expenditure;
	}

	public void setFund_expenditure(BigDecimal fund_expenditure) {
		this.fund_expenditure = fund_expenditure;
	}
	@Column(name = "fund_per_exp")
	public BigDecimal getFund_per_exp() {
		return fund_per_exp;
	}

	public void setFund_per_exp(BigDecimal fund_per_exp) {
		this.fund_per_exp = fund_per_exp;
	}

	@Column(name = "status")
	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Column(name = "requested_ip")
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}
	@Column(name = "updated_by")
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="updated_date", length=13)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	@Column(name = "created_by")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="created_date", length=13)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@OneToMany(fetch=FetchType.LAZY, mappedBy="janbhagidariPratiyogita")
	public Set<JanbhagidariPratiyogitaNgoname> getJanbhagidariPratiyogitaNgoname() {
		return janbhagidariPratiyogitaNgoname;
	}

	public void setJanbhagidariPratiyogitaNgoname(Set<JanbhagidariPratiyogitaNgoname> janbhagidariPratiyogitaNgoname) {
		this.janbhagidariPratiyogitaNgoname = janbhagidariPratiyogitaNgoname;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
