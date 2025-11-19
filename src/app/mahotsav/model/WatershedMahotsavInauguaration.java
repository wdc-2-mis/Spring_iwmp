package app.mahotsav.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.BlsOutDetail;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;

@Entity
@Table(name="watershed_mahotsav_inauguaration" ,schema="public")
public class WatershedMahotsavInauguaration implements java.io.Serializable {
	
	
	private int inauguarationId ;
	private IwmpState iwmpState;
    private IwmpDistrict iwmpDistrict;
    private IwmpBlock iwmpBlock;
    private Date inauguarationDate;
    private String inauguarationLocation;
    private Integer maleParticipants;
    private Integer femaleParticipants;
    private Integer centralMinister;
    private Integer stateMinister;
    private Integer parliamentMembers;
    private Integer legislativeAssemblyMembers;
    private Integer legislativeCouncilMembers;
    private Integer otherPublicRepresentatives;
    private Integer govOfficials;
    private Integer bhoomiPoojan;
    private Integer lokarpan ;
    private Integer shramdaanLocation;
    private Integer shramdaanParticipate;
    private Integer forestryHorticulture;
    private Integer awardedJanbhagidari;
    private Date createdOn;
    private Date updatedOn;
    private String requestIp;
    private String createdBy;
    private Character status;
    private Set<WatershedMahotsavInauguarationActPhoto> watershedMahotsavInauguarationActPhoto = new HashSet<WatershedMahotsavInauguarationActPhoto>(0);
	
    public WatershedMahotsavInauguaration() { }
    
    public WatershedMahotsavInauguaration(Integer inauguarationId) {
    	this.inauguarationId = inauguarationId;
    }
    
    public WatershedMahotsavInauguaration(Integer inauguarationId, IwmpState iwmpState, IwmpDistrict iwmpDistrict, IwmpBlock iwmpBlock, Date inauguarationDate,
    		String inauguarationLocation, Integer maleParticipants, Integer femaleParticipants, Integer centralMinister, Integer stateMinister, Integer parliamentMembers, 
    		Integer legislativeAssemblyMembers, Integer legislativeCouncilMembers, Integer otherPublicRepresentatives, Integer govOfficials, Integer bhoomiPoojan, 
    		Integer lokarpan, Integer shramdaanLocation, Integer shramdaanParticipate, Integer forestryHorticulture, Integer awardedJanbhagidari, Set<WatershedMahotsavInauguarationActPhoto> watershedMahotsavInauguarationActPhoto) {
    	
    	this.inauguarationId = inauguarationId;
    	this.iwmpState = iwmpState;
    	this.iwmpDistrict = iwmpDistrict;
    	this.iwmpBlock = iwmpBlock;
    	this.inauguarationDate = inauguarationDate;
    	this.inauguarationLocation = inauguarationLocation;
    	this.maleParticipants = maleParticipants;
    	this.femaleParticipants = femaleParticipants;
    	this.centralMinister = centralMinister;
    	this.stateMinister = stateMinister;
    	this.parliamentMembers = parliamentMembers;
    	this.legislativeAssemblyMembers = legislativeAssemblyMembers;
    	this.legislativeCouncilMembers = legislativeCouncilMembers;
    	this.otherPublicRepresentatives = otherPublicRepresentatives;
    	this.govOfficials = govOfficials;
    	this.bhoomiPoojan = bhoomiPoojan;
    	this.lokarpan = lokarpan;
    	this.shramdaanLocation = shramdaanLocation;
    	this.shramdaanParticipate=shramdaanParticipate;
    	this.forestryHorticulture=forestryHorticulture;
    	this.awardedJanbhagidari=awardedJanbhagidari;
    	this.watershedMahotsavInauguarationActPhoto=watershedMahotsavInauguarationActPhoto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="inauguaration_id", unique=true, nullable=false)
	public int getInauguarationId() {
		return inauguarationId;
	}

	public void setInauguarationId(int inauguarationId) {
		this.inauguarationId = inauguarationId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "st_code")
	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dcode")
	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "bcode")
	public IwmpBlock getIwmpBlock() {
		return iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}

	@Column(name = "inauguaration_date")
	public Date getInauguarationDate() {
		return inauguarationDate;
	}

	public void setInauguarationDate(Date inauguarationDate) {
		this.inauguarationDate = inauguarationDate;
	}
	@Column(name = "inauguaration_location")
	public String getInauguarationLocation() {
		return inauguarationLocation;
	}

	public void setInauguarationLocation(String inauguarationLocation) {
		this.inauguarationLocation = inauguarationLocation;
	}
	@Column(name = "male_participants")
	public Integer getMaleParticipants() {
		return maleParticipants;
	}

	public void setMaleParticipants(Integer maleParticipants) {
		this.maleParticipants = maleParticipants;
	}
	@Column(name = "female_participants")
	public Integer getFemaleParticipants() {
		return femaleParticipants;
	}

	public void setFemaleParticipants(Integer femaleParticipants) {
		this.femaleParticipants = femaleParticipants;
	}
	@Column(name = "central_minister")
	public Integer getCentralMinister() {
		return centralMinister;
	}

	public void setCentralMinister(Integer centralMinister) {
		this.centralMinister = centralMinister;
	}
	@Column(name = "state_minister")
	public Integer getStateMinister() {
		return stateMinister;
	}

	public void setStateMinister(Integer stateMinister) {
		this.stateMinister = stateMinister;
	}
	@Column(name = "parliament_members")
	public Integer getParliamentMembers() {
		return parliamentMembers;
	}

	public void setParliamentMembers(Integer parliamentMembers) {
		this.parliamentMembers = parliamentMembers;
	}
	@Column(name = "legislative_assembly_members")
	public Integer getLegislativeAssemblyMembers() {
		return legislativeAssemblyMembers;
	}

	public void setLegislativeAssemblyMembers(Integer legislativeAssemblyMembers) {
		this.legislativeAssemblyMembers = legislativeAssemblyMembers;
	}
	@Column(name = "legislative_council_members")
	public Integer getLegislativeCouncilMembers() {
		return legislativeCouncilMembers;
	}

	public void setLegislativeCouncilMembers(Integer legislativeCouncilMembers) {
		this.legislativeCouncilMembers = legislativeCouncilMembers;
	}
	@Column(name = "other_public_representatives")
	public Integer getOtherPublicRepresentatives() {
		return otherPublicRepresentatives;
	}

	public void setOtherPublicRepresentatives(Integer otherPublicRepresentatives) {
		this.otherPublicRepresentatives = otherPublicRepresentatives;
	}
	@Column(name = "gov_officials")
	public Integer getGovOfficials() {
		return govOfficials;
	}

	public void setGovOfficials(Integer govOfficials) {
		this.govOfficials = govOfficials;
	}
	@Column(name = "bhoomi_poojan")
	public Integer getBhoomiPoojan() {
		return bhoomiPoojan;
	}

	public void setBhoomiPoojan(Integer bhoomiPoojan) {
		this.bhoomiPoojan = bhoomiPoojan;
	}
	@Column(name = "lokarpan")
	public Integer getLokarpan() {
		return lokarpan;
	}

	public void setLokarpan(Integer lokarpan) {
		this.lokarpan = lokarpan;
	}
	@Column(name = "shramdaan_locations")
	public Integer getShramdaanLocation() {
		return shramdaanLocation;
	}

	public void setShramdaanLocation(Integer shramdaanLocation) {
		this.shramdaanLocation = shramdaanLocation;
	}
	@Column(name = "shramdaan_participate")
	public Integer getShramdaanParticipate() {
		return shramdaanParticipate;
	}

	public void setShramdaanParticipate(Integer shramdaanParticipate) {
		this.shramdaanParticipate = shramdaanParticipate;
	}
	@Column(name = "forestry_horticulture")
	public Integer getForestryHorticulture() {
		return forestryHorticulture;
	}

	public void setForestryHorticulture(Integer forestryHorticulture) {
		this.forestryHorticulture = forestryHorticulture;
	}
	@Column(name = "awarded_janbhagidari")
	public Integer getAwardedJanbhagidari() {
		return awardedJanbhagidari;
	}

	public void setAwardedJanbhagidari(Integer awardedJanbhagidari) {
		this.awardedJanbhagidari = awardedJanbhagidari;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="created_date", length=13)
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="updated_date", length=13)
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Column(name = "requested_ip", length=20)
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	@Column(name = "created_by", length=25)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "status")
	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}
	@OneToMany(fetch=FetchType.LAZY, mappedBy="watershedMahotsavInauguaration")
	public Set<WatershedMahotsavInauguarationActPhoto> getWatershedMahotsavInauguarationActPhoto() {
		return watershedMahotsavInauguarationActPhoto;
	}

	public void setWatershedMahotsavInauguarationActPhoto(
			Set<WatershedMahotsavInauguarationActPhoto> watershedMahotsavInauguarationActPhoto) {
		this.watershedMahotsavInauguarationActPhoto = watershedMahotsavInauguarationActPhoto;
	}
    
    
    
    
    
    
    
    
    

}
