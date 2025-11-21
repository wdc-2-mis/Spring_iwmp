package app.mahotsav.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;

@Entity
@Table(name = "watershed_mahotsav_prabhatpheri", schema = "public")
public class MahotsavPrabhatPheri implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prabhatpheri_id", unique = true, nullable = false)
    private Integer prabhatpheriId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_code")
    private IwmpState iwmpState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dcode")
    private IwmpDistrict iwmpDistrict;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bcode")
    private IwmpBlock iwmpBlock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode")
    private IwmpVillage iwmpVillage;

    @Column(name = "prabhatpheri_date")
    private Date prabhatpheriDate;

    @Column(name = "male_participants")
    private Integer maleParticipants;

    @Column(name = "female_participants")
    private Integer femaleParticipants;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date", length = 13)
    private Date createdOn;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_date", length = 13)
    private Date updatedOn;

    @Column(name = "requested_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 25)
    private String createdBy;

    @Column(name = "status")
    private Character status;

    // FIXED mappedBy → must match child's property name
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wmPrabhatPheri", cascade = CascadeType.ALL)
    private Set<MahotsavPrabhatPheriPhoto> wmPrabhatPheriPhoto = new HashSet<>();

    // Getters & Setters

    public Integer getPrabhatpheriId() {
        return prabhatpheriId;
    }

    public void setPrabhatpheriId(Integer prabhatpheriId) {
        this.prabhatpheriId = prabhatpheriId;
    }

    public IwmpState getIwmpState() {
        return iwmpState;
    }

    public void setIwmpState(IwmpState iwmpState) {
        this.iwmpState = iwmpState;
    }

    public IwmpDistrict getIwmpDistrict() {
        return iwmpDistrict;
    }

    public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
        this.iwmpDistrict = iwmpDistrict;
    }

    public IwmpBlock getIwmpBlock() {
        return iwmpBlock;
    }

    public void setIwmpBlock(IwmpBlock iwmpBlock) {
        this.iwmpBlock = iwmpBlock;
    }

    public IwmpVillage getIwmpVillage() {
        return iwmpVillage;
    }

    public void setIwmpVillage(IwmpVillage iwmpVillage) {
        this.iwmpVillage = iwmpVillage;
    }

    public Date getPrabhatpheriDate() {
        return prabhatpheriDate;
    }

    public void setPrabhatpheriDate(Date prabhatpheriDate) {
        this.prabhatpheriDate = prabhatpheriDate;
    }

    public Integer getMaleParticipants() {
        return maleParticipants;
    }

    public void setMaleParticipants(Integer maleParticipants) {
        this.maleParticipants = maleParticipants;
    }

    public Integer getFemaleParticipants() {
        return femaleParticipants;
    }

    public void setFemaleParticipants(Integer femaleParticipants) {
        this.femaleParticipants = femaleParticipants;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Set<MahotsavPrabhatPheriPhoto> getWmPrabhatPheriPhoto() {
        return wmPrabhatPheriPhoto;
    }

    public void setWmPrabhatPheriPhoto(Set<MahotsavPrabhatPheriPhoto> wmPrabhatPheriPhoto) {
        this.wmPrabhatPheriPhoto = wmPrabhatPheriPhoto;
    }
}
