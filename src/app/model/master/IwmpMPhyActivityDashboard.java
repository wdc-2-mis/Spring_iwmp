package app.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iwmp_m_phy_activity_dashboard", schema = "public")
public class IwmpMPhyActivityDashboard implements java.io.Serializable{

	private int activityCode;
	private String activityDesc;
	private int headcode;
	private String headdesc;
	private String unit;
	private Integer seqNo;
	private Character status;
	
	public IwmpMPhyActivityDashboard(int activityCode, String activityDesc, int headcode, String headdesc, String unit,
			Integer seqNo, Character status) {
		this.activityCode = activityCode;
		this.activityDesc = activityDesc;
		this.headcode = headcode;
		this.headdesc = headdesc;
		this.unit = unit;
		this.seqNo = seqNo;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_code", unique = true, nullable = false)
	public int getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(int activityCode) {
		this.activityCode = activityCode;
	}
	@Column(name = "activity_desc", length = 100)
	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
    
	@Column(name = "head_code")
	public int getHeadcode() {
		return headcode;
	}

	public void setHeadcode(int headcode) {
		this.headcode = headcode;
	}

	@Column(name = "head_desc", length = 100)
	public String getHeaddesc() {
		return headdesc;
	}

	public void setHeaddesc(String headdesc) {
		this.headdesc = headdesc;
	}
    
	@Column(name = "unit", length = 100)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
    
	@Column(name = "seq_no")
	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Character getStatus() {
		return status;
	}

	@Column(name = "status", length = 1)
	public void setStatus(Character status) {
		this.status = status;
	}
	
	
}
