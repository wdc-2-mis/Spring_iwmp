package app.watershedyatra.model;

import java.util.Date;
import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;

@Entity
@Table(name = "pre_yatra_preparation", schema = "public")
public class PreYatraPreparation {

    private Integer prepId;
    private IwmpState iwmpState;
    private IwmpDistrict iwmpDistrict;
    private IwmpBlock iwmpBlock;
    private IwmpGramPanchayat iwmpGramPanchayat;
    private IwmpVillage iwmpVillage;
    private String status;
    private String preYatraType;
    private Date createdDate;
    private String createdBy;
    private String requestedIp;
    private String updatedBy;
    private Date updatedDate;

    public PreYatraPreparation() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prep_id", unique = true, nullable = false)
    public Integer getPrepId() {
        return prepId;
    }

    public void setPrepId(Integer prepId) {
        this.prepId = prepId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_code")
    public IwmpState getIwmpState() {
        return iwmpState;
    }

    public void setIwmpState(IwmpState iwmpState) {
        this.iwmpState = iwmpState;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dcode")
    public IwmpDistrict getIwmpDistrict() {
        return iwmpDistrict;
    }

    public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
        this.iwmpDistrict = iwmpDistrict;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bcode")
    public IwmpBlock getIwmpBlock() {
        return iwmpBlock;
    }

    public void setIwmpBlock(IwmpBlock iwmpBlock) {
        this.iwmpBlock = iwmpBlock;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gcode")
    public IwmpGramPanchayat getIwmpGramPanchayat() {
        return iwmpGramPanchayat;
    }

    public void setIwmpGramPanchayat(IwmpGramPanchayat iwmpGramPanchayat) {
        this.iwmpGramPanchayat = iwmpGramPanchayat;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode", unique = true)
    public IwmpVillage getIwmpVillage() {
        return iwmpVillage;
    }

    public void setIwmpVillage(IwmpVillage iwmpVillage) {
        this.iwmpVillage = iwmpVillage;
    }

    @Column(name = "status", length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "preyatra_type", length = 25)
    public String getPreYatraType() {
        return preYatraType;
    }

    public void setPreYatraType(String preYatraType) {
        this.preYatraType = preYatraType;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "created_by", length = 25)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "requested_ip", length = 25)
    public String getRequestedIp() {
        return requestedIp;
    }

    public void setRequestedIp(String requestedIp) {
        this.requestedIp = requestedIp;
    }

    @Column(name = "updated_by", length = 25)
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_date")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
