package app.watershedyatra.bean;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public class WatershedYatraBean {
    
	private Integer district;
	private Integer block;
	private Integer grampan;
	private Integer village;
	private int maleParticipants;
	private int femaleParticipants;
    private String datetime;
    private String location;
    private Integer centralMinisters;
    private Integer stateMinisters;
    private Integer membersOfParliament;
    private Integer legAssemblyMembers;
    private Integer legCouncilMembers;
    private Integer publicReps;
    private Integer govOfficials;
    private Integer arExperience;
    private MultipartFile arExperiencephoto1;
    private MultipartFile arExperiencephoto2;
    private Boolean shapathYes;
    private MultipartFile shapathYesphoto1;
    private MultipartFile shapathYesphoto2;
    private Boolean FilmYes;
    private MultipartFile FilmYesphoto1;
    private MultipartFile FilmYesphoto2;
    private Integer quizParticipants;
    
    private MultipartFile quizParticipantsphoto1;
    private MultipartFile quizParticipantsphoto2;
    
    private Integer culturalActivity;
    
    private String otherActivity;
    private MultipartFile culturalActivityphoto1;
    private MultipartFile culturalActivityphoto2;
    private Integer bhoomiWorks;
    private Integer bhoomiCost;
    private MultipartFile bhoomiCostphoto1;
    private MultipartFile bhoomiCostphoto2;
    private Integer lokWorks;
    private Integer costWorks;
    private MultipartFile lokWorksphoto1;
    private MultipartFile lokWorksphoto2;
    private Integer locShramdaan;
    private Integer locShramdaanps;
    private MultipartFile locShramdaanpsphoto1;
    private MultipartFile locShramdaanpsphoto2;
    private Integer plantationArea;
    private Integer nofagrohorti;
    private MultipartFile plantationAreaphoto1;
    private MultipartFile plantationAreaphoto2;
    private Integer noOfwatershed;
    private MultipartFile noOfwatershedphoto1;
    private MultipartFile noOfwatershedphoto2;
    
    
    
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public Integer getBlock() {
		return block;
	}
	public void setBlock(Integer block) {
		this.block = block;
	}
	public Integer getGrampan() {
		return grampan;
	}
	public void setGrampan(Integer grampan) {
		this.grampan = grampan;
	}
	public Integer getVillage() {
		return village;
	}
	public void setVillage(Integer village) {
		this.village = village;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getCentralMinisters() {
		return centralMinisters;
	}
	public void setCentralMinisters(Integer centralMinisters) {
		this.centralMinisters = centralMinisters;
	}
	public Integer getStateMinisters() {
		return stateMinisters;
	}
	public void setStateMinisters(Integer stateMinisters) {
		this.stateMinisters = stateMinisters;
	}
	public Integer getMembersOfParliament() {
		return membersOfParliament;
	}
	public void setMembersOfParliament(Integer membersOfParliament) {
		this.membersOfParliament = membersOfParliament;
	}
	public Integer getLegAssemblyMembers() {
		return legAssemblyMembers;
	}
	public void setLegAssemblyMembers(Integer legAssemblyMembers) {
		this.legAssemblyMembers = legAssemblyMembers;
	}
	public Integer getLegCouncilMembers() {
		return legCouncilMembers;
	}
	public void setLegCouncilMembers(Integer legCouncilMembers) {
		this.legCouncilMembers = legCouncilMembers;
	}
	public Integer getPublicReps() {
		return publicReps;
	}
	public void setPublicReps(Integer publicReps) {
		this.publicReps = publicReps;
	}
	public Integer getGovOfficials() {
		return govOfficials;
	}
	public void setGovOfficials(Integer govOfficials) {
		this.govOfficials = govOfficials;
	}
	public Integer getArExperience() {
		return arExperience;
	}
	public void setArExperience(Integer arExperience) {
		this.arExperience = arExperience;
	}
	public MultipartFile getArExperiencephoto1() {
		return arExperiencephoto1;
	}
	public void setArExperiencephoto1(MultipartFile arExperiencephoto1) {
		this.arExperiencephoto1 = arExperiencephoto1;
	}
	public MultipartFile getArExperiencephoto2() {
		return arExperiencephoto2;
	}
	public void setArExperiencephoto2(MultipartFile arExperiencephoto2) {
		this.arExperiencephoto2 = arExperiencephoto2;
	}
	public Boolean getShapathYes() {
		return shapathYes;
	}
	public void setShapathYes(Boolean shapathYes) {
		this.shapathYes = shapathYes;
	}
	public MultipartFile getShapathYesphoto1() {
		return shapathYesphoto1;
	}
	public void setShapathYesphoto1(MultipartFile shapathYesphoto1) {
		this.shapathYesphoto1 = shapathYesphoto1;
	}
	public MultipartFile getShapathYesphoto2() {
		return shapathYesphoto2;
	}
	public void setShapathYesphoto2(MultipartFile shapathYesphoto2) {
		this.shapathYesphoto2 = shapathYesphoto2;
	}
	public Boolean getFilmYes() {
		return FilmYes;
	}
	public void setFilmYes(Boolean filmYes) {
		FilmYes = filmYes;
	}
	public MultipartFile getFilmYesphoto1() {
		return FilmYesphoto1;
	}
	public void setFilmYesphoto1(MultipartFile filmYesphoto1) {
		FilmYesphoto1 = filmYesphoto1;
	}
	public MultipartFile getFilmYesphoto2() {
		return FilmYesphoto2;
	}
	public void setFilmYesphoto2(MultipartFile filmYesphoto2) {
		FilmYesphoto2 = filmYesphoto2;
	}
	public Integer getQuizParticipants() {
		return quizParticipants;
	}
	public void setQuizParticipants(Integer quizParticipants) {
		this.quizParticipants = quizParticipants;
	}
	public MultipartFile getQuizParticipantsphoto1() {
		return quizParticipantsphoto1;
	}
	public void setQuizParticipantsphoto1(MultipartFile quizParticipantsphoto1) {
		this.quizParticipantsphoto1 = quizParticipantsphoto1;
	}
	public MultipartFile getQuizParticipantsphoto2() {
		return quizParticipantsphoto2;
	}
	public void setQuizParticipantsphoto2(MultipartFile quizParticipantsphoto2) {
		this.quizParticipantsphoto2 = quizParticipantsphoto2;
	}
	public Integer getCulturalActivity() {
		return culturalActivity;
	}
	public void setCulturalActivity(Integer culturalActivity) {
		this.culturalActivity = culturalActivity;
	}
	
	
	public String getOtherActivity() {
		return otherActivity;
	}
	public void setOtherActivity(String otherActivity) {
		this.otherActivity = otherActivity;
	}
	public MultipartFile getCulturalActivityphoto1() {
		return culturalActivityphoto1;
	}
	public void setCulturalActivityphoto1(MultipartFile culturalActivityphoto1) {
		this.culturalActivityphoto1 = culturalActivityphoto1;
	}
	public MultipartFile getCulturalActivityphoto2() {
		return culturalActivityphoto2;
	}
	public void setCulturalActivityphoto2(MultipartFile culturalActivityphoto2) {
		this.culturalActivityphoto2 = culturalActivityphoto2;
	}
	public Integer getBhoomiWorks() {
		return bhoomiWorks;
	}
	public void setBhoomiWorks(Integer bhoomiWorks) {
		this.bhoomiWorks = bhoomiWorks;
	}
	public Integer getBhoomiCost() {
		return bhoomiCost;
	}
	public void setBhoomiCost(Integer bhoomiCost) {
		this.bhoomiCost = bhoomiCost;
	}
	public MultipartFile getBhoomiCostphoto1() {
		return bhoomiCostphoto1;
	}
	public void setBhoomiCostphoto1(MultipartFile bhoomiCostphoto1) {
		this.bhoomiCostphoto1 = bhoomiCostphoto1;
	}
	public MultipartFile getBhoomiCostphoto2() {
		return bhoomiCostphoto2;
	}
	public void setBhoomiCostphoto2(MultipartFile bhoomiCostphoto2) {
		this.bhoomiCostphoto2 = bhoomiCostphoto2;
	}
	public Integer getLokWorks() {
		return lokWorks;
	}
	public void setLokWorks(Integer lokWorks) {
		this.lokWorks = lokWorks;
	}
	public Integer getCostWorks() {
		return costWorks;
	}
	public void setCostWorks(Integer costWorks) {
		this.costWorks = costWorks;
	}
	public MultipartFile getLokWorksphoto1() {
		return lokWorksphoto1;
	}
	public void setLokWorksphoto1(MultipartFile lokWorksphoto1) {
		this.lokWorksphoto1 = lokWorksphoto1;
	}
	public MultipartFile getLokWorksphoto2() {
		return lokWorksphoto2;
	}
	public void setLokWorksphoto2(MultipartFile lokWorksphoto2) {
		this.lokWorksphoto2 = lokWorksphoto2;
	}
	public Integer getLocShramdaan() {
		return locShramdaan;
	}
	public void setLocShramdaan(Integer locShramdaan) {
		this.locShramdaan = locShramdaan;
	}
	public Integer getLocShramdaanps() {
		return locShramdaanps;
	}
	public void setLocShramdaanps(Integer locShramdaanps) {
		this.locShramdaanps = locShramdaanps;
	}
	public MultipartFile getLocShramdaanpsphoto1() {
		return locShramdaanpsphoto1;
	}
	public void setLocShramdaanpsphoto1(MultipartFile locShramdaanpsphoto1) {
		this.locShramdaanpsphoto1 = locShramdaanpsphoto1;
	}
	public MultipartFile getLocShramdaanpsphoto2() {
		return locShramdaanpsphoto2;
	}
	public void setLocShramdaanpsphoto2(MultipartFile locShramdaanpsphoto2) {
		this.locShramdaanpsphoto2 = locShramdaanpsphoto2;
	}
	public Integer getPlantationArea() {
		return plantationArea;
	}
	public void setPlantationArea(Integer plantationArea) {
		this.plantationArea = plantationArea;
	}
	public Integer getNofagrohorti() {
		return nofagrohorti;
	}
	public void setNofagrohorti(Integer nofagrohorti) {
		this.nofagrohorti = nofagrohorti;
	}
	public MultipartFile getPlantationAreaphoto1() {
		return plantationAreaphoto1;
	}
	public void setPlantationAreaphoto1(MultipartFile plantationAreaphoto1) {
		this.plantationAreaphoto1 = plantationAreaphoto1;
	}
	public MultipartFile getPlantationAreaphoto2() {
		return plantationAreaphoto2;
	}
	public void setPlantationAreaphoto2(MultipartFile plantationAreaphoto2) {
		this.plantationAreaphoto2 = plantationAreaphoto2;
	}
	public Integer getNoOfwatershed() {
		return noOfwatershed;
	}
	public void setNoOfwatershed(Integer noOfwatershed) {
		this.noOfwatershed = noOfwatershed;
	}
	public MultipartFile getNoOfwatershedphoto1() {
		return noOfwatershedphoto1;
	}
	public void setNoOfwatershedphoto1(MultipartFile noOfwatershedphoto1) {
		this.noOfwatershedphoto1 = noOfwatershedphoto1;
	}
	public MultipartFile getNoOfwatershedphoto2() {
		return noOfwatershedphoto2;
	}
	public void setNoOfwatershedphoto2(MultipartFile noOfwatershedphoto2) {
		this.noOfwatershedphoto2 = noOfwatershedphoto2;
	}
	
	public int getMaleParticipants() {
		return maleParticipants;
	}
	public void setMaleParticipants(int maleParticipants) {
		this.maleParticipants = maleParticipants;
	}
	public int getFemaleParticipants() {
		return femaleParticipants;
	}
	public void setFemaleParticipants(int femaleParticipants) {
		this.femaleParticipants = femaleParticipants;
	}
    
	
    
}
