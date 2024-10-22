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

import app.model.project.WdcpmksyPrdouctionWorkid;

@Entity
@Table(name="wdcpmksy_project_asset_prod_status",schema="public" )
public class WdcpmksyProjectAssetProductionStatus implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private long statusid;
    private IwmpMProject iwmpMProject;
    private WdcpmksyPrdouctionWorkid wdcpmksyPrdouctionWorkid;
    private Date startdate;
    private Character status;
    private Date completiondate;
    private String reason;
    private Character headtype;
	private Date last_updated_date;
    public WdcpmksyProjectAssetProductionStatus() {
    }
    public WdcpmksyProjectAssetProductionStatus(long statusid) {
        this.statusid = statusid;
    }
    public WdcpmksyProjectAssetProductionStatus(long statusid, IwmpMProject iwmpMProject, WdcpmksyPrdouctionWorkid wdcpmksyPrdouctionWorkid, Date startdate, Character status, Date last_updated_date, Date completiondate, String reason, Character headtype) {
        this.statusid = statusid;
        this.iwmpMProject = iwmpMProject;
        this.wdcpmksyPrdouctionWorkid = wdcpmksyPrdouctionWorkid;
        this.startdate = startdate;
        this.status = status;
        this.completiondate = completiondate;
        this.reason = reason;
        this.headtype = headtype;
        this.last_updated_date = last_updated_date;
     }
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="statusid", unique=true, nullable=false)
    public long getStatusid() {
        return this.statusid;
    }
    
    public void setStatusid(long statusid) {
        this.statusid = statusid;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="projid")
    public IwmpMProject getIwmpMProject() {
        return this.iwmpMProject;
    }
    
    public void setIwmpMProject(IwmpMProject iwmpMProject) {
        this.iwmpMProject = iwmpMProject;
    }

     @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="assetid")
    public WdcpmksyPrdouctionWorkid getWdcpmksyPrdouctionWorkid() {
		return wdcpmksyPrdouctionWorkid;
	}
	public void setWdcpmksyPrdouctionWorkid(WdcpmksyPrdouctionWorkid wdcpmksyPrdouctionWorkid) {
		this.wdcpmksyPrdouctionWorkid = wdcpmksyPrdouctionWorkid;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="startdate", length=13)
    public Date getStartdate() {
        return this.startdate;
    }
    
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    
    @Column(name="status", length=1)
    public Character getStatus() {
        return this.status;
    }
    
    public void setStatus(Character status) {
        this.status = status;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="completiondate", length=13)
    public Date getCompletiondate() {
        return this.completiondate;
    }
    
    public void setCompletiondate(Date completiondate) {
        this.completiondate = completiondate;
    }

    
    @Column(name="reason", length=100)
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name="headtype", length=1)
	public Character getHeadtype() {
		return headtype;
	}


	public void setHeadtype(Character headtype) {
		this.headtype = headtype;
	}
	
	@Column(name="last_updated_date")
	public Date getLast_updated_date() {
		return last_updated_date;
	}
	public void setLast_updated_date(Date last_updated_date) {
		this.last_updated_date = last_updated_date;
	}

 

}



