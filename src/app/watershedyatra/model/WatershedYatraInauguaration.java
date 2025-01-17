package app.watershedyatra.model;

import java.math.BigDecimal;
import java.time.*;
import java.util.Date;

import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;

@Entity
@Table(name = "watershed_yatra_inauguaration", schema = "public")
public class WatershedYatraInauguaration {
	
	
    private Integer inauguarationId;
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
    private Boolean vanFlagOff;
    private String vanFlagPath1;
    private String vanFlagPath2;
    private Boolean themeSong;
    private String themeSongPath1;
    private String themeSongPath2;
    private Integer bhoomiPoojanNoOfWorks;
    private Integer bhoomiPoojanCostOfWorks;
    private String bhoomiPoojanPath1;
    private String bhoomiPoojanPath2;
    private Integer lokarpanNoOfWorks;
    private Integer lokarpanCostOfWorks;
    private String lokarpanPath1;
    private String lokarpanPath2;
    private Integer shramdaanNoOfLocation;
    private Integer shramdaanNoOfParticipatedPeople;
    private Integer manHr;
    private String shramdaanPath1;
    private String shramdaanPath2;
    private BigDecimal plantationArea;
    private Integer noOfAgroForsetry;
    private String plantationPath1;
    private String plantationPath2;
    private Integer awardDistribution;
    private String awardDistributionPath1;
    private String awardDistributionPath2;
    private String status;
    private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    private IwmpState iwmpState;
    private IwmpDistrict iwmpDistrict;
    private IwmpBlock iwmpBlock;
    
    public WatershedYatraInauguaration() { }
   
    public WatershedYatraInauguaration(Integer inauguarationId) {
    	this.inauguarationId = inauguarationId;
    }
    
	public WatershedYatraInauguaration(Integer inauguarationId,
			Date inauguarationDate, String inauguarationLocation, Integer maleParticipants,
			Integer femaleParticipants, Integer centralMinister, Integer stateMinister, Integer parliamentMembers,
			Integer legislativeAssemblyMembers, Integer legislativeCouncilMembers, Integer otherPublicRepresentatives,
			Integer govOfficials, Boolean vanFlagOff, String vanFlagPath1, String vanFlagPath2, Boolean themeSong,
			String themeSongPath1, String themeSongPath2, Integer bhoomiPoojanNoOfWorks,
			Integer bhoomiPoojanCostOfWorks, String bhoomiPoojanPath1, String bhoomiPoojanPath2,
			Integer lokarpanNoOfWorks, Integer lokarpanCostOfWorks, String lokarpanPath1, String lokarpanPath2,
			Integer shramdaanNoOfLocation, Integer shramdaanNoOfParticipatedPeople, Integer manHr, String shramdaanPath1,
			String shramdaanPath2, BigDecimal plantationArea, Integer noOfAgroForsetry, String plantationPath1,
			String plantationPath2, Integer awardDistribution, String awardDistributionPath1,
			String awardDistributionPath2, String status, String requestedIp, String updatedBy, Date updatedDate,
			String createdBy, Date createdDate, IwmpState iwmpState, IwmpDistrict iwmpDistrict,
			IwmpBlock iwmpBlock) {
		this.inauguarationId = inauguarationId;
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
		this.vanFlagOff = vanFlagOff;
		this.vanFlagPath1 = vanFlagPath1;
		this.vanFlagPath2 = vanFlagPath2;
		this.themeSong = themeSong;
		this.themeSongPath1 = themeSongPath1;
		this.themeSongPath2 = themeSongPath2;
		this.bhoomiPoojanNoOfWorks = bhoomiPoojanNoOfWorks;
		this.bhoomiPoojanCostOfWorks = bhoomiPoojanCostOfWorks;
		this.bhoomiPoojanPath1 = bhoomiPoojanPath1;
		this.bhoomiPoojanPath2 = bhoomiPoojanPath2;
		this.lokarpanNoOfWorks = lokarpanNoOfWorks;
		this.lokarpanCostOfWorks = lokarpanCostOfWorks;
		this.lokarpanPath1 = lokarpanPath1;
		this.lokarpanPath2 = lokarpanPath2;
		this.shramdaanNoOfLocation = shramdaanNoOfLocation;
		this.shramdaanNoOfParticipatedPeople = shramdaanNoOfParticipatedPeople;
		this.manHr = manHr;
		this.shramdaanPath1 = shramdaanPath1;
		this.shramdaanPath2 = shramdaanPath2;
		this.plantationArea = plantationArea;
		this.noOfAgroForsetry = noOfAgroForsetry;
		this.plantationPath1 = plantationPath1;
		this.plantationPath2 = plantationPath2;
		this.awardDistribution = awardDistribution;
		this.awardDistributionPath1 = awardDistributionPath1;
		this.awardDistributionPath2 = awardDistributionPath2;
		this.status = status;
		this.requestedIp = requestedIp;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.iwmpState = iwmpState;
		this.iwmpDistrict = iwmpDistrict;
		this.iwmpBlock = iwmpBlock;
	}
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="inauguaration_id", unique=true, nullable=false)
	public Integer getInauguarationId() {
		return inauguarationId;
	}

	public void setInauguarationId(Integer inauguarationId) {
		this.inauguarationId = inauguarationId;
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

	@Column(name = "van_flag_off")
	public Boolean getVanFlagOff() {
		return vanFlagOff;
	}

	public void setVanFlagOff(Boolean vanFlagOff) {
		this.vanFlagOff = vanFlagOff;
	}

	@Column(name = "van_flag_path1")
	public String getVanFlagPath1() {
		return vanFlagPath1;
	}

	public void setVanFlagPath1(String vanFlagPath1) {
		this.vanFlagPath1 = vanFlagPath1;
	}

	 @Column(name = "van_flag_path2")
	public String getVanFlagPath2() {
		return vanFlagPath2;
	}

	public void setVanFlagPath2(String vanFlagPath2) {
		this.vanFlagPath2 = vanFlagPath2;
	}

	@Column(name = "theme_song")
	public Boolean getThemeSong() {
		return themeSong;
	}

	public void setThemeSong(Boolean themeSong) {
		this.themeSong = themeSong;
	}

	@Column(name = "theme_song_path1")
	public String getThemeSongPath1() {
		return themeSongPath1;
	}

	public void setThemeSongPath1(String themeSongPath1) {
		this.themeSongPath1 = themeSongPath1;
	}

	@Column(name = "theme_song_path2")
	public String getThemeSongPath2() {
		return themeSongPath2;
	}

	public void setThemeSongPath2(String themeSongPath2) {
		this.themeSongPath2 = themeSongPath2;
	}

	@Column(name = "bhoomi_poojan_no_of_works")
	public Integer getBhoomiPoojanNoOfWorks() {
		return bhoomiPoojanNoOfWorks;
	}

	public void setBhoomiPoojanNoOfWorks(Integer bhoomiPoojanNoOfWorks) {
		this.bhoomiPoojanNoOfWorks = bhoomiPoojanNoOfWorks;
	}

	@Column(name = "bhoomi_poojan_cost_of_works")
	public Integer getBhoomiPoojanCostOfWorks() {
		return bhoomiPoojanCostOfWorks;
	}

	public void setBhoomiPoojanCostOfWorks(Integer bhoomiPoojanCostOfWorks) {
		this.bhoomiPoojanCostOfWorks = bhoomiPoojanCostOfWorks;
	}

	@Column(name = "bhoomi_poojan_path1")
	public String getBhoomiPoojanPath1() {
		return bhoomiPoojanPath1;
	}

	public void setBhoomiPoojanPath1(String bhoomiPoojanPath1) {
		this.bhoomiPoojanPath1 = bhoomiPoojanPath1;
	}

	@Column(name = "bhoomi_poojan_path2")
	public String getBhoomiPoojanPath2() {
		return bhoomiPoojanPath2;
	}

	public void setBhoomiPoojanPath2(String bhoomiPoojanPath2) {
		this.bhoomiPoojanPath2 = bhoomiPoojanPath2;
	}

	@Column(name = "lokarpan_no_of_works")
	public Integer getLokarpanNoOfWorks() {
		return lokarpanNoOfWorks;
	}

	public void setLokarpanNoOfWorks(Integer lokarpanNoOfWorks) {
		this.lokarpanNoOfWorks = lokarpanNoOfWorks;
	}

	@Column(name = "lokarpan_cost_of_works")
	public Integer getLokarpanCostOfWorks() {
		return lokarpanCostOfWorks;
	}

	public void setLokarpanCostOfWorks(Integer lokarpanCostOfWorks) {
		this.lokarpanCostOfWorks = lokarpanCostOfWorks;
	}

	@Column(name = "lokarpan_path1")
	public String getLokarpanPath1() {
		return lokarpanPath1;
	}

	public void setLokarpanPath1(String lokarpanPath1) {
		this.lokarpanPath1 = lokarpanPath1;
	}

	@Column(name = "lokarpan_path2")
	public String getLokarpanPath2() {
		return lokarpanPath2;
	}

	public void setLokarpanPath2(String lokarpanPath2) {
		this.lokarpanPath2 = lokarpanPath2;
	}

	@Column(name = "shramdaan_no_of_location")
	public Integer getShramdaanNoOfLocation() {
		return shramdaanNoOfLocation;
	}

	public void setShramdaanNoOfLocation(Integer shramdaanNoOfLocation) {
		this.shramdaanNoOfLocation = shramdaanNoOfLocation;
	}

	@Column(name = "shramdaan_no_of_participated_people")
	public Integer getShramdaanNoOfParticipatedPeople() {
		return shramdaanNoOfParticipatedPeople;
	}

	public void setShramdaanNoOfParticipatedPeople(Integer shramdaanNoOfParticipatedPeople) {
		this.shramdaanNoOfParticipatedPeople = shramdaanNoOfParticipatedPeople;
	}
	
	@Column(name = "man_hr")
	public Integer getManHr() {
		return manHr;
	}

	public void setManHr(Integer manHr) {
		this.manHr = manHr;
	}

	@Column(name = "shramdaan_path1")
	public String getShramdaanPath1() {
		return shramdaanPath1;
	}

	public void setShramdaanPath1(String shramdaanPath1) {
		this.shramdaanPath1 = shramdaanPath1;
	}

	@Column(name = "shramdaan_path2")
	public String getShramdaanPath2() {
		return shramdaanPath2;
	}

	public void setShramdaanPath2(String shramdaanPath2) {
		this.shramdaanPath2 = shramdaanPath2;
	}

	@Column(name = "plantation_area")
	public BigDecimal getPlantationArea() {
		return plantationArea;
	}

	public void setPlantationArea(BigDecimal plantationArea) {
		this.plantationArea = plantationArea;
	}

	@Column(name = "no_of_agro_forsetry")
	public Integer getNoOfAgroForsetry() {
		return noOfAgroForsetry;
	}

	public void setNoOfAgroForsetry(Integer noOfAgroForsetry) {
		this.noOfAgroForsetry = noOfAgroForsetry;
	}

	@Column(name = "plantation_path1")
	public String getPlantationPath1() {
		return plantationPath1;
	}

	public void setPlantationPath1(String plantationPath1) {
		this.plantationPath1 = plantationPath1;
	}

	@Column(name = "plantation_path2")
	public String getPlantationPath2() {
		return plantationPath2;
	}

	public void setPlantationPath2(String plantationPath2) {
		this.plantationPath2 = plantationPath2;
	}

	@Column(name = "award_distribution")
	public Integer getAwardDistribution() {
		return awardDistribution;
	}

	public void setAwardDistribution(Integer awardDistribution) {
		this.awardDistribution = awardDistribution;
	}

	@Column(name = "award_distribution_path1")
	public String getAwardDistributionPath1() {
		return awardDistributionPath1;
	}

	public void setAwardDistributionPath1(String awardDistributionPath1) {
		this.awardDistributionPath1 = awardDistributionPath1;
	}

	@Column(name = "award_distribution_path2")
	public String getAwardDistributionPath2() {
		return awardDistributionPath2;
	}

	public void setAwardDistributionPath2(String awardDistributionPath2) {
		this.awardDistributionPath2 = awardDistributionPath2;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "requested_ip", length=20)
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	@Column(name = "updated_by", length=25)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="updated_date", length=13)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "created_by", length=25)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="created_date", length=13)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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


}
