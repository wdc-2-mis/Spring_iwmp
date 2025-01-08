package app.watershedyatra.model;

import java.util.Date;
import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;

@Entity
@Table(name = "watershed_yatra_village_level")
public class WatershedYatVill {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long watershedYatraId;

    @Column(name = "st_code")
    private Integer stCode;

    @Column(name = "dcode")
    private Integer dCode;

    @Column(name = "bcode")
    private Integer bCode;

    @Column(name = "gcode")
    private Integer gCode;

    @Column(name = "vcode")
    private Integer vCode;

    @Column(name = "yatra_date1")
    private Date yatraDate1;

    @Column(name = "yatra_date2")
    private Date yatraDate2;

    @Column(name = "yatra_location")
    private String yatraLocation;

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

    @Column(name = "no_of_ar_experience_people")
    private Integer noOfArExperiencePeople;

    @Column(name = "ar_experience_path1")
    private String arExperiencePath1;

    @Column(name = "ar_experience_path2")
    private String arExperiencePath2;

    @Column(name = "bhumi_jal_sanrakshan")
    private Boolean bhumiJalSanrakshan;

    @Column(name = "bhumi_jal_sanrakshan_path1")
    private String bhumiJalSanrakshanPath1;

    @Column(name = "bhumi_jal_sanrakshan_path2")
    private String bhumiJalSanrakshanPath2;

    @Column(name = "watershed_yatra_film")
    private Boolean watershedYatraFilm;

    @Column(name = "yatra_film_path1")
    private String yatraFilmPath1;

    @Column(name = "yatra_film_path2")
    private String yatraFilmPath2;

    @Column(name = "quiz_participants")
    private Integer quizParticipants;

    @Column(name = "quiz_participants_path1")
    private String quizParticipantsPath1;

    @Column(name = "quiz_participants_path2")
    private String quizParticipantsPath2;

    @Column(name = "cultural_activity")
    private String culturalActivity;

    @Column(name = "cultural_activity_path1")
    private String culturalActivityPath1;

    @Column(name = "cultural_activity_path2")
    private String culturalActivityPath2;

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
    private Date updatedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "st_code", referencedColumnName = "st_code", insertable = false, updatable = false)
    private IwmpState iwmpState;

    @ManyToOne
    @JoinColumn(name = "dcode", referencedColumnName = "dcode", insertable = false, updatable = false)
    private IwmpDistrict iwmpDistrict;

    @ManyToOne
    @JoinColumn(name = "bcode", referencedColumnName = "bcode", insertable = false, updatable = false)
    private IwmpBlock iwmpBlock;

    @ManyToOne
    @JoinColumn(name = "gcode", referencedColumnName = "gcode", insertable = false, updatable = false)
    private IwmpGramPanchayat iwmpGramPanchayat;

    @ManyToOne
    @JoinColumn(name = "vcode", referencedColumnName = "vcode", insertable = false, updatable = false)
    private IwmpVillage iwmpVillage;
    
    
	public Long getWatershedYatraId() {
		return watershedYatraId;
	}

	public void setWatershedYatraId(Long watershedYatraId) {
		this.watershedYatraId = watershedYatraId;
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

	public Integer getgCode() {
		return gCode;
	}

	public void setgCode(Integer gCode) {
		this.gCode = gCode;
	}

	public Integer getvCode() {
		return vCode;
	}

	public void setvCode(Integer vCode) {
		this.vCode = vCode;
	}

	public Date getYatraDate1() {
		return yatraDate1;
	}

	public void setYatraDate1(Date yatraDate1) {
		this.yatraDate1 = yatraDate1;
	}

	public Date getYatraDate2() {
		return yatraDate2;
	}

	public void setYatraDate2(Date yatraDate2) {
		this.yatraDate2 = yatraDate2;
	}

	public String getYatraLocation() {
		return yatraLocation;
	}

	public void setYatraLocation(String yatraLocation) {
		this.yatraLocation = yatraLocation;
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

	public Integer getNoOfArExperiencePeople() {
		return noOfArExperiencePeople;
	}

	public void setNoOfArExperiencePeople(Integer noOfArExperiencePeople) {
		this.noOfArExperiencePeople = noOfArExperiencePeople;
	}

	public String getArExperiencePath1() {
		return arExperiencePath1;
	}

	public void setArExperiencePath1(String arExperiencePath1) {
		this.arExperiencePath1 = arExperiencePath1;
	}

	public String getArExperiencePath2() {
		return arExperiencePath2;
	}

	public void setArExperiencePath2(String arExperiencePath2) {
		this.arExperiencePath2 = arExperiencePath2;
	}

	public Boolean getBhumiJalSanrakshan() {
		return bhumiJalSanrakshan;
	}

	public void setBhumiJalSanrakshan(Boolean bhumiJalSanrakshan) {
		this.bhumiJalSanrakshan = bhumiJalSanrakshan;
	}

	public String getBhumiJalSanrakshanPath1() {
		return bhumiJalSanrakshanPath1;
	}

	public void setBhumiJalSanrakshanPath1(String bhumiJalSanrakshanPath1) {
		this.bhumiJalSanrakshanPath1 = bhumiJalSanrakshanPath1;
	}

	public String getBhumiJalSanrakshanPath2() {
		return bhumiJalSanrakshanPath2;
	}

	public void setBhumiJalSanrakshanPath2(String bhumiJalSanrakshanPath2) {
		this.bhumiJalSanrakshanPath2 = bhumiJalSanrakshanPath2;
	}

	public Boolean getWatershedYatraFilm() {
		return watershedYatraFilm;
	}

	public void setWatershedYatraFilm(Boolean watershedYatraFilm) {
		this.watershedYatraFilm = watershedYatraFilm;
	}

	public String getYatraFilmPath1() {
		return yatraFilmPath1;
	}

	public void setYatraFilmPath1(String yatraFilmPath1) {
		this.yatraFilmPath1 = yatraFilmPath1;
	}

	public String getYatraFilmPath2() {
		return yatraFilmPath2;
	}

	public void setYatraFilmPath2(String yatraFilmPath2) {
		this.yatraFilmPath2 = yatraFilmPath2;
	}

	public Integer getQuizParticipants() {
		return quizParticipants;
	}

	public void setQuizParticipants(Integer quizParticipants) {
		this.quizParticipants = quizParticipants;
	}

	public String getQuizParticipantsPath1() {
		return quizParticipantsPath1;
	}

	public void setQuizParticipantsPath1(String quizParticipantsPath1) {
		this.quizParticipantsPath1 = quizParticipantsPath1;
	}

	public String getQuizParticipantsPath2() {
		return quizParticipantsPath2;
	}

	public void setQuizParticipantsPath2(String quizParticipantsPath2) {
		this.quizParticipantsPath2 = quizParticipantsPath2;
	}

	public String getCulturalActivity() {
		return culturalActivity;
	}

	public void setCulturalActivity(String culturalActivity) {
		this.culturalActivity = culturalActivity;
	}

	public String getCulturalActivityPath1() {
		return culturalActivityPath1;
	}

	public void setCulturalActivityPath1(String culturalActivityPath1) {
		this.culturalActivityPath1 = culturalActivityPath1;
	}

	public String getCulturalActivityPath2() {
		return culturalActivityPath2;
	}

	public void setCulturalActivityPath2(String culturalActivityPath2) {
		this.culturalActivityPath2 = culturalActivityPath2;
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

	public IwmpGramPanchayat getIwmpGramPanchayat() {
		return iwmpGramPanchayat;
	}

	public void setIwmpGramPanchayat(IwmpGramPanchayat iwmpGramPanchayat) {
		this.iwmpGramPanchayat = iwmpGramPanchayat;
	}

	public IwmpVillage getIwmpVillage() {
		return iwmpVillage;
	}

	public void setIwmpVillage(IwmpVillage iwmpVillage) {
		this.iwmpVillage = iwmpVillage;
	}
    
    
}
