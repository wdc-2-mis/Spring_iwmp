package app.janbhagidariPratiyogita;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.master.IwmpVillage;
import app.model.master.JanbhagidariTypeOfWork;

@Entity
@Table(name = "janbhagidari_pratiyogita_activities", schema = "public")
public class JanbhagidariPratiyogitaTypeofWork {
	
	
	private Integer typeofwork_id;
	private IwmpState iwmpState;
    private IwmpDistrict iwmpDistrict;
    private IwmpMProject iwmpMProject;
    private IwmpVillage iwmpVillage;
    private JanbhagidariTypeOfWork janbhagidariTypeOfWork;
	private BigDecimal es_work;
	private BigDecimal village;
	private BigDecimal ngo;
	private BigDecimal corporate;
	private Character work_status;
	private Character status;
	private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
	
	
	public JanbhagidariPratiyogitaTypeofWork() {}
	
	public JanbhagidariPratiyogitaTypeofWork(Integer typeofwork_id) {
		
		this.typeofwork_id=typeofwork_id;
	}
	
	public JanbhagidariPratiyogitaTypeofWork(Integer typeofwork_id,IwmpState iwmpState,IwmpDistrict iwmpDistrict,IwmpMProject iwmpMProject,IwmpVillage iwmpVillage,
			JanbhagidariTypeOfWork janbhagidariTypeOfWork,BigDecimal es_work,BigDecimal village,BigDecimal ngo,BigDecimal corporate,Character work_status, 
			Character status, String requestedIp, String updatedBy,Date updatedDate, String createdBy, Date createdDate) {
		
		this.typeofwork_id=typeofwork_id;
		this.iwmpState=iwmpState;
		this.iwmpDistrict=iwmpDistrict;
		this.iwmpMProject=iwmpMProject;
		this.iwmpVillage=iwmpVillage;
		this.janbhagidariTypeOfWork=janbhagidariTypeOfWork;
		this.es_work=es_work;
		this.village=village;
		this.ngo=ngo;
		this.work_status=work_status;
		this.status=status;
		this.requestedIp=requestedIp;
		this.updatedBy=updatedBy;
		this.updatedDate=updatedDate;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeofwork_id", unique=true, nullable=false)
	public Integer getTypeofwork_id() {
		return typeofwork_id;
	}

	public void setTypeofwork_id(Integer typeofwork_id) {
		this.typeofwork_id = typeofwork_id;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vcode")
	public IwmpVillage getIwmpVillage() {
		return iwmpVillage;
	}

	public void setIwmpVillage(IwmpVillage iwmpVillage) {
		this.iwmpVillage = iwmpVillage;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "work_id")
	public JanbhagidariTypeOfWork getJanbhagidariTypeOfWork() {
		return janbhagidariTypeOfWork;
	}

	public void setJanbhagidariTypeOfWork(JanbhagidariTypeOfWork janbhagidariTypeOfWork) {
		this.janbhagidariTypeOfWork = janbhagidariTypeOfWork;
	}

	@Column(name = "es_work")
	public BigDecimal getEs_work() {
		return es_work;
	}

	public void setEs_work(BigDecimal es_work) {
		this.es_work = es_work;
	}
	@Column(name = "village")
	public BigDecimal getVillage() {
		return village;
	}

	public void setVillage(BigDecimal village) {
		this.village = village;
	}
	@Column(name = "ngo")
	public BigDecimal getNgo() {
		return ngo;
	}

	public void setNgo(BigDecimal ngo) {
		this.ngo = ngo;
	}
	@Column(name = "corporate")
	public BigDecimal getCorporate() {
		return corporate;
	}

	public void setCorporate(BigDecimal corporate) {
		this.corporate = corporate;
	}
	@Column(name = "work_status")
	public Character getWork_status() {
		return work_status;
	}

	public void setWork_status(Character work_status) {
		this.work_status = work_status;
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
	
	
	
	
	
	
	
	
}
