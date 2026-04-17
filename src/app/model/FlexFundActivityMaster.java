package app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "flexi_fund_activity_master")
public class FlexFundActivityMaster {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "act_id")
	    private Integer actId;

	    @Column(name = "act_name")
	    private String actName;

	    @Column(name = "updated_by")
	    private String updatedBy;

	    @Column(name = "updated_date")
	    @Temporal(TemporalType.DATE)
	    private Date updatedDate;

	    @Column(name = "request_ip")
	    private String requestIp;

	    // Getters & Setters

	    public Integer getActId() {
	        return actId;
	    }

	    public void setActId(Integer actId) {
	        this.actId = actId;
	    }

	    public String getActName() {
	        return actName;
	    }

	    public void setActName(String actName) {
	        this.actName = actName;
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

	    public String getRequestIp() {
	        return requestIp;
	    }

	    public void setRequestIp(String requestIp) {
	        this.requestIp = requestIp;
	    }
	}

