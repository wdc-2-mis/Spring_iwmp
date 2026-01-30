package app.punarutthan.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.watershedyatra.model.WatershedYatVill;

@Entity
@Table(name="m_structure" ,schema="public")
public class MStructure implements java.io.Serializable{
	
	private Integer structureId;
    private String structureDesc;
    private Set<Wdcpmksy1PunarutthanPlan> wdcpmksy1PunarutthanPlan = new HashSet<Wdcpmksy1PunarutthanPlan>(0);
    private Set<Wdcpmksy1PunarutthanPlanImplementation> wdcpmksy1PunarutthanPlanImplementation = new HashSet<Wdcpmksy1PunarutthanPlanImplementation>(0);
    
    public MStructure() {}
    
    public MStructure(Integer structureId, String structureDesc, Set<Wdcpmksy1PunarutthanPlan> wdcpmksy1PunarutthanPlan,
    		Set<Wdcpmksy1PunarutthanPlanImplementation> wdcpmksy1PunarutthanPlanImplementation) {
    	
    	this.structureId=structureId;
    	this.structureDesc=structureDesc;
    	this.wdcpmksy1PunarutthanPlan=wdcpmksy1PunarutthanPlan;
    	this.wdcpmksy1PunarutthanPlanImplementation=wdcpmksy1PunarutthanPlanImplementation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="structure_id", unique=true, nullable=false)
	public Integer getStructureId() {
		return structureId;
	}

	public void setStructureId(Integer structureId) {
		this.structureId = structureId;
	}

	@Column(name="structure_desc", nullable=false, length=250)
	public String getStructureDesc() {
		return structureDesc;
	}

	public void setStructureDesc(String structureDesc) {
		this.structureDesc = structureDesc;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="mStructure")
	public Set<Wdcpmksy1PunarutthanPlan> getWdcpmksy1PunarutthanPlan() {
		return wdcpmksy1PunarutthanPlan;
	}

	public void setWdcpmksy1PunarutthanPlan(Set<Wdcpmksy1PunarutthanPlan> wdcpmksy1PunarutthanPlan) {
		this.wdcpmksy1PunarutthanPlan = wdcpmksy1PunarutthanPlan;
	}
    
	@OneToMany(fetch=FetchType.LAZY, mappedBy="mStructure")
	public Set<Wdcpmksy1PunarutthanPlanImplementation> getWdcpmksy1PunarutthanPlanImplementation() {
		return wdcpmksy1PunarutthanPlanImplementation;
	}

	public void setWdcpmksy1PunarutthanPlanImplementation(
			Set<Wdcpmksy1PunarutthanPlanImplementation> wdcpmksy1PunarutthanPlanImplementation) {
		this.wdcpmksy1PunarutthanPlanImplementation = wdcpmksy1PunarutthanPlanImplementation;
	}
    
    

}
