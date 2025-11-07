package app.mahotsav.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "watershed_mahotsav_media_master", schema = "public")
public class WatershedMahotsavMediaMaster implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer mediaId;
    private String mediaName;
    private Set<WatershedMahotsavVideoDetails> watershedMahotsavVideoDetails = new HashSet<WatershedMahotsavVideoDetails>(0);
    
    public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public WatershedMahotsavMediaMaster() {
    }

    public WatershedMahotsavMediaMaster(Integer mediaId, String mediaName, Set<WatershedMahotsavVideoDetails> watershedMahotsavVideoDetails) {
        this.mediaId = mediaId;
        this.mediaName = mediaName;
        this.watershedMahotsavVideoDetails = watershedMahotsavVideoDetails;
    }
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id", unique = true, nullable = false)
    public int getMediaId() {
        return this.mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    @Column(name = "media_name", length = 50)
    public String getMediaName() {
        return this.mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="mediaMaster")
	public Set<WatershedMahotsavVideoDetails> getWatershedMahotsavVideoDetails() {
		return watershedMahotsavVideoDetails;
	}

	public void setWatershedMahotsavVideoDetails(Set<WatershedMahotsavVideoDetails> watershedMahotsavVideoDetails) {
		this.watershedMahotsavVideoDetails = watershedMahotsavVideoDetails;
	}
    
    
    
}