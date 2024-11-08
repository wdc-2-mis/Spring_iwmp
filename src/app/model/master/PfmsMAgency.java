package app.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PfmsMAgency generated by hbm2java
 */
@Entity
@Table(name="pfms_m_agency", schema="public")
public class PfmsMAgency  implements java.io.Serializable {


     private int agencyId;
     private String uniqueAgencyCode;
     private String agencyName;
     private String hierarchyLevel;
     private String stateName;
     private Integer stateLgd;
     private String districtName;
     private Integer districtLgd;
     private String blockName;
     private Integer blockLgd;
     private String panchayatName;
     private Integer panchayatLgd;
     private String villageName;
     private Integer villageLgd;
     private Integer stCode;
     private Integer dcode;
     private Integer bcode;
     private Integer gcode;
     private Integer vcode;

    public PfmsMAgency() {
    }

	
    public PfmsMAgency(int agencyId) {
        this.agencyId = agencyId;
    }
    public PfmsMAgency(int agencyId, String uniqueAgencyCode, String agencyName, String hierarchyLevel, String stateName, Integer stateLgd, String districtName, Integer districtLgd, String blockName, Integer blockLgd, String panchayatName, Integer panchayatLgd, String villageName, Integer villageLgd, Integer stCode, Integer dcode, Integer bcode, Integer gcode, Integer vcode) {
       this.agencyId = agencyId;
       this.uniqueAgencyCode = uniqueAgencyCode;
       this.agencyName = agencyName;
       this.hierarchyLevel = hierarchyLevel;
       this.stateName = stateName;
       this.stateLgd = stateLgd;
       this.districtName = districtName;
       this.districtLgd = districtLgd;
       this.blockName = blockName;
       this.blockLgd = blockLgd;
       this.panchayatName = panchayatName;
       this.panchayatLgd = panchayatLgd;
       this.villageName = villageName;
       this.villageLgd = villageLgd;
       this.stCode = stCode;
       this.dcode = dcode;
       this.bcode = bcode;
       this.gcode = gcode;
       this.vcode = vcode;
    }
   
     @Id 

    
    @Column(name="agency_id", unique=true, nullable=false)
    public int getAgencyId() {
        return this.agencyId;
    }
    
    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    
    @Column(name="unique_agency_code", length=13)
    public String getUniqueAgencyCode() {
        return this.uniqueAgencyCode;
    }
    
    public void setUniqueAgencyCode(String uniqueAgencyCode) {
        this.uniqueAgencyCode = uniqueAgencyCode;
    }

    
    @Column(name="agency_name", length=550)
    public String getAgencyName() {
        return this.agencyName;
    }
    
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    
    @Column(name="hierarchy_level", length=30)
    public String getHierarchyLevel() {
        return this.hierarchyLevel;
    }
    
    public void setHierarchyLevel(String hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    
    @Column(name="state_name", length=50)
    public String getStateName() {
        return this.stateName;
    }
    
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    
    @Column(name="state_lgd")
    public Integer getStateLgd() {
        return this.stateLgd;
    }
    
    public void setStateLgd(Integer stateLgd) {
        this.stateLgd = stateLgd;
    }

    
    @Column(name="district_name", length=50)
    public String getDistrictName() {
        return this.districtName;
    }
    
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    
    @Column(name="district_lgd")
    public Integer getDistrictLgd() {
        return this.districtLgd;
    }
    
    public void setDistrictLgd(Integer districtLgd) {
        this.districtLgd = districtLgd;
    }

    
    @Column(name="block_name", length=50)
    public String getBlockName() {
        return this.blockName;
    }
    
    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    
    @Column(name="block_lgd")
    public Integer getBlockLgd() {
        return this.blockLgd;
    }
    
    public void setBlockLgd(Integer blockLgd) {
        this.blockLgd = blockLgd;
    }

    
    @Column(name="panchayat_name", length=50)
    public String getPanchayatName() {
        return this.panchayatName;
    }
    
    public void setPanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
    }

    
    @Column(name="panchayat_lgd")
    public Integer getPanchayatLgd() {
        return this.panchayatLgd;
    }
    
    public void setPanchayatLgd(Integer panchayatLgd) {
        this.panchayatLgd = panchayatLgd;
    }

    
    @Column(name="village_name", length=50)
    public String getVillageName() {
        return this.villageName;
    }
    
    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    
    @Column(name="village_lgd")
    public Integer getVillageLgd() {
        return this.villageLgd;
    }
    
    public void setVillageLgd(Integer villageLgd) {
        this.villageLgd = villageLgd;
    }

    
    @Column(name="st_code")
    public Integer getStCode() {
        return this.stCode;
    }
    
    public void setStCode(Integer stCode) {
        this.stCode = stCode;
    }

    
    @Column(name="dcode")
    public Integer getDcode() {
        return this.dcode;
    }
    
    public void setDcode(Integer dcode) {
        this.dcode = dcode;
    }

    
    @Column(name="bcode")
    public Integer getBcode() {
        return this.bcode;
    }
    
    public void setBcode(Integer bcode) {
        this.bcode = bcode;
    }

    
    @Column(name="gcode")
    public Integer getGcode() {
        return this.gcode;
    }
    
    public void setGcode(Integer gcode) {
        this.gcode = gcode;
    }

    
    @Column(name="vcode")
    public Integer getVcode() {
        return this.vcode;
    }
    
    public void setVcode(Integer vcode) {
        this.vcode = vcode;
    }




}
