package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="slna_coordinators",schema="public")
public class SLNACoordinators {
	
	private int slnaCoordinatorId;
    private IwmpState iwmpState;
    private String coordinatorName;
    private String email;
    private long mobile;
    
    
	public SLNACoordinators() {
	}


	public SLNACoordinators(int slnaCoordinatorId, IwmpState iwmpState, String coordinatorName, String email,
			long mobile) {
		super();
		this.slnaCoordinatorId = slnaCoordinatorId;
		this.iwmpState = iwmpState;
		this.coordinatorName = coordinatorName;
		this.email = email;
		this.mobile = mobile;
	}

	@Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="slna_coordinator_id", unique=true, nullable=false)
	public int getSlnaCoordinatorId() {
		return slnaCoordinatorId;
	}


	public void setSlnaCoordinatorId(int slnaCoordinatorId) {
		this.slnaCoordinatorId = slnaCoordinatorId;
	}

	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="st_code", nullable=false)
	public IwmpState getIwmpState() {
		return iwmpState;
	}


	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	@Column(name="coordinator_name")
	public String getCoordinatorName() {
		return coordinatorName;
	}


	public void setCoordinatorName(String coordinatorName) {
		this.coordinatorName = coordinatorName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public long getMobile() {
		return mobile;
	}


	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	
	

    
}
