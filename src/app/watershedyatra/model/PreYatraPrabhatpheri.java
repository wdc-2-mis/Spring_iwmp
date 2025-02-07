package app.watershedyatra.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "pre_yatra_prabhatpheri", schema = "public")
public class PreYatraPrabhatpheri {

    private Integer prabhatpheriId;
    private PreYatraPreparation preYatraPreparation;
    private Date prabhatpheriDate;
    private String prabhatpheriPhoto1;
    private String prabhatpheriPhoto1Longitude;
    private String prabhatpheriPhoto1Latitude;
    private Date prabhatpheriPhoto1Time;
    private String prabhatpheriPhoto2;
    private String prabhatpheriPhoto2Longitude;
    private String prabhatpheriPhoto2Latitude;
    private Date prabhatpheriPhoto2Time;
    private Date createdDate;
    private String createdBy;
    private String requestedIp;

    public PreYatraPrabhatpheri() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prabhatpheri_id", unique = true, nullable = false)
    public Integer getPrabhatpheriId() {
        return prabhatpheriId;
    }

    public void setPrabhatpheriId(Integer prabhatpheriId) {
        this.prabhatpheriId = prabhatpheriId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prep_id")
    public PreYatraPreparation getPreYatraPreparation() {
        return preYatraPreparation;
    }

    public void setPreYatraPreparation(PreYatraPreparation preYatraPreparation) {
        this.preYatraPreparation = preYatraPreparation;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "prabhatpheri_date")
    public Date getPrabhatpheriDate() {
        return prabhatpheriDate;
    }

    public void setPrabhatpheriDate(Date prabhatpheriDate) {
        this.prabhatpheriDate = prabhatpheriDate;
    }
    
    @Column(name = "prabhatpheri_photo1")
	public String getPrabhatpheriPhoto1() {
		return prabhatpheriPhoto1;
	}

	public void setPrabhatpheriPhoto1(String prabhatpheriPhoto1) {
		this.prabhatpheriPhoto1 = prabhatpheriPhoto1;
	}

	@Column(name = "prabhatpheri_photo1_longitute")
	public String getPrabhatpheriPhoto1Longitude() {
		return prabhatpheriPhoto1Longitude;
	}

	public void setPrabhatpheriPhoto1Longitude(String prabhatpheriPhoto1Longitude) {
		this.prabhatpheriPhoto1Longitude = prabhatpheriPhoto1Longitude;
	}
	
	@Column(name = "prabhatpheri_photo1_langitute")
    public String getPrabhatpheriPhoto1Latitude() {
		return prabhatpheriPhoto1Latitude;
	}

	public void setPrabhatpheriPhoto1Latitude(String prabhatpheriPhoto1Latitude) {
		this.prabhatpheriPhoto1Latitude = prabhatpheriPhoto1Latitude;
	}

	@Column(name = "prabhatpheri_photo1_time")
	public Date getPrabhatpheriPhoto1Time() {
		return prabhatpheriPhoto1Time;
	}

	public void setPrabhatpheriPhoto1Time(Date prabhatpheriPhoto1Time) {
		this.prabhatpheriPhoto1Time = prabhatpheriPhoto1Time;
	}

	@Column(name = "prabhatpheri_photo2")
	public String getPrabhatpheriPhoto2() {
		return prabhatpheriPhoto2;
	}

	public void setPrabhatpheriPhoto2(String prabhatpheriPhoto2) {
		this.prabhatpheriPhoto2 = prabhatpheriPhoto2;
	}

	@Column(name = "prabhatpheri_photo2_longitute")
	public String getPrabhatpheriPhoto2Longitude() {
		return prabhatpheriPhoto2Longitude;
	}

	public void setPrabhatpheriPhoto2Longitude(String prabhatpheriPhoto2Longitude) {
		this.prabhatpheriPhoto2Longitude = prabhatpheriPhoto2Longitude;
	}

	@Column(name = "prabhatpheri_photo2_langitute")
	public String getPrabhatpheriPhoto2Latitude() {
		return prabhatpheriPhoto2Latitude;
	}

	public void setPrabhatpheriPhoto2Latitude(String prabhatpheriPhoto2Latitude) {
		this.prabhatpheriPhoto2Latitude = prabhatpheriPhoto2Latitude;
	}

	@Column(name = "prabhatpheri_photo2_time")
	public Date getPrabhatpheriPhoto2Time() {
		return prabhatpheriPhoto2Time;
	}

	public void setPrabhatpheriPhoto2Time(Date prabhatpheriPhoto2Time) {
		this.prabhatpheriPhoto2Time = prabhatpheriPhoto2Time;
	}

	@Column(name = "created_date")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "created_by")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "requested_ip")
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

    
}
