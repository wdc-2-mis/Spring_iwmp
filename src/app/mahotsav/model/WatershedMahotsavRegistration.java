package app.mahotsav.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "watershed_mahotsav_registration", schema = "public")
public class WatershedMahotsavRegistration implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer mahotsavRegId;
    private String regName;
    private Integer phno;
    private String email;
    private String address;
    private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    
    
    public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public WatershedMahotsavRegistration() {
    }

    public WatershedMahotsavRegistration(Integer mahotsavRegId, String regName, Integer phno, String email, String address, String requestedIp , 
    		String updatedBy, Date updatedDate, String createdBy, Date createdDate) 
    {
        this.mahotsavRegId = mahotsavRegId;
        this.regName = regName;
        this.phno = phno;
        this.email = email;
        this.address = address;
        this.requestedIp = requestedIp;
        this.updatedBy =updatedBy;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mahotsav_reg_id", unique = true, nullable = false)
    public Integer getMahotsavRegId() {
        return this.mahotsavRegId;
    }

    public void setMahotsavRegId(Integer mahotsavRegId) {
        this.mahotsavRegId = mahotsavRegId;
    }

    @Column(name = "reg_name", length = 100)
    public String getRegName() {
        return this.regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    @Column(name = "phno")
    public Integer getPhno() {
        return this.phno;
    }

    public void setPhno(Integer phno) {
        this.phno = phno;
    }

    @Column(name = "email", length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "address", length = 200)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "requested_ip", length=20)
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	@Column(name = "updated_by", length=25)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="updated_date", length=13)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "created_by", length=25)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="created_date", length=13)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}