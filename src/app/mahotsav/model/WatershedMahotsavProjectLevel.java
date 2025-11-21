package app.mahotsav.model;


import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;

@Entity
@Table(name = "watershed_mahotsav_project_level", schema = "public")
public class WatershedMahotsavProjectLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watershed_mahotsav_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "st_code", referencedColumnName = "st_code")
    private IwmpState state;

    @ManyToOne
    @JoinColumn(name = "dcode", referencedColumnName = "dcode")
    private IwmpDistrict district;

    @ManyToOne
    @JoinColumn(name = "bcode", referencedColumnName = "bcode")
    private IwmpBlock block;

    @Column(name = "mahotsav_date")
    private Date mahotsavDate;

    @Column(name = "mahotsav_location", length = 200)
    private String mahotsavLocation;

    @Column(name = "male_participants")
    private Integer maleParticipants;

    @Column(name = "female_participants")
    private Integer femaleParticipants;

    @Column(name = "central_minister")
    private Integer centralMinister;

    @Column(name = "state_minister")
    private Integer stateMinister;

    @Column(name = "parliament_members")
    private Integer parliamentMembers;

    @Column(name = "legislative_assembly_members")
    private Integer legislativeAssemblyMembers;

    @Column(name = "legislative_council_members")
    private Integer legislativeCouncilMembers;

    @Column(name = "other_public_representatives")
    private Integer otherPublicRepresentatives;

    @Column(name = "gov_officials")
    private Integer govOfficials;

    @Column(name = "bhoomi_poojan_no_of_works")
    private Integer bhoomiPoojanNoOfWorks;

    @Column(name = "lokarpan_no_of_works")
    private Integer lokarpanNoOfWorks;

    @Column(name = "shramdaan_no_of_location")
    private Integer shramdaanNoOfLocation;

    @Column(name = "shramdaan_no_of_participated_people")
    private Integer shramdaanNoOfParticipatedPeople;

    @Column(name = "no_of_agro_forsetry")
    private Integer noOfAgroForsetry;

    @Column(name = "status", length = 1)
    private Character status;

    @Column(name = "requested_ip", length = 25)
    private String requestedIp;

    @Column(name = "updated_by", length = 25)
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "created_by", length = 25)
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;
    
    

	public WatershedMahotsavProjectLevel(Integer id, IwmpState state, IwmpDistrict district, IwmpBlock block,
			Date mahotsavDate, String mahotsavLocation, Integer maleParticipants, Integer femaleParticipants,
			Integer centralMinister, Integer stateMinister, Integer parliamentMembers,
			Integer legislativeAssemblyMembers, Integer legislativeCouncilMembers, Integer otherPublicRepresentatives,
			Integer govOfficials, Integer bhoomiPoojanNoOfWorks, Integer lokarpanNoOfWorks,
			Integer shramdaanNoOfLocation, Integer shramdaanNoOfParticipatedPeople, Integer noOfAgroForsetry,
			Character status, String requestedIp, String updatedBy, Date updatedDate, String createdBy,
			Date createdDate) {
		super();
		this.id = id;
		this.state = state;
		this.district = district;
		this.block = block;
		this.mahotsavDate = mahotsavDate;
		this.mahotsavLocation = mahotsavLocation;
		this.maleParticipants = maleParticipants;
		this.femaleParticipants = femaleParticipants;
		this.centralMinister = centralMinister;
		this.stateMinister = stateMinister;
		this.parliamentMembers = parliamentMembers;
		this.legislativeAssemblyMembers = legislativeAssemblyMembers;
		this.legislativeCouncilMembers = legislativeCouncilMembers;
		this.otherPublicRepresentatives = otherPublicRepresentatives;
		this.govOfficials = govOfficials;
		this.bhoomiPoojanNoOfWorks = bhoomiPoojanNoOfWorks;
		this.lokarpanNoOfWorks = lokarpanNoOfWorks;
		this.shramdaanNoOfLocation = shramdaanNoOfLocation;
		this.shramdaanNoOfParticipatedPeople = shramdaanNoOfParticipatedPeople;
		this.noOfAgroForsetry = noOfAgroForsetry;
		this.status = status;
		this.requestedIp = requestedIp;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public WatershedMahotsavProjectLevel() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public IwmpState getState() {
		return state;
	}

	public void setState(IwmpState state) {
		this.state = state;
	}

	public IwmpDistrict getDistrict() {
		return district;
	}

	public void setDistrict(IwmpDistrict district) {
		this.district = district;
	}

	public IwmpBlock getBlock() {
		return block;
	}

	public void setBlock(IwmpBlock block) {
		this.block = block;
	}

	public Date getMahotsavDate() {
		return mahotsavDate;
	}

	public void setMahotsavDate(Date mahotsavDate) {
		this.mahotsavDate = mahotsavDate;
	}

	public String getMahotsavLocation() {
		return mahotsavLocation;
	}

	public void setMahotsavLocation(String mahotsavLocation) {
		this.mahotsavLocation = mahotsavLocation;
	}

	public Integer getMaleParticipants() {
		return maleParticipants;
	}

	public void setMaleParticipants(Integer maleParticipants) {
		this.maleParticipants = maleParticipants;
	}

	public Integer getFemaleParticipants() {
		return femaleParticipants;
	}

	public void setFemaleParticipants(Integer femaleParticipants) {
		this.femaleParticipants = femaleParticipants;
	}

	public Integer getCentralMinister() {
		return centralMinister;
	}

	public void setCentralMinister(Integer centralMinister) {
		this.centralMinister = centralMinister;
	}

	public Integer getStateMinister() {
		return stateMinister;
	}

	public void setStateMinister(Integer stateMinister) {
		this.stateMinister = stateMinister;
	}

	public Integer getParliamentMembers() {
		return parliamentMembers;
	}

	public void setParliamentMembers(Integer parliamentMembers) {
		this.parliamentMembers = parliamentMembers;
	}

	public Integer getLegislativeAssemblyMembers() {
		return legislativeAssemblyMembers;
	}

	public void setLegislativeAssemblyMembers(Integer legislativeAssemblyMembers) {
		this.legislativeAssemblyMembers = legislativeAssemblyMembers;
	}

	public Integer getLegislativeCouncilMembers() {
		return legislativeCouncilMembers;
	}

	public void setLegislativeCouncilMembers(Integer legislativeCouncilMembers) {
		this.legislativeCouncilMembers = legislativeCouncilMembers;
	}

	public Integer getOtherPublicRepresentatives() {
		return otherPublicRepresentatives;
	}

	public void setOtherPublicRepresentatives(Integer otherPublicRepresentatives) {
		this.otherPublicRepresentatives = otherPublicRepresentatives;
	}

	public Integer getGovOfficials() {
		return govOfficials;
	}

	public void setGovOfficials(Integer govOfficials) {
		this.govOfficials = govOfficials;
	}

	public Integer getBhoomiPoojanNoOfWorks() {
		return bhoomiPoojanNoOfWorks;
	}

	public void setBhoomiPoojanNoOfWorks(Integer bhoomiPoojanNoOfWorks) {
		this.bhoomiPoojanNoOfWorks = bhoomiPoojanNoOfWorks;
	}

	public Integer getLokarpanNoOfWorks() {
		return lokarpanNoOfWorks;
	}

	public void setLokarpanNoOfWorks(Integer lokarpanNoOfWorks) {
		this.lokarpanNoOfWorks = lokarpanNoOfWorks;
	}

	public Integer getShramdaanNoOfLocation() {
		return shramdaanNoOfLocation;
	}

	public void setShramdaanNoOfLocation(Integer shramdaanNoOfLocation) {
		this.shramdaanNoOfLocation = shramdaanNoOfLocation;
	}

	public Integer getShramdaanNoOfParticipatedPeople() {
		return shramdaanNoOfParticipatedPeople;
	}

	public void setShramdaanNoOfParticipatedPeople(Integer shramdaanNoOfParticipatedPeople) {
		this.shramdaanNoOfParticipatedPeople = shramdaanNoOfParticipatedPeople;
	}

	public Integer getNoOfAgroForsetry() {
		return noOfAgroForsetry;
	}

	public void setNoOfAgroForsetry(Integer noOfAgroForsetry) {
		this.noOfAgroForsetry = noOfAgroForsetry;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
    
}