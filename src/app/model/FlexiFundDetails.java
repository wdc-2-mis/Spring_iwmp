package app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import app.model.master.IwmpGramPanchayat;

@Entity
@Table(name = "flexi_fund_details")
public class FlexiFundDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ff_id")
    private Integer ffId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proj_id")
    private IwmpMProject project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gcode")
    private IwmpGramPanchayat gcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "act_id")
    private FlexFundActivityMaster activity;
    
    @Column(name = "work_desc")
    private String workDesc;

    @Column(name = "est_cost")
    private Double est_cost;
    
    @Column(name = "ff_cost")
    private Double ffCost;

    @Column(name = "status")
    private String status;

    @Column(name = "requested_ip")
    private String requestedIp;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_date")
    private Date updatedDate;

    @OneToMany(mappedBy = "flexiFundDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FlexiFundPhoto> photos;

	public Integer getFfId() {
		return ffId;
	}

	public void setFfId(Integer ffId) {
		this.ffId = ffId;
	}

	public IwmpMProject getProject() {
		return project;
	}

	public void setProject(IwmpMProject project) {
		this.project = project;
	}

	public FlexFundActivityMaster getActivity() {
		return activity;
	}

	public void setActivity(FlexFundActivityMaster activity) {
		this.activity = activity;
	}

	public String getWorkDesc() {
		return workDesc;
	}

	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}

	public Double getFfCost() {
		return ffCost;
	}

	public void setFfCost(Double ffCost) {
		this.ffCost = ffCost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<FlexiFundPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<FlexiFundPhoto> photos) {
		this.photos = photos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IwmpGramPanchayat getGcode() {
		return gcode;
	}

	public void setGcode(IwmpGramPanchayat gcode) {
		this.gcode = gcode;
	}

	public Double getEst_cost() {
		return est_cost;
	}

	public void setEst_cost(Double est_cost) {
		this.est_cost = est_cost;
	}

   
    
}
