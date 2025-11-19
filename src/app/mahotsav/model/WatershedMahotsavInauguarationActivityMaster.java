package app.mahotsav.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="watershed_mahotsav_inauguaration_activity_master" ,schema="public")
public class WatershedMahotsavInauguarationActivityMaster implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer actId;
    private String actName;
    
    
    public WatershedMahotsavInauguarationActivityMaster() {
    }

	
    public WatershedMahotsavInauguarationActivityMaster(Integer actId, String actName) {
        this.actId = actId;
        this.actName = actName;
    }


    @Id 
    @Column(name="act_id", unique=true, nullable=false, precision=2, scale=0)
	public Integer getActId() {
		return actId;
	}


	public void setActId(Integer actId) {
		this.actId = actId;
	}

	 @Column(name="act_name", nullable=false)
	public String getActName() {
		return actName;
	}


	public void setActName(String actName) {
		this.actName = actName;
	}
    
    
    
	
	
	
}
