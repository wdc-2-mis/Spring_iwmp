package app.model;

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

import app.model.master.PfmsEatmisdataDetail;
import app.model.outcome.FpoDetail;
import app.model.outcome.MFpoCoreactivity;
@Entity
@Table(name="pfms_eatmisexpdata_detail"
    ,schema="public"
)
public class PFMSEatmisExpDetail {

	private int pfmsEatmisDetailId;
	private PfmsEatmisdataDetail pfmsEatmisdataDetail;
	private Integer wicode ;
	private BigDecimal expenditure ;
	private Date createdOn;
    private String requestIp;
  //  private Date updatedOn;
    private String createdBy;
    
    
    public PFMSEatmisExpDetail() {
    }

	
    public PFMSEatmisExpDetail(int pfmsEatmisDetailId) {
        this.pfmsEatmisDetailId = pfmsEatmisDetailId;
    }
    public PFMSEatmisExpDetail(int pfmsEatmisDetailId, PfmsEatmisdataDetail pfmsEatmisdataDetail, Integer wicode, 
    		BigDecimal expenditure, Date createdOn, String requestIp, String createdBy) {
       this.pfmsEatmisDetailId = pfmsEatmisDetailId;
       this.pfmsEatmisdataDetail = pfmsEatmisdataDetail;
       this.wicode = wicode;
       this.createdOn = createdOn;
       this.requestIp = requestIp;
       this.createdBy = createdBy;
    }

   @Id 
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="pfms_eatmisdata_detail_id", unique=true, nullable=false)
	public int getPfmsEatmisDetailId() {
		return pfmsEatmisDetailId;
	}


	public void setPfmsEatmisDetailId(int pfmsEatmisDetailId) {
		this.pfmsEatmisDetailId = pfmsEatmisDetailId;
	}

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="eatmisdata_id")
	public PfmsEatmisdataDetail getPfmsEatmisdataDetail() {
		return pfmsEatmisdataDetail;
	}


	public void setPfmsEatmisdataDetail(PfmsEatmisdataDetail pfmsEatmisdataDetail) {
		this.pfmsEatmisdataDetail = pfmsEatmisdataDetail;
	}

	@Column(name = "wicode")
	public Integer getWicode() {
		return wicode;
	}


	public void setWicode(Integer wicode) {
		this.wicode = wicode;
	}

	@Column(name="expenditure", precision=20)
	public BigDecimal getExpenditure() {
		return expenditure;
	}


	public void setExpenditure(BigDecimal expenditure) {
		this.expenditure = expenditure;
	}

	@Column(name="created_on", length=13)
	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="request_ip", length=20)
	public String getRequestIp() {
		return requestIp;
	}


	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	 @Column(name="created_by", length=20)
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
    
    
    
    
    
}
