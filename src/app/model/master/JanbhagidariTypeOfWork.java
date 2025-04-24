package app.model.master;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "janbhagidari_m_typeofwork", schema = "public")
public class JanbhagidariTypeOfWork {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private Integer workId;

    @Column(name = "work_desc", length = 100)
    private String workDesc;

    @Column(name = "last_updated_by", length = 20)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    @Temporal(TemporalType.DATE)
    private Date lastUpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    public JanbhagidariTypeOfWork() {}
    
    public JanbhagidariTypeOfWork(String workDesc, String lastUpdatedBy, Date lastUpdatedDate, String requestIp) {
        this.workDesc = workDesc;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
        this.requestIp = requestIp;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
}
