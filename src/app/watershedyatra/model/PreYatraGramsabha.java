package app.watershedyatra.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "pre_yatra_gramsabha", schema = "public")
public class PreYatraGramsabha {

    private Integer gramsabhaId;
    private PreYatraPreparation preYatraPreparation;
    private Date gramsabhaDate;
    private String gramsabhaPhoto1;
    private String gramsabhaPhoto1Longitude;
    private String gramsabhaPhoto1Latitude;
    private Date gramsabhaPhoto1Time;
    private String gramsabhaPhoto2;
    private String gramsabhaPhoto2Longitude;
    private String gramsabhaPhoto2Latitude;
    private Date gramsabhaPhoto2Time;
    private Date createdDate;
    private String createdBy;
    private String requestedIp;
    private Integer gramsabhaParticipants;

    public PreYatraGramsabha() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gramsabha_id", unique = true, nullable = false)
    public Integer getGramsabhaId() {
        return gramsabhaId;
    }

    public void setGramsabhaId(Integer gramsabhaId) {
        this.gramsabhaId = gramsabhaId;
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
    @Column(name = "gramsabha_date")
    public Date getGramsabhaDate() {
        return gramsabhaDate;
    }

    public void setGramsabhaDate(Date gramsabhaDate) {
        this.gramsabhaDate = gramsabhaDate;
    }
    @Column(name ="gramsabha_photo1")
	public String getGramsabhaPhoto1() {
		return gramsabhaPhoto1;
	}

	public void setGramsabhaPhoto1(String gramsabhaPhoto1) {
		this.gramsabhaPhoto1 = gramsabhaPhoto1;
	}
	@Column(name ="gramsabha_photo1_longitute")
	public String getGramsabhaPhoto1Longitude() {
		return gramsabhaPhoto1Longitude;
	}

	public void setGramsabhaPhoto1Longitude(String gramsabhaPhoto1Longitude) {
		this.gramsabhaPhoto1Longitude = gramsabhaPhoto1Longitude;
	}
	@Column(name ="gramsabha_photo1_langitute")
	public String getGramsabhaPhoto1Latitude() {
		return gramsabhaPhoto1Latitude;
	}

	public void setGramsabhaPhoto1Latitude(String gramsabhaPhoto1Latitude) {
		this.gramsabhaPhoto1Latitude = gramsabhaPhoto1Latitude;
	}

	@Column(name ="gramsabha_photo1_time")
	public Date getGramsabhaPhoto1Time() {
		return gramsabhaPhoto1Time;
	}

	public void setGramsabhaPhoto1Time(Date gramsabhaPhoto1Time) {
		this.gramsabhaPhoto1Time = gramsabhaPhoto1Time;
	}
	@Column(name ="gramsabha_photo2")
	public String getGramsabhaPhoto2() {
		return gramsabhaPhoto2;
	}

	public void setGramsabhaPhoto2(String gramsabhaPhoto2) {
		this.gramsabhaPhoto2 = gramsabhaPhoto2;
	}
	@Column(name ="gramsabha_photo2_longitute")
	public String getGramsabhaPhoto2Longitude() {
		return gramsabhaPhoto2Longitude;
	}

	public void setGramsabhaPhoto2Longitude(String gramsabhaPhoto2Longitude) {
		this.gramsabhaPhoto2Longitude = gramsabhaPhoto2Longitude;
	}
	@Column(name ="gramsabha_photo2_langitute")
	public String getGramsabhaPhoto2Latitude() {
		return gramsabhaPhoto2Latitude;
	}

	public void setGramsabhaPhoto2Latitude(String gramsabhaPhoto2Latitude) {
		this.gramsabhaPhoto2Latitude = gramsabhaPhoto2Latitude;
	}
	@Column(name ="gramsabha_photo2_time")
	public Date getGramsabhaPhoto2Time() {
		return gramsabhaPhoto2Time;
	}

	public void setGramsabhaPhoto2Time(Date gramsabhaPhoto2Time) {
		this.gramsabhaPhoto2Time = gramsabhaPhoto2Time;
	}
	@Column(name ="created_date")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name ="created_by")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name ="requested_ip")
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}
	@Column(name ="gramsabha_participants")
	public Integer getGramsabhaParticipants() {
		return gramsabhaParticipants;
	}

	public void setGramsabhaParticipants(Integer gramsabhaParticipants) {
		this.gramsabhaParticipants = gramsabhaParticipants;
	}

    
}
