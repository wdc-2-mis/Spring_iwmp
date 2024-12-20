package app.model.outcome;
// Generated 4 May, 2022 2:59:48 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

import app.model.MDepartmentScheme;

/**
 * FpoDetail generated by hbm2java
 */
@Entity
@Table(name="fpo_detail"
    ,schema="public"
)
public class FpoDetail  implements java.io.Serializable {


     private int fpoDetailId;
     private FpoMain fpoMain;
     private MDepartmentScheme MDepartmentScheme;
     private String fpoName;
     private Integer noOfFarmerAssociated;
     private BigDecimal turnover;
     private Date createdOn;
     private String requestIp;
     private Date updatedOn;
     private String createdBy;
     private String registrationNo;
     private Date registrationDate;
     private Integer noOfMembers;
     private Integer deptorganisation;
     private Set<FpoCoreaactivityDetail> fpoCoreaactivityDetails = new HashSet<FpoCoreaactivityDetail>(0);

    public FpoDetail() {
    }

	
    public FpoDetail(int fpoDetailId) {
        this.fpoDetailId = fpoDetailId;
    }
    public FpoDetail(int fpoDetailId, FpoMain fpoMain, MDepartmentScheme MDepartmentScheme, String fpoName, Integer noOfFarmerAssociated, BigDecimal turnover, Date createdOn, String requestIp, Date updatedOn, String createdBy, String registrationNo, Date registrationDate, Integer noOfMembers, Set<FpoCoreaactivityDetail> fpoCoreaactivityDetails) {
       this.fpoDetailId = fpoDetailId;
       this.fpoMain = fpoMain;
       this.MDepartmentScheme = MDepartmentScheme;
       this.fpoName = fpoName;
       this.noOfFarmerAssociated = noOfFarmerAssociated;
       this.turnover = turnover;
       this.createdOn = createdOn;
       this.requestIp = requestIp;
       this.updatedOn = updatedOn;
       this.createdBy = createdBy;
       this.registrationNo = registrationNo;
       this.registrationDate = registrationDate;
       this.noOfMembers = noOfMembers;
       this.fpoCoreaactivityDetails = fpoCoreaactivityDetails;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fpo_detail_id", unique=true, nullable=false)
    public int getFpoDetailId() {
        return this.fpoDetailId;
    }
    
    public void setFpoDetailId(int fpoDetailId) {
        this.fpoDetailId = fpoDetailId;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="fpo_id")
    public FpoMain getFpoMain() {
        return this.fpoMain;
    }
    
    public void setFpoMain(FpoMain fpoMain) {
        this.fpoMain = fpoMain;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="dept_org")
    public MDepartmentScheme getMDepartmentScheme() {
        return this.MDepartmentScheme;
    }
    
    public void setMDepartmentScheme(MDepartmentScheme MDepartmentScheme) {
        this.MDepartmentScheme = MDepartmentScheme;
    }

    @Column(name="fpo_name", length=150)
    public String getFpoName() {
        return this.fpoName;
    }
    
    public void setFpoName(String fpoName) {
        this.fpoName = fpoName;
    }

    
    @Column(name="no_of_farmer_associated")
    public Integer getNoOfFarmerAssociated() {
        return this.noOfFarmerAssociated;
    }
    
    public void setNoOfFarmerAssociated(Integer noOfFarmerAssociated) {
        this.noOfFarmerAssociated = noOfFarmerAssociated;
    }

    
    @Column(name="turnover", precision=5)
    public BigDecimal getTurnover() {
        return this.turnover;
    }
    
    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="created_on", length=13)
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    
    @Column(name="request_ip", length=20)
    public String getRequestIp() {
        return this.requestIp;
    }
    
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="updated_on", length=13)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }
    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    
    @Column(name="created_by", length=20)
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    
    @Column(name="registration_no")
    public String getRegistrationNo() {
        return this.registrationNo;
    }
    
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="registration_date", length=13)
    public Date getRegistrationDate() {
        return this.registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    
    @Column(name="no_of_members")
    public Integer getNoOfMembers() {
        return this.noOfMembers;
    }
    
    public void setNoOfMembers(Integer noOfMembers) {
        this.noOfMembers = noOfMembers;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="fpoDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<FpoCoreaactivityDetail> getFpoCoreaactivityDetails() {
        return this.fpoCoreaactivityDetails;
    }
    
    public void setFpoCoreaactivityDetails(Set<FpoCoreaactivityDetail> fpoCoreaactivityDetails) {
        this.fpoCoreaactivityDetails = fpoCoreaactivityDetails;
    }




}


