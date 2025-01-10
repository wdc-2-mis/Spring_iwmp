package app.watershedyatra.model;

import java.sql.Timestamp;
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
	
	
    private Integer watershedYatraId;
	private IwmpState iwmpState;
    private IwmpDistrict iwmpDistrict;
    private IwmpBlock iwmpBlock;
    private IwmpGramPanchayat iwmpGramPanchayat;
    private IwmpVillage iwmpVillage;
    private Timestamp yatraDate1;
    private Timestamp yatraDate2;
    private String yatraLocation;
    private Integer maleParticipants;
    private Integer femaleParticipants;
    private Integer centralMinister;
    private Integer stateMinister;
    private Integer parliamentMembers;
    private Integer legislativeAssemblyMembers;
    private Integer legislativeCouncilMembers;
    private Integer otherPublicRepresentatives;
    private Integer govOfficials;
    private Integer noOfArExperiencePeople;
    private String arExperiencePath1;
    private String arExperiencePath2;
    private Boolean bhumiJalSanrakshan;
    private String bhumiJalSanrakshanPath1;
    private String bhumiJalSanrakshanPath2;
    private Boolean watershedYatraFilm;
    private String yatraFilmPath1;
    private String yatraFilmPath2;
    private Integer quizParticipants;
    private String quizParticipantsPath1;
    private String quizParticipantsPath2;
    private String culturalActivity;
    private String culturalActivityPath1;
    private String culturalActivityPath2;
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
    private String shramdaanPath1;
    private String shramdaanPath2;
    private Integer plantationArea;
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

   public WatershedYatVill() {}
   
   public WatershedYatVill(Integer watershedYatraId,IwmpState iwmpState,IwmpDistrict iwmpDistrict,IwmpBlock iwmpBlock,IwmpGramPanchayat iwmpGramPanchayat,IwmpVillage iwmpVillage,
		   Timestamp yatraDate1,Timestamp yatraDate2,String yatraLocation,Integer maleParticipants,Integer femaleParticipants,Integer centralMinister,Integer stateMinister,Integer parliamentMembers,
		   Integer legislativeAssemblyMembers,Integer legislativeCouncilMembers,Integer otherPublicRepresentatives,Integer govOfficials,Integer noOfArExperiencePeople,String arExperiencePath1,String arExperiencePath2,
		   Boolean bhumiJalSanrakshan,String bhumiJalSanrakshanPath1,String bhumiJalSanrakshanPath2,Boolean watershedYatraFilm,String yatraFilmPath1,String yatraFilmPath2,Integer quizParticipants,String quizParticipantsPath1,
		   String quizParticipantsPath2,String culturalActivity,String culturalActivityPath1,String culturalActivityPath2,Integer bhoomiPoojanNoOfWorks,Integer bhoomiPoojanCostOfWorks,String bhoomiPoojanPath1,
		   String bhoomiPoojanPath2,Integer lokarpanNoOfWorks,Integer lokarpanCostOfWorks,String lokarpanPath1,String lokarpanPath2,Integer shramdaanNoOfLocation,Integer shramdaanNoOfParticipatedPeople,String shramdaanPath1,
		   String shramdaanPath2,Integer plantationArea,Integer noOfAgroForsetry,String plantationPath1,String plantationPath2,Integer awardDistribution,String awardDistributionPath1,String awardDistributionPath2,String status,
		   String requestedIp,String updatedBy,Date updatedDate,String createdBy,Date createdDate)
   {
	  this.watershedYatraId=watershedYatraId;
	  this.iwmpState=iwmpState;
	  this.iwmpDistrict=iwmpDistrict;
	  this.iwmpBlock=iwmpBlock;
	  this.iwmpGramPanchayat=iwmpGramPanchayat;
	  this.iwmpVillage=iwmpVillage;
	  this.yatraDate1=yatraDate1;
	  this.yatraDate2=yatraDate2;
	  this.yatraLocation=yatraLocation;
	  this.maleParticipants=maleParticipants;
	  this.femaleParticipants=femaleParticipants;
	  this.centralMinister=centralMinister;
	  this.stateMinister=stateMinister;
	  this.parliamentMembers=parliamentMembers;
	  this.legislativeAssemblyMembers=legislativeAssemblyMembers;
	  this.legislativeCouncilMembers=legislativeCouncilMembers;
	  this.otherPublicRepresentatives=otherPublicRepresentatives;
	  this.govOfficials=govOfficials;
	  this.noOfArExperiencePeople=noOfArExperiencePeople;
	  this.arExperiencePath1=arExperiencePath1;
	  this.arExperiencePath2=arExperiencePath2;
	  this.bhumiJalSanrakshan=bhumiJalSanrakshan;
	  this.bhumiJalSanrakshanPath1=bhumiJalSanrakshanPath1;
	  this.bhumiJalSanrakshanPath2=bhumiJalSanrakshanPath2;
	  this.watershedYatraFilm=watershedYatraFilm;
	  this.yatraFilmPath1=yatraFilmPath1;
	  this.yatraFilmPath2=yatraFilmPath2;
	  this.quizParticipants=quizParticipants;
	  this.quizParticipantsPath1=quizParticipantsPath1;
	  this.quizParticipantsPath2=quizParticipantsPath2;
	  this.culturalActivity=culturalActivity;
	  this.culturalActivityPath1=culturalActivityPath1;
	  this.culturalActivityPath2=culturalActivityPath2;
	  this.bhoomiPoojanNoOfWorks=bhoomiPoojanNoOfWorks;
	  this.bhoomiPoojanCostOfWorks=bhoomiPoojanCostOfWorks;
	  this.bhoomiPoojanPath1=bhoomiPoojanPath1;
	  this.bhoomiPoojanPath2=bhoomiPoojanPath2;
	  this.lokarpanNoOfWorks=lokarpanNoOfWorks;
	  this.lokarpanCostOfWorks=lokarpanCostOfWorks;
	  this.lokarpanPath1=lokarpanPath1;
	  this.lokarpanPath2=lokarpanPath2;
	  this.shramdaanNoOfLocation=shramdaanNoOfLocation;
	  this.shramdaanNoOfParticipatedPeople=shramdaanNoOfParticipatedPeople;
	  this.shramdaanPath1=shramdaanPath1;
	  this.shramdaanPath2=shramdaanPath2;
	  this.plantationArea=plantationArea;
	  this.noOfAgroForsetry=noOfAgroForsetry;
	  this.plantationPath1=plantationPath1;
	  this.plantationPath2=plantationPath2;
	  this.awardDistribution=awardDistribution;
	  this.awardDistributionPath1=awardDistributionPath1;
	  this.awardDistributionPath2=awardDistributionPath2;
	  this.status=status;
	  this.requestedIp=requestedIp;
	  this.updatedBy=updatedBy;
	  this.updatedDate=updatedDate;
	  this.createdBy=createdBy;
	  this.createdDate=createdDate;
   }

   
   
   
   @Id 
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="watershed_yatra_id", unique=true, nullable=false)   
   public Integer getWatershedYatraId() {
	   return watershedYatraId;
   }

   public void setWatershedYatraId(Integer watershedYatraId) {
	   this.watershedYatraId = watershedYatraId;
   }

   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="st_code")
	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dcode")
	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bcode")
	public IwmpBlock getIwmpBlock() {
		return iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gcode")
	public IwmpGramPanchayat getIwmpGramPanchayat() {
		return iwmpGramPanchayat;
	}
	
	public void setIwmpGramPanchayat(IwmpGramPanchayat iwmpGramPanchayat) {
		this.iwmpGramPanchayat = iwmpGramPanchayat;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="vcode")
	public IwmpVillage getIwmpVillage() {
		return iwmpVillage;
	}

	public void setIwmpVillage(IwmpVillage iwmpVillage) {
		this.iwmpVillage = iwmpVillage;
	}

	@Column(name="yatra_date1")
	public Timestamp getYatraDate1() {
		return yatraDate1;
	}
	
	public void setYatraDate1(Timestamp yatraDate1) {
		this.yatraDate1 = yatraDate1;
	}
	
	@Column(name="yatra_date2")
	public Timestamp getYatraDate2() {
		return yatraDate2;
	}
	
	public void setYatraDate2(Timestamp yatraDate2) {
		this.yatraDate2 = yatraDate2;
	}
	
	@Column(name="yatra_location")
	public String getYatraLocation() {
		return yatraLocation;
	}
	
	public void setYatraLocation(String yatraLocation) {
		this.yatraLocation = yatraLocation;
	}
	
	@Column(name="male_participants")
	public Integer getMaleParticipants() {
		return maleParticipants;
	}
	
	public void setMaleParticipants(Integer maleParticipants) {
		this.maleParticipants = maleParticipants;
	}
	
	@Column(name="female_participants")
	public Integer getFemaleParticipants() {
		return femaleParticipants;
	}
	
	public void setFemaleParticipants(Integer femaleParticipants) {
		this.femaleParticipants = femaleParticipants;
	}
	
	@Column(name="central_minister")
	public Integer getCentralMinister() {
		return centralMinister;
	}
	
	public void setCentralMinister(Integer centralMinister) {
		this.centralMinister = centralMinister;
	}
	
	@Column(name="state_minister")
	public Integer getStateMinister() {
		return stateMinister;
	}
	
	public void setStateMinister(Integer stateMinister) {
		this.stateMinister = stateMinister;
	}
	
	@Column(name="parliament_members")
	public Integer getParliamentMembers() {
		return parliamentMembers;
	}
	
	public void setParliamentMembers(Integer parliamentMembers) {
		this.parliamentMembers = parliamentMembers;
	}

	@Column(name="legislative_assembly_members")
	public Integer getLegislativeAssemblyMembers() {
		return legislativeAssemblyMembers;
	}
	
	public void setLegislativeAssemblyMembers(Integer legislativeAssemblyMembers) {
		this.legislativeAssemblyMembers = legislativeAssemblyMembers;
	}
	
	@Column(name="legislative_council_members")
	public Integer getLegislativeCouncilMembers() {
		return legislativeCouncilMembers;
	}
	
	public void setLegislativeCouncilMembers(Integer legislativeCouncilMembers) {
		this.legislativeCouncilMembers = legislativeCouncilMembers;
	}
	
	@Column(name="other_public_representatives")
	public Integer getOtherPublicRepresentatives() {
		return otherPublicRepresentatives;
	}
	
	public void setOtherPublicRepresentatives(Integer otherPublicRepresentatives) {
		this.otherPublicRepresentatives = otherPublicRepresentatives;
	}
	
	@Column(name="gov_officials")
	public Integer getGovOfficials() {
		return govOfficials;
	}
	
	public void setGovOfficials(Integer govOfficials) {
		this.govOfficials = govOfficials;
	}
	
	@Column(name="no_of_ar_experience_people")
	public Integer getNoOfArExperiencePeople() {
		return noOfArExperiencePeople;
	}
	
	public void setNoOfArExperiencePeople(Integer noOfArExperiencePeople) {
		this.noOfArExperiencePeople = noOfArExperiencePeople;
	}
	
	@Column(name="ar_experience_path1")
	public String getArExperiencePath1() {
		return arExperiencePath1;
	}
	
	public void setArExperiencePath1(String arExperiencePath1) {
		this.arExperiencePath1 = arExperiencePath1;
	}
	
	@Column(name="ar_experience_path2")
	public String getArExperiencePath2() {
		return arExperiencePath2;
	}
	
	public void setArExperiencePath2(String arExperiencePath2) {
		this.arExperiencePath2 = arExperiencePath2;
	}
	
	@Column(name="bhumi_jal_sanrakshan")
	public Boolean getBhumiJalSanrakshan() {
		return bhumiJalSanrakshan;
	}
	
	public void setBhumiJalSanrakshan(Boolean bhumiJalSanrakshan) {
		this.bhumiJalSanrakshan = bhumiJalSanrakshan;
	}
	
	@Column(name="bhumi_jal_sanrakshan_path1")
	public String getBhumiJalSanrakshanPath1() {
		return bhumiJalSanrakshanPath1;
	}
	
	public void setBhumiJalSanrakshanPath1(String bhumiJalSanrakshanPath1) {
		this.bhumiJalSanrakshanPath1 = bhumiJalSanrakshanPath1;
	}
	
	@Column(name="bhumi_jal_sanrakshan_path2")
	public String getBhumiJalSanrakshanPath2() {
		return bhumiJalSanrakshanPath2;
	}
	
	public void setBhumiJalSanrakshanPath2(String bhumiJalSanrakshanPath2) {
		this.bhumiJalSanrakshanPath2 = bhumiJalSanrakshanPath2;
	}
	
	@Column(name="watershed_yatra_film")
	public Boolean getWatershedYatraFilm() {
		return watershedYatraFilm;
	}
	
	public void setWatershedYatraFilm(Boolean watershedYatraFilm) {
		this.watershedYatraFilm = watershedYatraFilm;
	}
	
	@Column(name="yatra_film_path1")
	public String getYatraFilmPath1() {
		return yatraFilmPath1;
	}
	
	public void setYatraFilmPath1(String yatraFilmPath1) {
		this.yatraFilmPath1 = yatraFilmPath1;
	}
	
	@Column(name="yatra_film_path2")
	public String getYatraFilmPath2() {
		return yatraFilmPath2;
	}
	
	public void setYatraFilmPath2(String yatraFilmPath2) {
		this.yatraFilmPath2 = yatraFilmPath2;
	}
	
	@Column(name="quiz_participants")
	public Integer getQuizParticipants() {
		return quizParticipants;
	}
	
	public void setQuizParticipants(Integer quizParticipants) {
		this.quizParticipants = quizParticipants;
	}
	
	@Column(name="quiz_participants_path1")
	public String getQuizParticipantsPath1() {
		return quizParticipantsPath1;
	}
	
	public void setQuizParticipantsPath1(String quizParticipantsPath1) {
		this.quizParticipantsPath1 = quizParticipantsPath1;
	}
	
	@Column(name="quiz_participants_path2")
	public String getQuizParticipantsPath2() {
		return quizParticipantsPath2;
	}
	
	public void setQuizParticipantsPath2(String quizParticipantsPath2) {
		this.quizParticipantsPath2 = quizParticipantsPath2;
	}
	
	@Column(name="cultural_activity")
	public String getCulturalActivity() {
		return culturalActivity;
	}
	
	public void setCulturalActivity(String culturalActivity) {
		this.culturalActivity = culturalActivity;
	}
	
	@Column(name="cultural_activity_path1")
	public String getCulturalActivityPath1() {
		return culturalActivityPath1;
	}
	
	public void setCulturalActivityPath1(String culturalActivityPath1) {
		this.culturalActivityPath1 = culturalActivityPath1;
	}
	
	@Column(name="cultural_activity_path2")
	public String getCulturalActivityPath2() {
		return culturalActivityPath2;
	}
	
	public void setCulturalActivityPath2(String culturalActivityPath2) {
		this.culturalActivityPath2 = culturalActivityPath2;
	}
	
	@Column(name="bhoomi_poojan_no_of_works")
	public Integer getBhoomiPoojanNoOfWorks() {
		return bhoomiPoojanNoOfWorks;
	}
	
	public void setBhoomiPoojanNoOfWorks(Integer bhoomiPoojanNoOfWorks) {
		this.bhoomiPoojanNoOfWorks = bhoomiPoojanNoOfWorks;
	}
	
	@Column(name="bhoomi_poojan_cost_of_works")
	public Integer getBhoomiPoojanCostOfWorks() {
		return bhoomiPoojanCostOfWorks;
	}
	
	public void setBhoomiPoojanCostOfWorks(Integer bhoomiPoojanCostOfWorks) {
		this.bhoomiPoojanCostOfWorks = bhoomiPoojanCostOfWorks;
	}
	
	@Column(name="bhoomi_poojan_path1")
	public String getBhoomiPoojanPath1() {
		return bhoomiPoojanPath1;
	}
	
	public void setBhoomiPoojanPath1(String bhoomiPoojanPath1) {
		this.bhoomiPoojanPath1 = bhoomiPoojanPath1;
	}

	@Column(name="bhoomi_poojan_path2")
	public String getBhoomiPoojanPath2() {
		return bhoomiPoojanPath2;
	}
	
	public void setBhoomiPoojanPath2(String bhoomiPoojanPath2) {
		this.bhoomiPoojanPath2 = bhoomiPoojanPath2;
	}

	@Column(name="lokarpan_no_of_works")
	public Integer getLokarpanNoOfWorks() {
		return lokarpanNoOfWorks;
	}
	
	public void setLokarpanNoOfWorks(Integer lokarpanNoOfWorks) {
		this.lokarpanNoOfWorks = lokarpanNoOfWorks;
	}
	
	@Column(name="lokarpan_cost_of_works")
	public Integer getLokarpanCostOfWorks() {
		return lokarpanCostOfWorks;
	}
	
	public void setLokarpanCostOfWorks(Integer lokarpanCostOfWorks) {
		this.lokarpanCostOfWorks = lokarpanCostOfWorks;
	}
	
	@Column(name="lokarpan_path1")
	public String getLokarpanPath1() {
		return lokarpanPath1;
	}
	
	public void setLokarpanPath1(String lokarpanPath1) {
		this.lokarpanPath1 = lokarpanPath1;
	}
	
	@Column(name="lokarpan_path2")
	public String getLokarpanPath2() {
		return lokarpanPath2;
	}
	
	public void setLokarpanPath2(String lokarpanPath2) {
		this.lokarpanPath2 = lokarpanPath2;
	}
	
	@Column(name="shramdaan_no_of_location")
	public Integer getShramdaanNoOfLocation() {
		return shramdaanNoOfLocation;
	}
	
	public void setShramdaanNoOfLocation(Integer shramdaanNoOfLocation) {
		this.shramdaanNoOfLocation = shramdaanNoOfLocation;
	}
	
	@Column(name="shramdaan_no_of_participated_people")
	public Integer getShramdaanNoOfParticipatedPeople() {
		return shramdaanNoOfParticipatedPeople;
	}
	
	public void setShramdaanNoOfParticipatedPeople(Integer shramdaanNoOfParticipatedPeople) {
		this.shramdaanNoOfParticipatedPeople = shramdaanNoOfParticipatedPeople;
	}
	
	@Column(name="shramdaan_path1")
	public String getShramdaanPath1() {
		return shramdaanPath1;
	}
	
	public void setShramdaanPath1(String shramdaanPath1) {
		this.shramdaanPath1 = shramdaanPath1;
	}
	
	@Column(name="shramdaan_path2")
	public String getShramdaanPath2() {
		return shramdaanPath2;
	}
	
	public void setShramdaanPath2(String shramdaanPath2) {
		this.shramdaanPath2 = shramdaanPath2;
	}
	
	@Column(name="plantation_area")
	public Integer getPlantationArea() {
		return plantationArea;
	}
	
	public void setPlantationArea(Integer plantationArea) {
		this.plantationArea = plantationArea;
	}
	
	@Column(name="no_of_agro_forsetry")
	public Integer getNoOfAgroForsetry() {
		return noOfAgroForsetry;
	}
	
	public void setNoOfAgroForsetry(Integer noOfAgroForsetry) {
		this.noOfAgroForsetry = noOfAgroForsetry;
	}
	
	@Column(name="plantation_path1")
	public String getPlantationPath1() {
		return plantationPath1;
	}
	
	public void setPlantationPath1(String plantationPath1) {
		this.plantationPath1 = plantationPath1;
	}
	
	@Column(name="plantation_path2")
	public String getPlantationPath2() {
		return plantationPath2;
	}
	
	public void setPlantationPath2(String plantationPath2) {
		this.plantationPath2 = plantationPath2;
	}
	
	@Column(name="award_distribution")
	public Integer getAwardDistribution() {
		return awardDistribution;
	}
	
	public void setAwardDistribution(Integer awardDistribution) {
		this.awardDistribution = awardDistribution;
	}
	
	@Column(name="award_distribution_path1")
	public String getAwardDistributionPath1() {
		return awardDistributionPath1;
	}
	
	public void setAwardDistributionPath1(String awardDistributionPath1) {
		this.awardDistributionPath1 = awardDistributionPath1;
	}
	
	@Column(name="award_distribution_path2")
	public String getAwardDistributionPath2() {
		return awardDistributionPath2;
	}
	
	public void setAwardDistributionPath2(String awardDistributionPath2) {
		this.awardDistributionPath2 = awardDistributionPath2;
	}
	
	@Column(name="status", length=1)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="requested_ip")
	public String getRequestedIp() {
		return requestedIp;
	}
	
	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}
	
	@Column(name="updated_by")
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name="updated_date")
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@Column(name="created_by")
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="created_date")
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

   
   
   
   
   
   
   
   
   
   
}
