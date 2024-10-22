package app.projectevaluation.model;

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
@Table(name="wdcpmksy_status_geotagg_work" ,schema="public")
public class WdcpmksyStatusGeotaggWork implements java.io.Serializable{
	
	
	private int geotaggWorkId ;			//		geotagg_work_id
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;   ///   project_profile_id
	private int geoTaggWork;					//	geo_tagg_work
	private String geoTaggWorkRemark;					//  geo_tagg_work_remark
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ; 
	private String requestIp; 
	
	
	public WdcpmksyStatusGeotaggWork() {
    }
	
    public WdcpmksyStatusGeotaggWork(int geotaggWorkId) {
        this.geotaggWorkId = geotaggWorkId;
    }
    
    public WdcpmksyStatusGeotaggWork(int geotaggWorkId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, int geoTaggWork, String geoTaggWorkRemark, 
    		 Date createdOn, String createdBy, Date updatedOn, String requestIp) {
        this.geotaggWorkId = geotaggWorkId;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
        this.geoTaggWork=geoTaggWork;
        this.geoTaggWorkRemark=geoTaggWorkRemark;
        this.createdOn=createdOn;
        this.createdBy=createdBy;
        this.updatedOn=updatedOn;
        this.requestIp=requestIp;
    }

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="geotagg_work_id", unique=true, nullable=false)
	public int getGeotaggWorkId() {
		return geotaggWorkId;
	}

	public void setGeotaggWorkId(int geotaggWorkId) {
		this.geotaggWorkId = geotaggWorkId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_profile_id")
	public WdcpmksyProjectProfileEvaluation getWdcpmksyProjectProfileEvaluation() {
		return wdcpmksyProjectProfileEvaluation;
	}

	public void setWdcpmksyProjectProfileEvaluation(WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation) {
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
	}

	@Column(name="geo_tagg_work")
	public int getGeoTaggWork() {
		return geoTaggWork;
	}

	public void setGeoTaggWork(int geoTaggWork) {
		this.geoTaggWork = geoTaggWork;
	}

	@Column(name="geo_tagg_work_remark", length=200)
	public String getGeoTaggWorkRemark() {
		return geoTaggWorkRemark;
	}

	public void setGeoTaggWorkRemark(String geoTaggWorkRemark) {
		this.geoTaggWorkRemark = geoTaggWorkRemark;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="created_on")
	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="created_by", length=20)
	public String getCreatedBy() {
		return createdBy;
	}

	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name="request_ip", length=20)
	public String getRequestIp() {
		return requestIp;
	}


	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
    
    
	
	
	
	

}
