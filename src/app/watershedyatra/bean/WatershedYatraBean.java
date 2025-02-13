package app.watershedyatra.bean;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public class WatershedYatraBean {
	
    private Integer watershed_yatra_id;
    private String date;
    private String timed;
    private Integer st_code;
    private String stname;
    private String distname;
    private String blockname;
    private String gpname;
    private String villagename;
    private Integer male_participants;
    private Integer female_participants;
    private Integer central_ministers;
    private Integer state_ministers;
    private Integer parliament;
    private Integer assembly_members;
    private Integer council_members;
    private Integer others;
    private Integer gov_officials;
    private Integer no_of_ar_experience_people;
    private String bhumi_jal_sanrakshan;
    private String watershed_yatra_film;
    private Integer quiz_participants;
    private Integer cultural_activity_id;
    private String cultural_name;
    private Integer no_works_bhoomipoojan;
   // private Integer tot_works_bhoomipoojan;
    private BigDecimal tot_works_bhoomipoojan;
    private Integer no_works_lokarpan;
   // private Integer tot_works_lokarpan;
    private BigDecimal tot_works_lokarpan;
    private Integer no_location_shramdaan;
    private Integer no_people_shramdaan;
    private Integer manhour;
	private BigDecimal area_plantation;
    private Integer no_plantation;
    private Integer no_awards;
    private Integer image_count;
    
    private String ar_experience_path1;
    private String ar_experience_path2;
    private String bhumi_jal_sanrakshan_path1;
    private String bhumi_jal_sanrakshan_path2;
    private String yatra_film_path1;
    private String yatra_film_path2;
    private String quiz_participants_path1;
    private String quiz_participants_path2;
    private String cultural_activity_path1;
    private String cultural_activity_path2;
    private String bhoomi_poojan_path1;
    private String bhoomi_poojan_path2;
    private String lokarpan_path1;
    private String lokarpan_path2;
    private String shramdaan_path1;
    private String shramdaan_path2;
    private String plantation_path1;
    private String plantation_path2;
    private String award_distribution_path1;
    private String award_distribution_path2;
    
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
    private BigDecimal bhoomiCost;
    private MultipartFile bhoomiCostphoto1;
    private MultipartFile bhoomiCostphoto2;
    private Integer lokWorks;
    private BigDecimal costWorks;
    private MultipartFile lokWorksphoto1;
    private MultipartFile lokWorksphoto2;
    private Integer locShramdaan;
    private Integer locShramdaanps;
    private MultipartFile locShramdaanpsphoto1;
    private MultipartFile locShramdaanpsphoto2;
    private BigDecimal plantationArea;
    private Integer nofagrohorti;
    private MultipartFile plantationAreaphoto1;
    private MultipartFile plantationAreaphoto2;
    private Integer noOfwatershed;
    private MultipartFile noOfwatershedphoto1;
    private MultipartFile noOfwatershedphoto2;
    
    
    
    
    
    
    
	public String getTimed() {
		return timed;
	}
	public void setTimed(String timed) {
		this.timed = timed;
	}
	public String getAr_experience_path1() {
		return ar_experience_path1;
	}
	public void setAr_experience_path1(String ar_experience_path1) {
		this.ar_experience_path1 = ar_experience_path1;
	}
	public String getAr_experience_path2() {
		return ar_experience_path2;
	}
	public void setAr_experience_path2(String ar_experience_path2) {
		this.ar_experience_path2 = ar_experience_path2;
	}
	public String getBhumi_jal_sanrakshan_path1() {
		return bhumi_jal_sanrakshan_path1;
	}
	public void setBhumi_jal_sanrakshan_path1(String bhumi_jal_sanrakshan_path1) {
		this.bhumi_jal_sanrakshan_path1 = bhumi_jal_sanrakshan_path1;
	}
	public String getBhumi_jal_sanrakshan_path2() {
		return bhumi_jal_sanrakshan_path2;
	}
	public void setBhumi_jal_sanrakshan_path2(String bhumi_jal_sanrakshan_path2) {
		this.bhumi_jal_sanrakshan_path2 = bhumi_jal_sanrakshan_path2;
	}
	public String getYatra_film_path1() {
		return yatra_film_path1;
	}
	public void setYatra_film_path1(String yatra_film_path1) {
		this.yatra_film_path1 = yatra_film_path1;
	}
	public String getYatra_film_path2() {
		return yatra_film_path2;
	}
	public void setYatra_film_path2(String yatra_film_path2) {
		this.yatra_film_path2 = yatra_film_path2;
	}
	public String getQuiz_participants_path1() {
		return quiz_participants_path1;
	}
	public void setQuiz_participants_path1(String quiz_participants_path1) {
		this.quiz_participants_path1 = quiz_participants_path1;
	}
	public String getQuiz_participants_path2() {
		return quiz_participants_path2;
	}
	public void setQuiz_participants_path2(String quiz_participants_path2) {
		this.quiz_participants_path2 = quiz_participants_path2;
	}
	public String getCultural_activity_path1() {
		return cultural_activity_path1;
	}
	public void setCultural_activity_path1(String cultural_activity_path1) {
		this.cultural_activity_path1 = cultural_activity_path1;
	}
	public String getCultural_activity_path2() {
		return cultural_activity_path2;
	}
	public void setCultural_activity_path2(String cultural_activity_path2) {
		this.cultural_activity_path2 = cultural_activity_path2;
	}
	public String getBhoomi_poojan_path1() {
		return bhoomi_poojan_path1;
	}
	public void setBhoomi_poojan_path1(String bhoomi_poojan_path1) {
		this.bhoomi_poojan_path1 = bhoomi_poojan_path1;
	}
	public String getBhoomi_poojan_path2() {
		return bhoomi_poojan_path2;
	}
	public void setBhoomi_poojan_path2(String bhoomi_poojan_path2) {
		this.bhoomi_poojan_path2 = bhoomi_poojan_path2;
	}
	public String getLokarpan_path1() {
		return lokarpan_path1;
	}
	public void setLokarpan_path1(String lokarpan_path1) {
		this.lokarpan_path1 = lokarpan_path1;
	}
	public String getLokarpan_path2() {
		return lokarpan_path2;
	}
	public void setLokarpan_path2(String lokarpan_path2) {
		this.lokarpan_path2 = lokarpan_path2;
	}
	public String getShramdaan_path1() {
		return shramdaan_path1;
	}
	public void setShramdaan_path1(String shramdaan_path1) {
		this.shramdaan_path1 = shramdaan_path1;
	}
	public String getShramdaan_path2() {
		return shramdaan_path2;
	}
	public void setShramdaan_path2(String shramdaan_path2) {
		this.shramdaan_path2 = shramdaan_path2;
	}
	public String getPlantation_path1() {
		return plantation_path1;
	}
	public void setPlantation_path1(String plantation_path1) {
		this.plantation_path1 = plantation_path1;
	}
	public String getPlantation_path2() {
		return plantation_path2;
	}
	public void setPlantation_path2(String plantation_path2) {
		this.plantation_path2 = plantation_path2;
	}
	public String getAward_distribution_path1() {
		return award_distribution_path1;
	}
	public void setAward_distribution_path1(String award_distribution_path1) {
		this.award_distribution_path1 = award_distribution_path1;
	}
	public String getAward_distribution_path2() {
		return award_distribution_path2;
	}
	public void setAward_distribution_path2(String award_distribution_path2) {
		this.award_distribution_path2 = award_distribution_path2;
	}
	public Integer getImage_count() {
		return image_count;
	}
	public void setImage_count(Integer image_count) {
		this.image_count = image_count;
	}
	public Integer getWatershed_yatra_id() {
		return watershed_yatra_id;
	}
	public void setWatershed_yatra_id(Integer watershed_yatra_id) {
		this.watershed_yatra_id = watershed_yatra_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getSt_code() {
		return st_code;
	}
	public void setSt_code(Integer st_code) {
		this.st_code = st_code;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public String getDistname() {
		return distname;
	}
	public void setDistname(String distname) {
		this.distname = distname;
	}
	public String getBlockname() {
		return blockname;
	}
	public void setBlockname(String blockname) {
		this.blockname = blockname;
	}
	public String getGpname() {
		return gpname;
	}
	public void setGpname(String gpname) {
		this.gpname = gpname;
	}
	public String getVillagename() {
		return villagename;
	}
	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}
	public Integer getMale_participants() {
		return male_participants;
	}
	public void setMale_participants(Integer male_participants) {
		this.male_participants = male_participants;
	}
	public Integer getFemale_participants() {
		return female_participants;
	}
	public void setFemale_participants(Integer female_participants) {
		this.female_participants = female_participants;
	}
	public Integer getCentral_ministers() {
		return central_ministers;
	}
	public void setCentral_ministers(Integer central_ministers) {
		this.central_ministers = central_ministers;
	}
	public Integer getState_ministers() {
		return state_ministers;
	}
	public void setState_ministers(Integer state_ministers) {
		this.state_ministers = state_ministers;
	}
	public Integer getParliament() {
		return parliament;
	}
	public void setParliament(Integer parliament) {
		this.parliament = parliament;
	}
	public Integer getAssembly_members() {
		return assembly_members;
	}
	public void setAssembly_members(Integer assembly_members) {
		this.assembly_members = assembly_members;
	}
	public Integer getCouncil_members() {
		return council_members;
	}
	public void setCouncil_members(Integer council_members) {
		this.council_members = council_members;
	}
	public Integer getOthers() {
		return others;
	}
	public void setOthers(Integer others) {
		this.others = others;
	}
	public Integer getGov_officials() {
		return gov_officials;
	}
	public void setGov_officials(Integer gov_officials) {
		this.gov_officials = gov_officials;
	}
	public Integer getNo_of_ar_experience_people() {
		return no_of_ar_experience_people;
	}
	public void setNo_of_ar_experience_people(Integer no_of_ar_experience_people) {
		this.no_of_ar_experience_people = no_of_ar_experience_people;
	}
	public String getBhumi_jal_sanrakshan() {
		return bhumi_jal_sanrakshan;
	}
	public void setBhumi_jal_sanrakshan(String bhumi_jal_sanrakshan) {
		this.bhumi_jal_sanrakshan = bhumi_jal_sanrakshan;
	}
	public String getWatershed_yatra_film() {
		return watershed_yatra_film;
	}
	public void setWatershed_yatra_film(String watershed_yatra_film) {
		this.watershed_yatra_film = watershed_yatra_film;
	}
	public Integer getQuiz_participants() {
		return quiz_participants;
	}
	public void setQuiz_participants(Integer quiz_participants) {
		this.quiz_participants = quiz_participants;
	}
	public Integer getCultural_activity_id() {
		return cultural_activity_id;
	}
	public void setCultural_activity_id(Integer cultural_activity_id) {
		this.cultural_activity_id = cultural_activity_id;
	}
	public String getCultural_name() {
		return cultural_name;
	}
	public void setCultural_name(String cultural_name) {
		this.cultural_name = cultural_name;
	}
	public Integer getNo_works_bhoomipoojan() {
		return no_works_bhoomipoojan;
	}
	public void setNo_works_bhoomipoojan(Integer no_works_bhoomipoojan) {
		this.no_works_bhoomipoojan = no_works_bhoomipoojan;
	}
	public BigDecimal getTot_works_bhoomipoojan() {
		return tot_works_bhoomipoojan;
	}
	public void setTot_works_bhoomipoojan(BigDecimal tot_works_bhoomipoojan) {
		this.tot_works_bhoomipoojan = tot_works_bhoomipoojan;
	}
	public Integer getNo_works_lokarpan() {
		return no_works_lokarpan;
	}
	public void setNo_works_lokarpan(Integer no_works_lokarpan) {
		this.no_works_lokarpan = no_works_lokarpan;
	}
	public BigDecimal getTot_works_lokarpan() {
		return tot_works_lokarpan;
	}
	public void setTot_works_lokarpan(BigDecimal tot_works_lokarpan) {
		this.tot_works_lokarpan = tot_works_lokarpan;
	}
	public Integer getNo_location_shramdaan() {
		return no_location_shramdaan;
	}
	public void setNo_location_shramdaan(Integer no_location_shramdaan) {
		this.no_location_shramdaan = no_location_shramdaan;
	}
	public Integer getNo_people_shramdaan() {
		return no_people_shramdaan;
	}
	public void setNo_people_shramdaan(Integer no_people_shramdaan) {
		this.no_people_shramdaan = no_people_shramdaan;
	}
	public BigDecimal getArea_plantation() {
		return area_plantation;
	}
	public void setArea_plantation(BigDecimal area_plantation) {
		this.area_plantation = area_plantation;
	}
	public Integer getNo_plantation() {
		return no_plantation;
	}
	public void setNo_plantation(Integer no_plantation) {
		this.no_plantation = no_plantation;
	}
	public Integer getNo_awards() {
		return no_awards;
	}
	public void setNo_awards(Integer no_awards) {
		this.no_awards = no_awards;
	}
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
	public BigDecimal getBhoomiCost() {
		return bhoomiCost;
	}
	public void setBhoomiCost(BigDecimal bhoomiCost) {
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
	public BigDecimal getCostWorks() {
		return costWorks;
	}
	public void setCostWorks(BigDecimal costWorks) {
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
	public BigDecimal getPlantationArea() {
		return plantationArea;
	}
	public void setPlantationArea(BigDecimal plantationArea) {
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
	 public Integer getManhour() {
			return manhour;
		}
		public void setManhour(Integer manhour) {
			this.manhour = manhour;
		}
	
    
}
