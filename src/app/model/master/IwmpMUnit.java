package app.model.master;
// Generated 8 Mar, 2021 4:17:47 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.IwmpActivityAssetAddonParameter;

@Entity
@Table(name="iwmp_m_unit" ,schema="public")
public class IwmpMUnit  implements java.io.Serializable {


     private int unitCode;
     private String unitDesc;
     private String lastUpdatedBy;
     private Date lastUpdatedDate;
     private String requestIp;
     private Set<IwmpMPhyActivity> iwmpMPhyActivities = new HashSet<IwmpMPhyActivity>(0);
     private Set<IwmpActivityAssetAddonParameter> iwmpAssetParameters = new HashSet<IwmpActivityAssetAddonParameter>(0);

    public IwmpMUnit() {
    }

	
    public IwmpMUnit(int unitCode) {
        this.unitCode = unitCode;
    }
    public IwmpMUnit(int unitCode, String unitDesc, String lastUpdatedBy, Date lastUpdatedDate, String requestIp, Set<IwmpMPhyActivity> iwmpMPhyActivities) {
       this.unitCode = unitCode;
       this.unitDesc = unitDesc;
       this.lastUpdatedBy = lastUpdatedBy;
       this.lastUpdatedDate = lastUpdatedDate;
       this.requestIp = requestIp;
       this.iwmpMPhyActivities = iwmpMPhyActivities;
    }
   
    @Id 
    @Column(name="unit_code", unique=true, nullable=false)
    public int getUnitCode() {
        return this.unitCode;
    }
    
    public void setUnitCode(int unitCode) {
        this.unitCode = unitCode;
    }
    
    @Column(name="unit_desc", length=30)
    public String getUnitDesc() {
        return this.unitDesc;
    }
    
    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }

    
    @Column(name="last_updated_by", length=20)
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="last_updated_date", length=13)
    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }
    
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    
    @Column(name="request_ip", length=20)
    public String getRequestIp() {
        return this.requestIp;
    }
    
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMUnit")
    public Set<IwmpMPhyActivity> getIwmpMPhyActivities() {
        return this.iwmpMPhyActivities;
    }
    
    public void setIwmpMPhyActivities(Set<IwmpMPhyActivity> iwmpMPhyActivities) {
        this.iwmpMPhyActivities = iwmpMPhyActivities;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpMUnit")
    public Set<IwmpActivityAssetAddonParameter> getIwmpAssetParameters() {
        return this.iwmpAssetParameters;
    }
    
    public void setIwmpAssetParameters(Set<IwmpActivityAssetAddonParameter> iwmpAssetParameters) {
        this.iwmpAssetParameters = iwmpAssetParameters;
    }



}


