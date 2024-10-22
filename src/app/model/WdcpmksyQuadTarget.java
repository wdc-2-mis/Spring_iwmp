package app.model;

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

@Entity
@Table(name="wdcpmksy_quad_target"
    ,schema="public"
)
public class WdcpmksyQuadTarget  implements java.io.Serializable {


     private int targetId;
     private IwmpMFinYear iwmpMFinYear;
     private IwmpMProject iwmpMProject;
     private Integer indicatorId;
     private Double firstQuad;
     private Double secondQuad;
     private Double thirdQuad;
     private Double fourthQuad;
     private Character status;
     private String createdBy;
     private String updatedBy;
     private Date createdOn;
     private Date updatedOn;
     private String requestIp;
     private Character q1status;
     private Character q2status;
     private Character q3status;
     private Character q4status;
     private Integer stcode;
    public WdcpmksyQuadTarget() {
    }

	
    public WdcpmksyQuadTarget(int targetId) {
        this.targetId = targetId;
    }
    public WdcpmksyQuadTarget(int targetId, IwmpMFinYear iwmpMFinYear, IwmpMProject iwmpMProject,  Integer indicatorId, Integer stcode, Double firstQuad, Double secondQuad, Double thirdQuad, Double fourthQuad, Character status, Character q1status, Character q2status, Character q3status, Character q4status, String createdBy, String updatedBy, Date createdOn, Date updatedOn, String requestIp) {
       this.targetId = targetId;
       this.iwmpMFinYear = iwmpMFinYear;
       this.iwmpMProject = iwmpMProject;
       this.indicatorId = indicatorId;
       this.stcode = stcode;
       this.firstQuad = firstQuad;
       this.secondQuad = secondQuad;
       this.thirdQuad = thirdQuad;
       this.fourthQuad = fourthQuad;
       this.status = status;
       this.q1status = q1status;
       this.q2status = q1status;
       this.q3status = q1status;
       this.q4status = q1status;
       this.createdBy = createdBy;
       this.updatedBy = updatedBy;
       this.createdOn = createdOn;
       this.updatedOn = updatedOn;
       this.requestIp = requestIp;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="target_id", unique=true, nullable=false)
    public int getTargetId() {
        return this.targetId;
    }
    
    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fin_yr_cd")
    public IwmpMFinYear getIwmpMFinYear() {
        return this.iwmpMFinYear;
    }
    
    public void setIwmpMFinYear(IwmpMFinYear iwmpMFinYear) {
        this.iwmpMFinYear = iwmpMFinYear;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="proj_id")
    public IwmpMProject getIwmpMProject() {
        return this.iwmpMProject;
    }
    
    public void setIwmpMProject(IwmpMProject iwmpMProject) {
        this.iwmpMProject = iwmpMProject;
    }

   
    @Column(name="indicator_id")
    public Integer getIndicatorId() {
		return indicatorId;
	}


	public void setIndicatorId(Integer indicatorId) {
		this.indicatorId = indicatorId;
	}


	@Column(name="stcode")
	public Integer getStcode() {
		return stcode;
	}


	public void setStcode(Integer stcode) {
		this.stcode = stcode;
	}


	public void setFirstQuad(Double firstQuad) {
		this.firstQuad = firstQuad;
	}


	@Column(name="first_quad")
    public Double getFirstQuad() {
        return this.firstQuad;
    }
    
    public void setFirstQuad(double d) {
        this.firstQuad = d;
    }

    
    @Column(name="second_quad")
    public Double getSecondQuad() {
        return this.secondQuad;
    }
    
    public void setSecondQuad(Double secondQuad) {
        this.secondQuad = secondQuad;
    }

    
    @Column(name="third_quad")
    public Double getThirdQuad() {
        return this.thirdQuad;
    }
    
    public void setThirdQuad(Double thirdQuad) {
        this.thirdQuad = thirdQuad;
    }

    
    @Column(name="fourth_quad")
    public Double getFourthQuad() {
        return this.fourthQuad;
    }
    
    public void setFourthQuad(Double fourthQuad) {
        this.fourthQuad = fourthQuad;
    }

    
    @Column(name="status", length=1)
    public Character getStatus() {
        return this.status;
    }
    
    public void setStatus(Character status) {
        this.status = status;
    }

    
    @Column(name="q1status", length=1)
    public Character getQ1status() {
		return q1status;
	}


	public void setQ1status(Character q1status) {
		this.q1status = q1status;
	}

	@Column(name="q2status", length=1)
	public Character getQ2status() {
		return q2status;
	}


	public void setQ2status(Character q2status) {
		this.q2status = q2status;
	}

	@Column(name="q3status", length=1)
	public Character getQ3status() {
		return q3status;
	}


	public void setQ3status(Character q3status) {
		this.q3status = q3status;
	}

	@Column(name="q4status", length=1)
	public Character getQ4status() {
		return q4status;
	}


	public void setQ4status(Character q4status) {
		this.q4status = q4status;
	}


	@Column(name="created_by", length=20)
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    
    @Column(name="updated_by", length=20)
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="created_on", length=13)
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="updated_on", length=13)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }
    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    
    @Column(name="request_ip", length=20)
    public String getRequestIp() {
        return this.requestIp;
    }
    
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }




}
