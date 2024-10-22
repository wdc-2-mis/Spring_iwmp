package app.projectevaluation.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="wdcpmksy_cropped_details_2" ,schema="public")
public class WdcpmksyCroppedDetails2 implements java.io.Serializable{
	
	
	private int croppedDetails2Id;								//  cropped_details_2_id
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;					// project_profile_id
	//private Character projectControlled;               //   project_controlled
	private BigDecimal nillSingle;						//	nill_single 
	private BigDecimal singelDoublemore;		//   singel_doublemore 
	private BigDecimal plantationCover;		//		plantation_cover
	private BigDecimal wheat;
	private BigDecimal rice; 
	private BigDecimal pulses; 
	private BigDecimal oil_seed;			//   oil_seed 
	private BigDecimal millets; 
	private BigDecimal other;
	
	private BigDecimal control_nill_single;
	private BigDecimal control_singel_doublemore;
	private BigDecimal control_plantation_cover ;
	private BigDecimal control_wheat;
	private BigDecimal control_rice ;
	private BigDecimal control_pulses ;
	private BigDecimal control_oil_seed ;
	private BigDecimal control_millets ;
	private BigDecimal control_other ;
	
	
	
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ;
	private String requestIp; 
	
	public WdcpmksyCroppedDetails2() {
 	}

	
    public WdcpmksyCroppedDetails2(int croppedDetails2Id) {
        this.croppedDetails2Id = croppedDetails2Id;
    }
	
    public WdcpmksyCroppedDetails2(int croppedDetails2Id, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, Character projectControlled, BigDecimal nillSingle, BigDecimal singelDoublemore, BigDecimal plantationCover, BigDecimal wheat, BigDecimal rice, BigDecimal pulses,
    		BigDecimal oil_seed, BigDecimal millets, BigDecimal other, Date createdOn, String createdBy, Date updatedOn, String requestIp) {
        this.croppedDetails2Id = croppedDetails2Id;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
      //  this.projectControlled=projectControlled;
        this.nillSingle=nillSingle;
        this.singelDoublemore=singelDoublemore;
        this.plantationCover=plantationCover;
        this.wheat=wheat;
        this.rice=rice;
        this.pulses=pulses;
        this.oil_seed=oil_seed;
        this.millets=millets;
        this.other=other;
        this.createdOn=createdOn;
    	this.createdBy=createdBy;
    	this.updatedOn=updatedOn;
    	this.requestIp=requestIp;
        
    }

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="cropped_details_2_id", unique=true, nullable=false)
	public int getCroppedDetails2Id() {
		return croppedDetails2Id;
	}


	public void setCroppedDetails2Id(int croppedDetails2Id) {
		this.croppedDetails2Id = croppedDetails2Id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_profile_id")
	public WdcpmksyProjectProfileEvaluation getWdcpmksyProjectProfileEvaluation() {
		return wdcpmksyProjectProfileEvaluation;
	}


	public void setWdcpmksyProjectProfileEvaluation(WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation) {
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
	}

	/*
	 * @Column(name="project_controlled", length=1) public Character
	 * getProjectControlled() { return projectControlled; }
	 * 
	 * 
	 * public void setProjectControlled(Character projectControlled) {
	 * this.projectControlled = projectControlled; }
	 */

	@Column(name="nill_single", precision=20)
	public BigDecimal getNillSingle() {
		return nillSingle;
	}


	public void setNillSingle(BigDecimal nillSingle) {
		this.nillSingle = nillSingle;
	}

	@Column(name="singel_doublemore", precision=20)
	public BigDecimal getSingelDoublemore() {
		return singelDoublemore;
	}


	public void setSingelDoublemore(BigDecimal singelDoublemore) {
		this.singelDoublemore = singelDoublemore;
	}

	@Column(name="plantation_cover", precision=20)
	public BigDecimal getPlantationCover() {
		return plantationCover;
	}


	public void setPlantationCover(BigDecimal plantationCover) {
		this.plantationCover = plantationCover;
	}

	@Column(name="wheat", precision=20)
	public BigDecimal getWheat() {
		return wheat;
	}


	public void setWheat(BigDecimal wheat) {
		this.wheat = wheat;
	}

	@Column(name="rice", precision=20)
	public BigDecimal getRice() {
		return rice;
	}


	public void setRice(BigDecimal rice) {
		this.rice = rice;
	}

	@Column(name="pulses", precision=20)
	public BigDecimal getPulses() {
		return pulses;
	}


	public void setPulses(BigDecimal pulses) {
		this.pulses = pulses;
	}

	@Column(name="oil_seed", precision=20)
	public BigDecimal getOil_seed() {
		return oil_seed;
	}


	public void setOil_seed(BigDecimal oil_seed) {
		this.oil_seed = oil_seed;
	}

	@Column(name="millets", precision=20)
	public BigDecimal getMillets() {
		return millets;
	}


	public void setMillets(BigDecimal millets) {
		this.millets = millets;
	}

	@Column(name="other", precision=20)
	public BigDecimal getOther() {
		return other;
	}


	public void setOther(BigDecimal other) {
		this.other = other;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="created_on")
	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="created_by", length=20)
	public String getCreatedBy() {
		return createdBy;
	}

	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name="request_ip", length=20)
	public String getRequestIp() {
		return requestIp;
	}


	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	@Column(name="control_nill_single", precision=20)
	public BigDecimal getControl_nill_single() {
		return control_nill_single;
	}


	public void setControl_nill_single(BigDecimal control_nill_single) {
		this.control_nill_single = control_nill_single;
	}

	@Column(name="control_singel_doublemore", precision=20)
	public BigDecimal getControl_singel_doublemore() {
		return control_singel_doublemore;
	}


	public void setControl_singel_doublemore(BigDecimal control_singel_doublemore) {
		this.control_singel_doublemore = control_singel_doublemore;
	}

	@Column(name="control_plantation_cover", precision=20)
	public BigDecimal getControl_plantation_cover() {
		return control_plantation_cover;
	}


	public void setControl_plantation_cover(BigDecimal control_plantation_cover) {
		this.control_plantation_cover = control_plantation_cover;
	}

	@Column(name="control_wheat", precision=20)
	public BigDecimal getControl_wheat() {
		return control_wheat;
	}


	public void setControl_wheat(BigDecimal control_wheat) {
		this.control_wheat = control_wheat;
	}

	@Column(name="control_rice", precision=20)
	public BigDecimal getControl_rice() {
		return control_rice;
	}


	public void setControl_rice(BigDecimal control_rice) {
		this.control_rice = control_rice;
	}

	@Column(name="control_pulses", precision=20)
	public BigDecimal getControl_pulses() {
		return control_pulses;
	}


	public void setControl_pulses(BigDecimal control_pulses) {
		this.control_pulses = control_pulses;
	}

	@Column(name="control_oil_seed", precision=20)
	public BigDecimal getControl_oil_seed() {
		return control_oil_seed;
	}


	public void setControl_oil_seed(BigDecimal control_oil_seed) {
		this.control_oil_seed = control_oil_seed;
	}

	@Column(name="control_millets", precision=20)
	public BigDecimal getControl_millets() {
		return control_millets;
	}


	public void setControl_millets(BigDecimal control_millets) {
		this.control_millets = control_millets;
	}

	@Column(name="control_other", precision=20)
	public BigDecimal getControl_other() {
		return control_other;
	}


	public void setControl_other(BigDecimal control_other) {
		this.control_other = control_other;
	}
	
	
	
	
	
	
	
	

}
