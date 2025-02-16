package app.watershedyatra.model;

import java.math.BigDecimal;
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
    private MCulturalActivity mCulturalActivity;
    private String culturalActivityOther;
    private String culturalActivityPath1;
    private String culturalActivityPath2;
    private Integer bhoomiPoojanNoOfWorks;
    private BigDecimal bhoomiPoojanCostOfWorks;
    private String bhoomiPoojanPath1;
    private String bhoomiPoojanPath2;
    private Integer lokarpanNoOfWorks;
    private BigDecimal lokarpanCostOfWorks;
    private String lokarpanPath1;
    private String lokarpanPath2;
    private Integer shramdaanNoOfLocation;
    private Integer shramdaanNoOfParticipatedPeople;
    private Integer manhour;
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
    private String remarks;
    
    private String ar_experience_path1_longitute;
    private String ar_experience_path1_latitude;
    private Timestamp ar_experience_path1_time;
    private String ar_experience_path2_longitute;
    private String ar_experience_path2_latitude;
    private Timestamp ar_experience_path2_time;
    private String bhumi_jal_sanrakshan_path1_longitute;
    private String bhumi_jal_sanrakshan_path1_latitude;
    private Timestamp bhumi_jal_sanrakshan_path1_time;
    private String bhumi_jal_sanrakshan_path2_longitute;
    private String bhumi_jal_sanrakshan_path2_latitude;
    private Timestamp bhumi_jal_sanrakshan_path2_time;
    private String yatra_film_path1_longitute;
    private String yatra_film_path1_latitude;
    private Timestamp yatra_film_path1_time;
    private String yatra_film_path2_longitute;
    private String yatra_film_path2_latitude;
    private Timestamp yatra_film_path2_time;
    private String quiz_participants_path1_longitute;
    private String quiz_participants_path1_latitude;
    private Timestamp quiz_participants_path1_time;
    private String quiz_participants_path2_longitute;
    private String quiz_participants_path2_latitude;
    private Timestamp quiz_participants_path2_time;
    private String cultural_activity_path1_longitute;
    private String cultural_activity_path1_latitude;
    private Timestamp cultural_activity_path1_time;
    private String cultural_activity_path2_longitute;
    private String cultural_activity_path2_latitude;
    private Timestamp cultural_activity_path2_time;
    private String bhoomi_poojan_path1_longitute;
    private String bhoomi_poojan_path1_latitude;
    private Timestamp bhoomi_poojan_path1_time;
    private String bhoomi_poojan_path2_longitute;
    private String bhoomi_poojan_path2_latitude;
    private Timestamp bhoomi_poojan_path2_time;
    private String lokarpan_path1_longitute;
    private String lokarpan_path1_latitude;
    private Timestamp lokarpan_path1_time;
    private String lokarpan_path2_longitute;
    private String lokarpan_path2_latitude;
    private Timestamp lokarpan_path2_time;
    private String shramdaan_path1_longitute;
    private String shramdaan_path1_latitude;
    private Timestamp shramdaan_path1_time;
    private String shramdaan_path2_longitute;
    private String shramdaan_path2_latitude;
    private Timestamp shramdaan_path2_time;
    private String plantation_path1_longitute;
    private String plantation_path1_latitude;
    private Timestamp plantation_path1_time;
    private String plantation_path2_longitute;
    private String plantation_path2_latitude;
    private Timestamp plantation_path2_time;
    private String award_distribution_path1_longitute;
    private String award_distribution_path1_latitude;
    private Timestamp award_distribution_path1_time;
    private String award_distribution_path2_longitute;
    private String award_distribution_path2_latitude;
    private Timestamp award_distribution_path2_time; 

   public WatershedYatVill() {}
   
   public WatershedYatVill(Integer watershedYatraId,IwmpState iwmpState,IwmpDistrict iwmpDistrict,IwmpBlock iwmpBlock,IwmpGramPanchayat iwmpGramPanchayat,IwmpVillage iwmpVillage,
		   Timestamp yatraDate1,Timestamp yatraDate2,String yatraLocation,Integer maleParticipants,Integer femaleParticipants,Integer centralMinister,Integer stateMinister,Integer parliamentMembers,
		   Integer legislativeAssemblyMembers,Integer legislativeCouncilMembers,Integer otherPublicRepresentatives,Integer govOfficials,Integer noOfArExperiencePeople,String arExperiencePath1,String arExperiencePath2,
		   Boolean bhumiJalSanrakshan,String bhumiJalSanrakshanPath1,String bhumiJalSanrakshanPath2,Boolean watershedYatraFilm,String yatraFilmPath1,String yatraFilmPath2,Integer quizParticipants,String quizParticipantsPath1,
		   String quizParticipantsPath2,MCulturalActivity mCulturalActivity, String culturalActivityOther,String culturalActivityPath1,String culturalActivityPath2,Integer bhoomiPoojanNoOfWorks,BigDecimal bhoomiPoojanCostOfWorks,String bhoomiPoojanPath1,
		   String bhoomiPoojanPath2,Integer lokarpanNoOfWorks,BigDecimal lokarpanCostOfWorks,String lokarpanPath1,String lokarpanPath2,Integer shramdaanNoOfLocation,Integer shramdaanNoOfParticipatedPeople,Integer manhour,String shramdaanPath1,
		   String shramdaanPath2,BigDecimal plantationArea,Integer noOfAgroForsetry,String plantationPath1,String plantationPath2,Integer awardDistribution,String awardDistributionPath1,String awardDistributionPath2,String status,
		   String requestedIp,String updatedBy,Date updatedDate,String createdBy,Date createdDate, String remarks)
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
	  this.mCulturalActivity=mCulturalActivity;
	  this.culturalActivityOther=culturalActivityOther;
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
	  this.manhour=manhour;
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
	  this.remarks=remarks;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cultural_activity_id")
	public MCulturalActivity getmCulturalActivity() {
		return mCulturalActivity;
	}

	public void setmCulturalActivity(MCulturalActivity mCulturalActivity) {
		this.mCulturalActivity = mCulturalActivity;
	}

	@Column(name="cultural_activity_other")
	public String getCulturalActivityOther() {
		return culturalActivityOther;
	}
	
	public void setCulturalActivityOther(String culturalActivityOther) {
		this.culturalActivityOther = culturalActivityOther;
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
	public BigDecimal getBhoomiPoojanCostOfWorks() {
		return bhoomiPoojanCostOfWorks;
	}
	
	public void setBhoomiPoojanCostOfWorks(BigDecimal bhoomiPoojanCostOfWorks) {
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
	public BigDecimal getLokarpanCostOfWorks() {
		return lokarpanCostOfWorks;
	}
	
	public void setLokarpanCostOfWorks(BigDecimal lokarpanCostOfWorks) {
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
	
	@Column(name="man_hr")
	public Integer getManhour() {
		return manhour;
	}

	public void setManhour(Integer manhour) {
		this.manhour = manhour;
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
	public BigDecimal getPlantationArea() {
		return plantationArea;
	}
	
	public void setPlantationArea(BigDecimal plantationArea) {
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
	
	@Column(name="remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name ="ar_experience_path1_longitute")
	public String getAr_experience_path1_longitute() {
		return ar_experience_path1_longitute;
	}

	public void setAr_experience_path1_longitute(String ar_experience_path1_longitute) {
		this.ar_experience_path1_longitute = ar_experience_path1_longitute;
	}

	@Column(name ="ar_experience_path1_latitude")
	public String getAr_experience_path1_latitude() {
		return ar_experience_path1_latitude;
	}

	public void setAr_experience_path1_latitude(String ar_experience_path1_latitude) {
		this.ar_experience_path1_latitude = ar_experience_path1_latitude;
	}

	@Column(name ="ar_experience_path1_time")
	public Timestamp getAr_experience_path1_time() {
		return ar_experience_path1_time;
	}

	public void setAr_experience_path1_time(Timestamp ar_experience_path1_time) {
		this.ar_experience_path1_time = ar_experience_path1_time;
	}

	@Column(name ="ar_experience_path2_longitute")
	public String getAr_experience_path2_longitute() {
		return ar_experience_path2_longitute;
	}

	public void setAr_experience_path2_longitute(String ar_experience_path2_longitute) {
		this.ar_experience_path2_longitute = ar_experience_path2_longitute;
	}

	@Column(name ="ar_experience_path2_latitude")
	public String getAr_experience_path2_latitude() {
		return ar_experience_path2_latitude;
	}

	public void setAr_experience_path2_latitude(String ar_experience_path2_latitude) {
		this.ar_experience_path2_latitude = ar_experience_path2_latitude;
	}

	@Column(name ="ar_experience_path2_time")
	public Timestamp getAr_experience_path2_time() {
		return ar_experience_path2_time;
	}

	public void setAr_experience_path2_time(Timestamp ar_experience_path2_time) {
		this.ar_experience_path2_time = ar_experience_path2_time;
	}

	@Column(name ="bhumi_jal_sanrakshan_path1_longitute")
	public String getBhumi_jal_sanrakshan_path1_longitute() {
		return bhumi_jal_sanrakshan_path1_longitute;
	}

	public void setBhumi_jal_sanrakshan_path1_longitute(String bhumi_jal_sanrakshan_path1_longitute) {
		this.bhumi_jal_sanrakshan_path1_longitute = bhumi_jal_sanrakshan_path1_longitute;
	}

	@Column(name ="bhumi_jal_sanrakshan_path1_latitude")
	public String getBhumi_jal_sanrakshan_path1_latitude() {
		return bhumi_jal_sanrakshan_path1_latitude;
	}

	public void setBhumi_jal_sanrakshan_path1_latitude(String bhumi_jal_sanrakshan_path1_latitude) {
		this.bhumi_jal_sanrakshan_path1_latitude = bhumi_jal_sanrakshan_path1_latitude;
	}

	@Column(name ="bhumi_jal_sanrakshan_path1_time")
	public Timestamp getBhumi_jal_sanrakshan_path1_time() {
		return bhumi_jal_sanrakshan_path1_time;
	}

	public void setBhumi_jal_sanrakshan_path1_time(Timestamp bhumi_jal_sanrakshan_path1_time) {
		this.bhumi_jal_sanrakshan_path1_time = bhumi_jal_sanrakshan_path1_time;
	}

	@Column(name ="bhumi_jal_sanrakshan_path2_longitute")
	public String getBhumi_jal_sanrakshan_path2_longitute() {
		return bhumi_jal_sanrakshan_path2_longitute;
	}

	public void setBhumi_jal_sanrakshan_path2_longitute(String bhumi_jal_sanrakshan_path2_longitute) {
		this.bhumi_jal_sanrakshan_path2_longitute = bhumi_jal_sanrakshan_path2_longitute;
	}

	@Column(name ="bhumi_jal_sanrakshan_path2_latitude")
	public String getBhumi_jal_sanrakshan_path2_latitude() {
		return bhumi_jal_sanrakshan_path2_latitude;
	}

	public void setBhumi_jal_sanrakshan_path2_latitude(String bhumi_jal_sanrakshan_path2_latitude) {
		this.bhumi_jal_sanrakshan_path2_latitude = bhumi_jal_sanrakshan_path2_latitude;
	}

	@Column(name ="bhumi_jal_sanrakshan_path2_time")
	public Timestamp getBhumi_jal_sanrakshan_path2_time() {
		return bhumi_jal_sanrakshan_path2_time;
	}

	public void setBhumi_jal_sanrakshan_path2_time(Timestamp bhumi_jal_sanrakshan_path2_time) {
		this.bhumi_jal_sanrakshan_path2_time = bhumi_jal_sanrakshan_path2_time;
	}

	@Column(name ="yatra_film_path1_longitute")
	public String getYatra_film_path1_longitute() {
		return yatra_film_path1_longitute;
	}

	public void setYatra_film_path1_longitute(String yatra_film_path1_longitute) {
		this.yatra_film_path1_longitute = yatra_film_path1_longitute;
	}

	@Column(name ="yatra_film_path1_latitude")
	public String getYatra_film_path1_latitude() {
		return yatra_film_path1_latitude;
	}

	public void setYatra_film_path1_latitude(String yatra_film_path1_latitude) {
		this.yatra_film_path1_latitude = yatra_film_path1_latitude;
	}

	@Column(name ="yatra_film_path1_time")
	public Timestamp getYatra_film_path1_time() {
		return yatra_film_path1_time;
	}

	public void setYatra_film_path1_time(Timestamp yatra_film_path1_time) {
		this.yatra_film_path1_time = yatra_film_path1_time;
	}

	@Column(name ="yatra_film_path2_longitute")
	public String getYatra_film_path2_longitute() {
		return yatra_film_path2_longitute;
	}

	public void setYatra_film_path2_longitute(String yatra_film_path2_longitute) {
		this.yatra_film_path2_longitute = yatra_film_path2_longitute;
	}

	@Column(name ="yatra_film_path2_latitude")
	public String getYatra_film_path2_latitude() {
		return yatra_film_path2_latitude;
	}

	public void setYatra_film_path2_latitude(String yatra_film_path2_latitude) {
		this.yatra_film_path2_latitude = yatra_film_path2_latitude;
	}

	@Column(name ="yatra_film_path2_time")
	public Timestamp getYatra_film_path2_time() {
		return yatra_film_path2_time;
	}

	public void setYatra_film_path2_time(Timestamp yatra_film_path2_time) {
		this.yatra_film_path2_time = yatra_film_path2_time;
	}

	@Column(name ="quiz_participants_path1_longitute")
	public String getQuiz_participants_path1_longitute() {
		return quiz_participants_path1_longitute;
	}

	public void setQuiz_participants_path1_longitute(String quiz_participants_path1_longitute) {
		this.quiz_participants_path1_longitute = quiz_participants_path1_longitute;
	}

	@Column(name ="quiz_participants_path1_latitude")
	public String getQuiz_participants_path1_latitude() {
		return quiz_participants_path1_latitude;
	}

	public void setQuiz_participants_path1_latitude(String quiz_participants_path1_latitude) {
		this.quiz_participants_path1_latitude = quiz_participants_path1_latitude;
	}

	@Column(name ="quiz_participants_path1_time")
	public Timestamp getQuiz_participants_path1_time() {
		return quiz_participants_path1_time;
	}

	public void setQuiz_participants_path1_time(Timestamp quiz_participants_path1_time) {
		this.quiz_participants_path1_time = quiz_participants_path1_time;
	}

	@Column(name ="quiz_participants_path2_longitute")
	public String getQuiz_participants_path2_longitute() {
		return quiz_participants_path2_longitute;
	}

	public void setQuiz_participants_path2_longitute(String quiz_participants_path2_longitute) {
		this.quiz_participants_path2_longitute = quiz_participants_path2_longitute;
	}

	@Column(name ="quiz_participants_path2_latitude")
	public String getQuiz_participants_path2_latitude() {
		return quiz_participants_path2_latitude;
	}

	public void setQuiz_participants_path2_latitude(String quiz_participants_path2_latitude) {
		this.quiz_participants_path2_latitude = quiz_participants_path2_latitude;
	}

	@Column(name ="quiz_participants_path2_time")
	public Timestamp getQuiz_participants_path2_time() {
		return quiz_participants_path2_time;
	}

	public void setQuiz_participants_path2_time(Timestamp quiz_participants_path2_time) {
		this.quiz_participants_path2_time = quiz_participants_path2_time;
	}

	@Column(name ="cultural_activity_path1_longitute")
	public String getCultural_activity_path1_longitute() {
		return cultural_activity_path1_longitute;
	}

	public void setCultural_activity_path1_longitute(String cultural_activity_path1_longitute) {
		this.cultural_activity_path1_longitute = cultural_activity_path1_longitute;
	}

	@Column(name ="cultural_activity_path1_latitude")
	public String getCultural_activity_path1_latitude() {
		return cultural_activity_path1_latitude;
	}

	public void setCultural_activity_path1_latitude(String cultural_activity_path1_latitude) {
		this.cultural_activity_path1_latitude = cultural_activity_path1_latitude;
	}

	@Column(name ="cultural_activity_path1_time")
	public Timestamp getCultural_activity_path1_time() {
		return cultural_activity_path1_time;
	}

	public void setCultural_activity_path1_time(Timestamp cultural_activity_path1_time) {
		this.cultural_activity_path1_time = cultural_activity_path1_time;
	}

	@Column(name ="cultural_activity_path2_longitute")
	public String getCultural_activity_path2_longitute() {
		return cultural_activity_path2_longitute;
	}

	public void setCultural_activity_path2_longitute(String cultural_activity_path2_longitute) {
		this.cultural_activity_path2_longitute = cultural_activity_path2_longitute;
	}

	@Column(name ="cultural_activity_path2_latitude")
	public String getCultural_activity_path2_latitude() {
		return cultural_activity_path2_latitude;
	}

	public void setCultural_activity_path2_latitude(String cultural_activity_path2_latitude) {
		this.cultural_activity_path2_latitude = cultural_activity_path2_latitude;
	}

	@Column(name ="cultural_activity_path2_time")
	public Timestamp getCultural_activity_path2_time() {
		return cultural_activity_path2_time;
	}

	public void setCultural_activity_path2_time(Timestamp cultural_activity_path2_time) {
		this.cultural_activity_path2_time = cultural_activity_path2_time;
	}

	@Column(name ="bhoomi_poojan_path1_longitute")
	public String getBhoomi_poojan_path1_longitute() {
		return bhoomi_poojan_path1_longitute;
	}

	public void setBhoomi_poojan_path1_longitute(String bhoomi_poojan_path1_longitute) {
		this.bhoomi_poojan_path1_longitute = bhoomi_poojan_path1_longitute;
	}

	@Column(name ="bhoomi_poojan_path1_latitude")
	public String getBhoomi_poojan_path1_latitude() {
		return bhoomi_poojan_path1_latitude;
	}

	public void setBhoomi_poojan_path1_latitude(String bhoomi_poojan_path1_latitude) {
		this.bhoomi_poojan_path1_latitude = bhoomi_poojan_path1_latitude;
	}

	@Column(name ="bhoomi_poojan_path1_time")
	public Timestamp getBhoomi_poojan_path1_time() {
		return bhoomi_poojan_path1_time;
	}

	public void setBhoomi_poojan_path1_time(Timestamp bhoomi_poojan_path1_time) {
		this.bhoomi_poojan_path1_time = bhoomi_poojan_path1_time;
	}

	@Column(name ="bhoomi_poojan_path2_longitute")
	public String getBhoomi_poojan_path2_longitute() {
		return bhoomi_poojan_path2_longitute;
	}

	public void setBhoomi_poojan_path2_longitute(String bhoomi_poojan_path2_longitute) {
		this.bhoomi_poojan_path2_longitute = bhoomi_poojan_path2_longitute;
	}

	@Column(name ="bhoomi_poojan_path2_latitude")
	public String getBhoomi_poojan_path2_latitude() {
		return bhoomi_poojan_path2_latitude;
	}

	public void setBhoomi_poojan_path2_latitude(String bhoomi_poojan_path2_latitude) {
		this.bhoomi_poojan_path2_latitude = bhoomi_poojan_path2_latitude;
	}

	@Column(name ="bhoomi_poojan_path2_time")
	public Timestamp getBhoomi_poojan_path2_time() {
		return bhoomi_poojan_path2_time;
	}

	public void setBhoomi_poojan_path2_time(Timestamp bhoomi_poojan_path2_time) {
		this.bhoomi_poojan_path2_time = bhoomi_poojan_path2_time;
	}

	@Column(name ="lokarpan_path1_longitute")
	public String getLokarpan_path1_longitute() {
		return lokarpan_path1_longitute;
	}

	public void setLokarpan_path1_longitute(String lokarpan_path1_longitute) {
		this.lokarpan_path1_longitute = lokarpan_path1_longitute;
	}

	@Column(name ="lokarpan_path1_latitude")
	public String getLokarpan_path1_latitude() {
		return lokarpan_path1_latitude;
	}

	public void setLokarpan_path1_latitude(String lokarpan_path1_latitude) {
		this.lokarpan_path1_latitude = lokarpan_path1_latitude;
	}

	@Column(name ="lokarpan_path1_time")
	public Timestamp getLokarpan_path1_time() {
		return lokarpan_path1_time;
	}

	public void setLokarpan_path1_time(Timestamp lokarpan_path1_time) {
		this.lokarpan_path1_time = lokarpan_path1_time;
	}

	@Column(name ="lokarpan_path2_longitute")
	public String getLokarpan_path2_longitute() {
		return lokarpan_path2_longitute;
	}

	public void setLokarpan_path2_longitute(String lokarpan_path2_longitute) {
		this.lokarpan_path2_longitute = lokarpan_path2_longitute;
	}

	@Column(name ="lokarpan_path2_latitude")
	public String getLokarpan_path2_latitude() {
		return lokarpan_path2_latitude;
	}

	public void setLokarpan_path2_latitude(String lokarpan_path2_latitude) {
		this.lokarpan_path2_latitude = lokarpan_path2_latitude;
	}

	@Column(name ="lokarpan_path2_time")
	public Timestamp getLokarpan_path2_time() {
		return lokarpan_path2_time;
	}

	public void setLokarpan_path2_time(Timestamp lokarpan_path2_time) {
		this.lokarpan_path2_time = lokarpan_path2_time;
	}

	@Column(name ="shramdaan_path1_longitute")
	public String getShramdaan_path1_longitute() {
		return shramdaan_path1_longitute;
	}

	public void setShramdaan_path1_longitute(String shramdaan_path1_longitute) {
		this.shramdaan_path1_longitute = shramdaan_path1_longitute;
	}

	@Column(name ="shramdaan_path1_latitude")
	public String getShramdaan_path1_latitude() {
		return shramdaan_path1_latitude;
	}

	public void setShramdaan_path1_latitude(String shramdaan_path1_latitude) {
		this.shramdaan_path1_latitude = shramdaan_path1_latitude;
	}

	@Column(name ="shramdaan_path1_time")
	public Timestamp getShramdaan_path1_time() {
		return shramdaan_path1_time;
	}

	public void setShramdaan_path1_time(Timestamp shramdaan_path1_time) {
		this.shramdaan_path1_time = shramdaan_path1_time;
	}

	@Column(name ="shramdaan_path2_longitute")
	public String getShramdaan_path2_longitute() {
		return shramdaan_path2_longitute;
	}

	public void setShramdaan_path2_longitute(String shramdaan_path2_longitute) {
		this.shramdaan_path2_longitute = shramdaan_path2_longitute;
	}

	@Column(name ="shramdaan_path2_latitude")
	public String getShramdaan_path2_latitude() {
		return shramdaan_path2_latitude;
	}

	public void setShramdaan_path2_latitude(String shramdaan_path2_latitude) {
		this.shramdaan_path2_latitude = shramdaan_path2_latitude;
	}

	@Column(name ="shramdaan_path2_time")
	public Timestamp getShramdaan_path2_time() {
		return shramdaan_path2_time;
	}

	public void setShramdaan_path2_time(Timestamp shramdaan_path2_time) {
		this.shramdaan_path2_time = shramdaan_path2_time;
	}

	@Column(name ="plantation_path1_longitute")
	public String getPlantation_path1_longitute() {
		return plantation_path1_longitute;
	}

	public void setPlantation_path1_longitute(String plantation_path1_longitute) {
		this.plantation_path1_longitute = plantation_path1_longitute;
	}

	@Column(name ="plantation_path1_latitude")
	public String getPlantation_path1_latitude() {
		return plantation_path1_latitude;
	}

	public void setPlantation_path1_latitude(String plantation_path1_latitude) {
		this.plantation_path1_latitude = plantation_path1_latitude;
	}

	@Column(name ="plantation_path1_time")
	public Timestamp getPlantation_path1_time() {
		return plantation_path1_time;
	}

	public void setPlantation_path1_time(Timestamp plantation_path1_time) {
		this.plantation_path1_time = plantation_path1_time;
	}

	@Column(name ="plantation_path2_longitute")
	public String getPlantation_path2_longitute() {
		return plantation_path2_longitute;
	}

	public void setPlantation_path2_longitute(String plantation_path2_longitute) {
		this.plantation_path2_longitute = plantation_path2_longitute;
	}

	@Column(name ="plantation_path2_latitude")
	public String getPlantation_path2_latitude() {
		return plantation_path2_latitude;
	}

	public void setPlantation_path2_latitude(String plantation_path2_latitude) {
		this.plantation_path2_latitude = plantation_path2_latitude;
	}

	@Column(name ="plantation_path2_time")
	public Timestamp getPlantation_path2_time() {
		return plantation_path2_time;
	}

	public void setPlantation_path2_time(Timestamp plantation_path2_time) {
		this.plantation_path2_time = plantation_path2_time;
	}

	@Column(name ="award_distribution_path1_longitute")
	public String getAward_distribution_path1_longitute() {
		return award_distribution_path1_longitute;
	}

	public void setAward_distribution_path1_longitute(String award_distribution_path1_longitute) {
		this.award_distribution_path1_longitute = award_distribution_path1_longitute;
	}
	
	@Column(name ="award_distribution_path1_latitude")
	public String getAward_distribution_path1_latitude() {
		return award_distribution_path1_latitude;
	}

	public void setAward_distribution_path1_latitude(String award_distribution_path1_latitude) {
		this.award_distribution_path1_latitude = award_distribution_path1_latitude;
	}

	@Column(name ="award_distribution_path1_time")
	public Timestamp getAward_distribution_path1_time() {
		return award_distribution_path1_time;
	}

	public void setAward_distribution_path1_time(Timestamp award_distribution_path1_time) {
		this.award_distribution_path1_time = award_distribution_path1_time;
	}

	@Column(name ="award_distribution_path2_longitute")
	public String getAward_distribution_path2_longitute() {
		return award_distribution_path2_longitute;
	}

	public void setAward_distribution_path2_longitute(String award_distribution_path2_longitute) {
		this.award_distribution_path2_longitute = award_distribution_path2_longitute;
	}

	@Column(name ="award_distribution_path2_latitude")
	public String getAward_distribution_path2_latitude() {
		return award_distribution_path2_latitude;
	}

	public void setAward_distribution_path2_latitude(String award_distribution_path2_latitude) {
		this.award_distribution_path2_latitude = award_distribution_path2_latitude;
	}

	@Column(name ="award_distribution_path2_time")
	public Timestamp getAward_distribution_path2_time() {
		return award_distribution_path2_time;
	}

	public void setAward_distribution_path2_time(Timestamp award_distribution_path2_time) {
		this.award_distribution_path2_time = award_distribution_path2_time;
	}
	
	

   
   
   
   
   
   
   
   
   
   
}
