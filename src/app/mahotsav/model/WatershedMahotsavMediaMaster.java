package app.mahotsav.model;

import javax.persistence.*;

@Entity
@Table(name = "watershed_mahotsav_media_master", schema = "public")
public class WatershedMahotsavMediaMaster implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer mediaId;
    private String mediaName;
    
    
    public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public WatershedMahotsavMediaMaster() {
    }

    public WatershedMahotsavMediaMaster(Integer mediaId, String mediaName) {
        this.mediaId = mediaId;
        this.mediaName = mediaName;
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
    
}