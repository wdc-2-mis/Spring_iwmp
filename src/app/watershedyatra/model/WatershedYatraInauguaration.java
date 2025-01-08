package app.watershedyatra.model;

import java.time.*;
import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;

@Entity
@Table(name = "watershed_yatra_inauguaration", schema = "public")
public class WatershedYatraInauguaration {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inauguarationId;
	
	@Column(name = "st_code")
    private Integer stCode;

    @Column(name = "dcode")
    private Integer dCode;

    @Column(name = "bcode")
    private Integer bCode;

    @Column(name = "inauguaration_date")
    private LocalDateTime inauguarationDate;

    @Column(name = "inauguaration_location")
    private String inauguarationLocation;

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

    @Column(name = "van_flag_off")
    private Boolean vanFlagOff;

    @Column(name = "van_flag_path1")
    private String vanFlagPath1;

    @Column(name = "van_flag_path2")
    private String vanFlagPath2;

    @Column(name = "theme_song")
    private Boolean themeSong;

    @Column(name = "theme_song_path1")
    private String themeSongPath1;

    @Column(name = "theme_song_path2")
    private String themeSongPath2;

    @Column(name = "bhoomi_poojan_no_of_works")
    private Integer bhoomiPoojanNoOfWorks;

    @Column(name = "bhoomi_poojan_cost_of_works")
    private Integer bhoomiPoojanCostOfWorks;

    @Column(name = "bhoomi_poojan_path1")
    private String bhoomiPoojanPath1;

    @Column(name = "bhoomi_poojan_path2")
    private String bhoomiPoojanPath2;

    @Column(name = "lokarpan_no_of_works")
    private Integer lokarpanNoOfWorks;

    @Column(name = "lokarpan_cost_of_works")
    private Integer lokarpanCostOfWorks;

    @Column(name = "lokarpan_path1")
    private String lokarpanPath1;

    @Column(name = "lokarpan_path2")
    private String lokarpanPath2;

    @Column(name = "shramdaan_no_of_location")
    private Integer shramdaanNoOfLocation;

    @Column(name = "shramdaan_no_of_participated_people")
    private Integer shramdaanNoOfParticipatedPeople;

    @Column(name = "shramdaan_path1")
    private String shramdaanPath1;

    @Column(name = "shramdaan_path2")
    private String shramdaanPath2;

    @Column(name = "plantation_area")
    private Integer plantationArea;

    @Column(name = "no_of_agro_forsetry")
    private Integer noOfAgroForsetry;

    @Column(name = "plantation_path1")
    private String plantationPath1;

    @Column(name = "plantation_path2")
    private String plantationPath2;

    @Column(name = "award_distribution")
    private Integer awardDistribution;

    @Column(name = "award_distribution_path1")
    private String awardDistributionPath1;

    @Column(name = "award_distribution_path2")
    private String awardDistributionPath2;

    @Column(name = "status")
    private String status;

    @Column(name = "requested_ip")
    private String requestedIp;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "st_code", referencedColumnName = "st_code", insertable = false, updatable = false)
    private IwmpState iwmpState;

    @ManyToOne
    @JoinColumn(name = "dcode", referencedColumnName = "dcode", insertable = false, updatable = false)
    private IwmpDistrict iwmpDistrict;

    @ManyToOne
    @JoinColumn(name = "bcode", referencedColumnName = "bcode", insertable = false, updatable = false)
    private IwmpBlock iwmpBlock;
    
    
	public Integer getInauguarationId() {
		return inauguarationId;
	}

	public void setInauguarationId(Integer inauguarationId) {
		this.inauguarationId = inauguarationId;
	}

	public Integer getStCode() {
		return stCode;
	}

	public void setStCode(Integer stCode) {
		this.stCode = stCode;
	}

	public Integer getdCode() {
		return dCode;
	}

	public void setdCode(Integer dCode) {
		this.dCode = dCode;
	}

	public Integer getbCode() {
		return bCode;
	}

	public void setbCode(Integer bCode) {
		this.bCode = bCode;
	}

	public LocalDateTime getInauguarationDate() {
		return inauguarationDate;
	}

	public void setInauguarationDate(LocalDateTime inauguarationDate) {
		this.inauguarationDate = inauguarationDate;
	}

	public String getInauguarationLocation() {
		return inauguarationLocation;
	}

	public void setInauguarationLocation(String inauguarationLocation) {
		this.inauguarationLocation = inauguarationLocation;
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

	public Boolean getVanFlagOff() {
		return vanFlagOff;
	}

	public void setVanFlagOff(Boolean vanFlagOff) {
		this.vanFlagOff = vanFlagOff;
	}

	public String getVanFlagPath1() {
		return vanFlagPath1;
	}

	public void setVanFlagPath1(String vanFlagPath1) {
		this.vanFlagPath1 = vanFlagPath1;
	}

	public String getVanFlagPath2() {
		return vanFlagPath2;
	}

	public void setVanFlagPath2(String vanFlagPath2) {
		this.vanFlagPath2 = vanFlagPath2;
	}

	public Boolean getThemeSong() {
		return themeSong;
	}

	public void setThemeSong(Boolean themeSong) {
		this.themeSong = themeSong;
	}

	public String getThemeSongPath1() {
		return themeSongPath1;
	}

	public void setThemeSongPath1(String themeSongPath1) {
		this.themeSongPath1 = themeSongPath1;
	}

	public String getThemeSongPath2() {
		return themeSongPath2;
	}

	public void setThemeSongPath2(String themeSongPath2) {
		this.themeSongPath2 = themeSongPath2;
	}

	public Integer getBhoomiPoojanNoOfWorks() {
		return bhoomiPoojanNoOfWorks;
	}

	public void setBhoomiPoojanNoOfWorks(Integer bhoomiPoojanNoOfWorks) {
		this.bhoomiPoojanNoOfWorks = bhoomiPoojanNoOfWorks;
	}

	public Integer getBhoomiPoojanCostOfWorks() {
		return bhoomiPoojanCostOfWorks;
	}

	public void setBhoomiPoojanCostOfWorks(Integer bhoomiPoojanCostOfWorks) {
		this.bhoomiPoojanCostOfWorks = bhoomiPoojanCostOfWorks;
	}

	public String getBhoomiPoojanPath1() {
		return bhoomiPoojanPath1;
	}

	public void setBhoomiPoojanPath1(String bhoomiPoojanPath1) {
		this.bhoomiPoojanPath1 = bhoomiPoojanPath1;
	}

	public String getBhoomiPoojanPath2() {
		return bhoomiPoojanPath2;
	}

	public void setBhoomiPoojanPath2(String bhoomiPoojanPath2) {
		this.bhoomiPoojanPath2 = bhoomiPoojanPath2;
	}

	public Integer getLokarpanNoOfWorks() {
		return lokarpanNoOfWorks;
	}

	public void setLokarpanNoOfWorks(Integer lokarpanNoOfWorks) {
		this.lokarpanNoOfWorks = lokarpanNoOfWorks;
	}

	public Integer getLokarpanCostOfWorks() {
		return lokarpanCostOfWorks;
	}

	public void setLokarpanCostOfWorks(Integer lokarpanCostOfWorks) {
		this.lokarpanCostOfWorks = lokarpanCostOfWorks;
	}

	public String getLokarpanPath1() {
		return lokarpanPath1;
	}

	public void setLokarpanPath1(String lokarpanPath1) {
		this.lokarpanPath1 = lokarpanPath1;
	}

	public String getLokarpanPath2() {
		return lokarpanPath2;
	}

	public void setLokarpanPath2(String lokarpanPath2) {
		this.lokarpanPath2 = lokarpanPath2;
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

	public String getShramdaanPath1() {
		return shramdaanPath1;
	}

	public void setShramdaanPath1(String shramdaanPath1) {
		this.shramdaanPath1 = shramdaanPath1;
	}

	public String getShramdaanPath2() {
		return shramdaanPath2;
	}

	public void setShramdaanPath2(String shramdaanPath2) {
		this.shramdaanPath2 = shramdaanPath2;
	}

	public Integer getPlantationArea() {
		return plantationArea;
	}

	public void setPlantationArea(Integer plantationArea) {
		this.plantationArea = plantationArea;
	}

	public Integer getNoOfAgroForsetry() {
		return noOfAgroForsetry;
	}

	public void setNoOfAgroForsetry(Integer noOfAgroForsetry) {
		this.noOfAgroForsetry = noOfAgroForsetry;
	}

	public String getPlantationPath1() {
		return plantationPath1;
	}

	public void setPlantationPath1(String plantationPath1) {
		this.plantationPath1 = plantationPath1;
	}

	public String getPlantationPath2() {
		return plantationPath2;
	}

	public void setPlantationPath2(String plantationPath2) {
		this.plantationPath2 = plantationPath2;
	}

	public Integer getAwardDistribution() {
		return awardDistribution;
	}

	public void setAwardDistribution(Integer awardDistribution) {
		this.awardDistribution = awardDistribution;
	}

	public String getAwardDistributionPath1() {
		return awardDistributionPath1;
	}

	public void setAwardDistributionPath1(String awardDistributionPath1) {
		this.awardDistributionPath1 = awardDistributionPath1;
	}

	public String getAwardDistributionPath2() {
		return awardDistributionPath2;
	}

	public void setAwardDistributionPath2(String awardDistributionPath2) {
		this.awardDistributionPath2 = awardDistributionPath2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	public IwmpBlock getIwmpBlock() {
		return iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}
    

}
