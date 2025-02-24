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
    
    
    
    private String noOfwatershedphoto1_lat;
    private String noOfwatershedphoto1_lng;
    private String noOfwatershedphoto1_time;
    
    private String noOfwatershedphoto2_lat;
    private String noOfwatershedphoto2_lng;
    private String noOfwatershedphoto2_time;
    
    private String plantationAreaphoto1_lat;
    private String plantationAreaphoto1_lng;
    private String plantationAreaphoto1_time;
    
    private String plantationAreaphoto2_lat;
    private String plantationAreaphoto2_lng;
    private String plantationAreaphoto2_time;
    
    private String locShramdaanpsphoto1_lat;
    private String locShramdaanpsphoto1_lng;
    private String locShramdaanpsphoto1_time;
    
    private String locShramdaanpsphoto2_lat;
    private String locShramdaanpsphoto2_lng;
    private String locShramdaanpsphoto2_time;
    
    private String lokWorksphoto1_lat;
    private String lokWorksphoto1_lng;
    private String lokWorksphoto1_time;
    
    private String lokWorksphoto2_lat;
    private String lokWorksphoto2_lng;
    private String lokWorksphoto2_time;
    
    private String bhoomiCostphoto1_lat;
    private String bhoomiCostphoto1_lng;
    private String bhoomiCostphoto1_time;
    
    private String bhoomiCostphoto2_lat;
    private String bhoomiCostphoto2_lng;
    private String bhoomiCostphoto2_time;
    
    private String culturalActivityphoto1_lat;
    private String culturalActivityphoto1_lng;
    private String culturalActivityphoto1_time;
    
    private String culturalActivityphoto2_lat;
    private String culturalActivityphoto2_lng;
    private String culturalActivityphoto2_time;
    
    private String quizParticipantsphoto1_lat;
    private String quizParticipantsphoto1_lng;
    private String quizParticipantsphoto1_time;
    
    private String quizParticipantsphoto2_lat;
    private String quizParticipantsphoto2_lng;
    private String quizParticipantsphoto2_time;
    
    private String FilmYesphoto1_lat;
    private String FilmYesphoto1_lng;
    private String FilmYesphoto1_time;
    
    private String FilmYesphoto2_lat;
    private String FilmYesphoto2_lng;
    private String FilmYesphoto2_time;
    
    private String shapathYesphoto1_lat;
    private String shapathYesphoto1_lng;
    private String shapathYesphoto1_time;
    
    private String shapathYesphoto2_lat;
    private String shapathYesphoto2_lng;
    private String shapathYesphoto2_time;
    
    private String arExperiencephoto1_lat;
    private String arExperiencephoto1_lng;
    private String arExperiencephoto1_time;
    
    private String arExperiencephoto2_lat;
    private String arExperiencephoto2_lng;
    private String arExperiencephoto2_time;
    
    private String remarks;
    private String yatra_date;
    
    private String photocheckar;
    private String photocheckjal;
    private String photocheckfilm;
    private String photocheckquiz;
    private String photocheckcul;
    private String photocheckbhu;
    private String photochecklok;
    private String photochecksdn;
    private String photocheckplt;
    private String photocheckawd;
    
    
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getArExperiencephoto1_lat() {
		return arExperiencephoto1_lat;
	}
	public void setArExperiencephoto1_lat(String arExperiencephoto1_lat) {
		this.arExperiencephoto1_lat = arExperiencephoto1_lat;
	}
	public String getArExperiencephoto1_lng() {
		return arExperiencephoto1_lng;
	}
	public void setArExperiencephoto1_lng(String arExperiencephoto1_lng) {
		this.arExperiencephoto1_lng = arExperiencephoto1_lng;
	}
	public String getArExperiencephoto1_time() {
		return arExperiencephoto1_time;
	}
	public void setArExperiencephoto1_time(String arExperiencephoto1_time) {
		this.arExperiencephoto1_time = arExperiencephoto1_time;
	}
	public String getArExperiencephoto2_lat() {
		return arExperiencephoto2_lat;
	}
	public void setArExperiencephoto2_lat(String arExperiencephoto2_lat) {
		this.arExperiencephoto2_lat = arExperiencephoto2_lat;
	}
	public String getArExperiencephoto2_lng() {
		return arExperiencephoto2_lng;
	}
	public void setArExperiencephoto2_lng(String arExperiencephoto2_lng) {
		this.arExperiencephoto2_lng = arExperiencephoto2_lng;
	}
	public String getArExperiencephoto2_time() {
		return arExperiencephoto2_time;
	}
	public void setArExperiencephoto2_time(String arExperiencephoto2_time) {
		this.arExperiencephoto2_time = arExperiencephoto2_time;
	}
	public String getNoOfwatershedphoto1_lat() {
		return noOfwatershedphoto1_lat;
	}
	public void setNoOfwatershedphoto1_lat(String noOfwatershedphoto1_lat) {
		this.noOfwatershedphoto1_lat = noOfwatershedphoto1_lat;
	}
	public String getNoOfwatershedphoto1_lng() {
		return noOfwatershedphoto1_lng;
	}
	public void setNoOfwatershedphoto1_lng(String noOfwatershedphoto1_lng) {
		this.noOfwatershedphoto1_lng = noOfwatershedphoto1_lng;
	}
	public String getNoOfwatershedphoto1_time() {
		return noOfwatershedphoto1_time;
	}
	public void setNoOfwatershedphoto1_time(String noOfwatershedphoto1_time) {
		this.noOfwatershedphoto1_time = noOfwatershedphoto1_time;
	}
	public String getNoOfwatershedphoto2_lat() {
		return noOfwatershedphoto2_lat;
	}
	public void setNoOfwatershedphoto2_lat(String noOfwatershedphoto2_lat) {
		this.noOfwatershedphoto2_lat = noOfwatershedphoto2_lat;
	}
	public String getNoOfwatershedphoto2_lng() {
		return noOfwatershedphoto2_lng;
	}
	public void setNoOfwatershedphoto2_lng(String noOfwatershedphoto2_lng) {
		this.noOfwatershedphoto2_lng = noOfwatershedphoto2_lng;
	}
	public String getNoOfwatershedphoto2_time() {
		return noOfwatershedphoto2_time;
	}
	public void setNoOfwatershedphoto2_time(String noOfwatershedphoto2_time) {
		this.noOfwatershedphoto2_time = noOfwatershedphoto2_time;
	}
	public String getPlantationAreaphoto1_lat() {
		return plantationAreaphoto1_lat;
	}
	public void setPlantationAreaphoto1_lat(String plantationAreaphoto1_lat) {
		this.plantationAreaphoto1_lat = plantationAreaphoto1_lat;
	}
	public String getPlantationAreaphoto1_lng() {
		return plantationAreaphoto1_lng;
	}
	public void setPlantationAreaphoto1_lng(String plantationAreaphoto1_lng) {
		this.plantationAreaphoto1_lng = plantationAreaphoto1_lng;
	}
	public String getPlantationAreaphoto1_time() {
		return plantationAreaphoto1_time;
	}
	public void setPlantationAreaphoto1_time(String plantationAreaphoto1_time) {
		this.plantationAreaphoto1_time = plantationAreaphoto1_time;
	}
	public String getPlantationAreaphoto2_lat() {
		return plantationAreaphoto2_lat;
	}
	public void setPlantationAreaphoto2_lat(String plantationAreaphoto2_lat) {
		this.plantationAreaphoto2_lat = plantationAreaphoto2_lat;
	}
	public String getPlantationAreaphoto2_lng() {
		return plantationAreaphoto2_lng;
	}
	public void setPlantationAreaphoto2_lng(String plantationAreaphoto2_lng) {
		this.plantationAreaphoto2_lng = plantationAreaphoto2_lng;
	}
	public String getPlantationAreaphoto2_time() {
		return plantationAreaphoto2_time;
	}
	public void setPlantationAreaphoto2_time(String plantationAreaphoto2_time) {
		this.plantationAreaphoto2_time = plantationAreaphoto2_time;
	}
	public String getLocShramdaanpsphoto1_lat() {
		return locShramdaanpsphoto1_lat;
	}
	public void setLocShramdaanpsphoto1_lat(String locShramdaanpsphoto1_lat) {
		this.locShramdaanpsphoto1_lat = locShramdaanpsphoto1_lat;
	}
	public String getLocShramdaanpsphoto1_lng() {
		return locShramdaanpsphoto1_lng;
	}
	public void setLocShramdaanpsphoto1_lng(String locShramdaanpsphoto1_lng) {
		this.locShramdaanpsphoto1_lng = locShramdaanpsphoto1_lng;
	}
	public String getLocShramdaanpsphoto1_time() {
		return locShramdaanpsphoto1_time;
	}
	public void setLocShramdaanpsphoto1_time(String locShramdaanpsphoto1_time) {
		this.locShramdaanpsphoto1_time = locShramdaanpsphoto1_time;
	}
	public String getLocShramdaanpsphoto2_lat() {
		return locShramdaanpsphoto2_lat;
	}
	public void setLocShramdaanpsphoto2_lat(String locShramdaanpsphoto2_lat) {
		this.locShramdaanpsphoto2_lat = locShramdaanpsphoto2_lat;
	}
	public String getLocShramdaanpsphoto2_lng() {
		return locShramdaanpsphoto2_lng;
	}
	public void setLocShramdaanpsphoto2_lng(String locShramdaanpsphoto2_lng) {
		this.locShramdaanpsphoto2_lng = locShramdaanpsphoto2_lng;
	}
	public String getLocShramdaanpsphoto2_time() {
		return locShramdaanpsphoto2_time;
	}
	public void setLocShramdaanpsphoto2_time(String locShramdaanpsphoto2_time) {
		this.locShramdaanpsphoto2_time = locShramdaanpsphoto2_time;
	}
	public String getLokWorksphoto1_lat() {
		return lokWorksphoto1_lat;
	}
	public void setLokWorksphoto1_lat(String lokWorksphoto1_lat) {
		this.lokWorksphoto1_lat = lokWorksphoto1_lat;
	}
	public String getLokWorksphoto1_lng() {
		return lokWorksphoto1_lng;
	}
	public void setLokWorksphoto1_lng(String lokWorksphoto1_lng) {
		this.lokWorksphoto1_lng = lokWorksphoto1_lng;
	}
	public String getLokWorksphoto1_time() {
		return lokWorksphoto1_time;
	}
	public void setLokWorksphoto1_time(String lokWorksphoto1_time) {
		this.lokWorksphoto1_time = lokWorksphoto1_time;
	}
	public String getLokWorksphoto2_lat() {
		return lokWorksphoto2_lat;
	}
	public void setLokWorksphoto2_lat(String lokWorksphoto2_lat) {
		this.lokWorksphoto2_lat = lokWorksphoto2_lat;
	}
	public String getLokWorksphoto2_lng() {
		return lokWorksphoto2_lng;
	}
	public void setLokWorksphoto2_lng(String lokWorksphoto2_lng) {
		this.lokWorksphoto2_lng = lokWorksphoto2_lng;
	}
	public String getLokWorksphoto2_time() {
		return lokWorksphoto2_time;
	}
	public void setLokWorksphoto2_time(String lokWorksphoto2_time) {
		this.lokWorksphoto2_time = lokWorksphoto2_time;
	}
	public String getBhoomiCostphoto1_lat() {
		return bhoomiCostphoto1_lat;
	}
	public void setBhoomiCostphoto1_lat(String bhoomiCostphoto1_lat) {
		this.bhoomiCostphoto1_lat = bhoomiCostphoto1_lat;
	}
	public String getBhoomiCostphoto1_lng() {
		return bhoomiCostphoto1_lng;
	}
	public void setBhoomiCostphoto1_lng(String bhoomiCostphoto1_lng) {
		this.bhoomiCostphoto1_lng = bhoomiCostphoto1_lng;
	}
	public String getBhoomiCostphoto1_time() {
		return bhoomiCostphoto1_time;
	}
	public void setBhoomiCostphoto1_time(String bhoomiCostphoto1_time) {
		this.bhoomiCostphoto1_time = bhoomiCostphoto1_time;
	}
	public String getBhoomiCostphoto2_lat() {
		return bhoomiCostphoto2_lat;
	}
	public void setBhoomiCostphoto2_lat(String bhoomiCostphoto2_lat) {
		this.bhoomiCostphoto2_lat = bhoomiCostphoto2_lat;
	}
	public String getBhoomiCostphoto2_lng() {
		return bhoomiCostphoto2_lng;
	}
	public void setBhoomiCostphoto2_lng(String bhoomiCostphoto2_lng) {
		this.bhoomiCostphoto2_lng = bhoomiCostphoto2_lng;
	}
	public String getBhoomiCostphoto2_time() {
		return bhoomiCostphoto2_time;
	}
	public void setBhoomiCostphoto2_time(String bhoomiCostphoto2_time) {
		this.bhoomiCostphoto2_time = bhoomiCostphoto2_time;
	}
	public String getCulturalActivityphoto1_lat() {
		return culturalActivityphoto1_lat;
	}
	public void setCulturalActivityphoto1_lat(String culturalActivityphoto1_lat) {
		this.culturalActivityphoto1_lat = culturalActivityphoto1_lat;
	}
	public String getCulturalActivityphoto1_lng() {
		return culturalActivityphoto1_lng;
	}
	public void setCulturalActivityphoto1_lng(String culturalActivityphoto1_lng) {
		this.culturalActivityphoto1_lng = culturalActivityphoto1_lng;
	}
	public String getCulturalActivityphoto1_time() {
		return culturalActivityphoto1_time;
	}
	public void setCulturalActivityphoto1_time(String culturalActivityphoto1_time) {
		this.culturalActivityphoto1_time = culturalActivityphoto1_time;
	}
	public String getCulturalActivityphoto2_lat() {
		return culturalActivityphoto2_lat;
	}
	public void setCulturalActivityphoto2_lat(String culturalActivityphoto2_lat) {
		this.culturalActivityphoto2_lat = culturalActivityphoto2_lat;
	}
	public String getCulturalActivityphoto2_lng() {
		return culturalActivityphoto2_lng;
	}
	public void setCulturalActivityphoto2_lng(String culturalActivityphoto2_lng) {
		this.culturalActivityphoto2_lng = culturalActivityphoto2_lng;
	}
	public String getCulturalActivityphoto2_time() {
		return culturalActivityphoto2_time;
	}
	public void setCulturalActivityphoto2_time(String culturalActivityphoto2_time) {
		this.culturalActivityphoto2_time = culturalActivityphoto2_time;
	}
	public String getQuizParticipantsphoto1_lat() {
		return quizParticipantsphoto1_lat;
	}
	public void setQuizParticipantsphoto1_lat(String quizParticipantsphoto1_lat) {
		this.quizParticipantsphoto1_lat = quizParticipantsphoto1_lat;
	}
	public String getQuizParticipantsphoto1_lng() {
		return quizParticipantsphoto1_lng;
	}
	public void setQuizParticipantsphoto1_lng(String quizParticipantsphoto1_lng) {
		this.quizParticipantsphoto1_lng = quizParticipantsphoto1_lng;
	}
	public String getQuizParticipantsphoto1_time() {
		return quizParticipantsphoto1_time;
	}
	public void setQuizParticipantsphoto1_time(String quizParticipantsphoto1_time) {
		this.quizParticipantsphoto1_time = quizParticipantsphoto1_time;
	}
	public String getQuizParticipantsphoto2_lat() {
		return quizParticipantsphoto2_lat;
	}
	public void setQuizParticipantsphoto2_lat(String quizParticipantsphoto2_lat) {
		this.quizParticipantsphoto2_lat = quizParticipantsphoto2_lat;
	}
	public String getQuizParticipantsphoto2_lng() {
		return quizParticipantsphoto2_lng;
	}
	public void setQuizParticipantsphoto2_lng(String quizParticipantsphoto2_lng) {
		this.quizParticipantsphoto2_lng = quizParticipantsphoto2_lng;
	}
	public String getQuizParticipantsphoto2_time() {
		return quizParticipantsphoto2_time;
	}
	public void setQuizParticipantsphoto2_time(String quizParticipantsphoto2_time) {
		this.quizParticipantsphoto2_time = quizParticipantsphoto2_time;
	}
	public String getFilmYesphoto1_lat() {
		return FilmYesphoto1_lat;
	}
	public void setFilmYesphoto1_lat(String filmYesphoto1_lat) {
		FilmYesphoto1_lat = filmYesphoto1_lat;
	}
	public String getFilmYesphoto1_lng() {
		return FilmYesphoto1_lng;
	}
	public void setFilmYesphoto1_lng(String filmYesphoto1_lng) {
		FilmYesphoto1_lng = filmYesphoto1_lng;
	}
	public String getFilmYesphoto1_time() {
		return FilmYesphoto1_time;
	}
	public void setFilmYesphoto1_time(String filmYesphoto1_time) {
		FilmYesphoto1_time = filmYesphoto1_time;
	}
	public String getFilmYesphoto2_lat() {
		return FilmYesphoto2_lat;
	}
	public void setFilmYesphoto2_lat(String filmYesphoto2_lat) {
		FilmYesphoto2_lat = filmYesphoto2_lat;
	}
	public String getFilmYesphoto2_lng() {
		return FilmYesphoto2_lng;
	}
	public void setFilmYesphoto2_lng(String filmYesphoto2_lng) {
		FilmYesphoto2_lng = filmYesphoto2_lng;
	}
	public String getFilmYesphoto2_time() {
		return FilmYesphoto2_time;
	}
	public void setFilmYesphoto2_time(String filmYesphoto2_time) {
		FilmYesphoto2_time = filmYesphoto2_time;
	}
	public String getShapathYesphoto1_lat() {
		return shapathYesphoto1_lat;
	}
	public void setShapathYesphoto1_lat(String shapathYesphoto1_lat) {
		this.shapathYesphoto1_lat = shapathYesphoto1_lat;
	}
	public String getShapathYesphoto1_lng() {
		return shapathYesphoto1_lng;
	}
	public void setShapathYesphoto1_lng(String shapathYesphoto1_lng) {
		this.shapathYesphoto1_lng = shapathYesphoto1_lng;
	}
	public String getShapathYesphoto1_time() {
		return shapathYesphoto1_time;
	}
	public void setShapathYesphoto1_time(String shapathYesphoto1_time) {
		this.shapathYesphoto1_time = shapathYesphoto1_time;
	}
	public String getShapathYesphoto2_lat() {
		return shapathYesphoto2_lat;
	}
	public void setShapathYesphoto2_lat(String shapathYesphoto2_lat) {
		this.shapathYesphoto2_lat = shapathYesphoto2_lat;
	}
	public String getShapathYesphoto2_lng() {
		return shapathYesphoto2_lng;
	}
	public void setShapathYesphoto2_lng(String shapathYesphoto2_lng) {
		this.shapathYesphoto2_lng = shapathYesphoto2_lng;
	}
	public String getShapathYesphoto2_time() {
		return shapathYesphoto2_time;
	}
	public void setShapathYesphoto2_time(String shapathYesphoto2_time) {
		this.shapathYesphoto2_time = shapathYesphoto2_time;
	}
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
	public String getYatra_date() {
		return yatra_date;
	}
	public void setYatra_date(String yatra_date) {
		this.yatra_date = yatra_date;
	}
	public String getPhotocheckar() {
		return photocheckar;
	}
	public void setPhotocheckar(String photocheckar) {
		this.photocheckar = photocheckar;
	}
	public String getPhotocheckjal() {
		return photocheckjal;
	}
	public void setPhotocheckjal(String photocheckjal) {
		this.photocheckjal = photocheckjal;
	}
	public String getPhotocheckfilm() {
		return photocheckfilm;
	}
	public void setPhotocheckfilm(String photocheckfilm) {
		this.photocheckfilm = photocheckfilm;
	}
	public String getPhotocheckquiz() {
		return photocheckquiz;
	}
	public void setPhotocheckquiz(String photocheckquiz) {
		this.photocheckquiz = photocheckquiz;
	}
	public String getPhotocheckcul() {
		return photocheckcul;
	}
	public void setPhotocheckcul(String photocheckcul) {
		this.photocheckcul = photocheckcul;
	}
	public String getPhotocheckbhu() {
		return photocheckbhu;
	}
	public void setPhotocheckbhu(String photocheckbhu) {
		this.photocheckbhu = photocheckbhu;
	}
	public String getPhotochecklok() {
		return photochecklok;
	}
	public void setPhotochecklok(String photochecklok) {
		this.photochecklok = photochecklok;
	}
	public String getPhotochecksdn() {
		return photochecksdn;
	}
	public void setPhotochecksdn(String photochecksdn) {
		this.photochecksdn = photochecksdn;
	}
	public String getPhotocheckplt() {
		return photocheckplt;
	}
	public void setPhotocheckplt(String photocheckplt) {
		this.photocheckplt = photocheckplt;
	}
	public String getPhotocheckawd() {
		return photocheckawd;
	}
	public void setPhotocheckawd(String photocheckawd) {
		this.photocheckawd = photocheckawd;
	}
	
	
	
    
}
