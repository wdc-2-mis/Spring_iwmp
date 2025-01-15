package app.watershedyatra.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.model.Outcome2Data;

@Entity
@Table(name="m_cultural_activity" ,schema="public")
public class MCulturalActivity {
	
	
	
	private Integer culturalId;
    private String culturalName;
    private Set<WatershedYatVill> watershedYatVill = new HashSet<WatershedYatVill>(0);
    
    public MCulturalActivity() {}
    
    public MCulturalActivity(Integer culturalId, String culturalName, Set<WatershedYatVill> watershedYatVill) {
    	
    	this.culturalId=culturalId;
    	this.culturalName=culturalName;
    	this.watershedYatVill=watershedYatVill;
    	 
        
    }
    
    
    @Id 
    @Column(name="cultural_id", unique=true, nullable=false)
	public Integer getCulturalId() {
		return culturalId;
	}

	public void setCulturalId(Integer culturalId) {
		this.culturalId = culturalId;
	}

	@Column(name="cultural_name", nullable=false, length=100)
	public String getCulturalName() {
		return culturalName;
	}

	public void setCulturalName(String culturalName) {
		this.culturalName = culturalName;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="mCulturalActivity")
	public Set<WatershedYatVill> getWatershedYatVill() {
		return watershedYatVill;
	}

	public void setWatershedYatVill(Set<WatershedYatVill> watershedYatVill) {
		this.watershedYatVill = watershedYatVill;
	}

}
