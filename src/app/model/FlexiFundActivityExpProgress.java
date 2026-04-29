package app.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "flexi_fund_activity_exp_progress", schema = "public")
public class FlexiFundActivityExpProgress {

	private Integer ffActId;
	private Date completionDate;
	private BigDecimal ffCost;
	private FlexiFundDetails flexiFundDetails;

	private String requestedIp;
	private String createdBy;
	private String updatedBy;
	private Date updatedDate;

	public FlexiFundActivityExpProgress() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ff_act_id", unique = true, nullable = false)
	public Integer getFfActId() {
		return ffActId;
	}

	public void setFfActId(Integer ffActId) {
		this.ffActId = ffActId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "completion_date")
	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	@Column(name = "ff_cost", precision = 20, scale = 4)
	public BigDecimal getFfCost() {
		return ffCost;
	}

	public void setFfCost(BigDecimal ffCost) {
		this.ffCost = ffCost;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flexi_fund_id")
	public FlexiFundDetails getFlexiFundDetails() {
		return flexiFundDetails;
	}

	public void setFlexiFundDetails(FlexiFundDetails flexiFundDetails) {
		this.flexiFundDetails = flexiFundDetails;
	}

	@Column(name = "requested_ip", length = 25)
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	@Column(name = "created_by", length = 25)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_by", length = 25)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_date")
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
