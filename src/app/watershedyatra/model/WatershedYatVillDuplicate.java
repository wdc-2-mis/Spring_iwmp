package app.watershedyatra.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.model.master.IwmpVillage;

@Entity
@Table(name = "watershed_yatra_village_duplicate")
public class WatershedYatVillDuplicate {
	
		
	    private Integer watershedId;
	    private IwmpVillage iwmpVillage;
	    private String villageName;
	    
	    @Id 
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="watershed_id", unique=true, nullable=false)   
		public Integer getWatershedId() {
			return watershedId;
		}
		public void setWatershedId(Integer watershedId) {
			this.watershedId = watershedId;
		}
		
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="vcode")
		public IwmpVillage getIwmpVillage() {
			return iwmpVillage;
		}
		public void setIwmpVillage(IwmpVillage iwmpVillage) {
			this.iwmpVillage = iwmpVillage;
		}
		
		public String getVillageName() {
			return villageName;
		}
		public void setVillageName(String villageName) {
			this.villageName = villageName;
		}
	    
	    
	    
	   

}
