package app.watershedyatra.bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class InaugurationBean {
	
    private Integer district;
    private Integer block;
    private String date;
    private String location;
    private Integer male_participants;
    private Integer female_participants;
    private Integer central_ministers;
    private Integer state_ministers;
    private Integer parliament;
    private Integer assembly_members;
    private Integer council_members;
    private Integer others;
    private Integer gov_officials;
    private String flagoff;
    private MultipartFile flagoff_photo1;
    private String flagoff_photo1_lat;
    private String flagoff_photo1_lng;
    private String flagoff_photo1_time;
    private MultipartFile flagoff_photo2;
    private String flagoff_photo2_lat;
    private String flagoff_photo2_lng;
    private String flagoff_photo2_time;
    private String themesong;
    private MultipartFile themesong_photo1;
    private String themesong_photo1_lat;
    private String themesong_photo1_lng;
    private String themesong_photo1_time;
    private MultipartFile themesong_photo2;
    private String themesong_photo2_lat;
    private String themesong_photo2_lng;
    private String themesong_photo2_time;
    private Integer no_works_bhoomipoojan;
    private BigDecimal tot_works_bhoomipoojan;
    private MultipartFile bhoomipoojan_photo1;
    private String bhoomipoojan_photo1_lat;
    private String bhoomipoojan_photo1_lng;
    private String bhoomipoojan_photo1_time;
    private MultipartFile bhoomipoojan_photo2;
    private String bhoomipoojan_photo2_lat;
    private String bhoomipoojan_photo2_lng;
    private String bhoomipoojan_photo2_time;
    private Integer no_works_lokarpan;
    private BigDecimal tot_works_lokarpan;
    private MultipartFile lokarpan_photo1;
    private String lokarpan_photo1_lat;
    private String lokarpan_photo1_lng;
    private String lokarpan_photo1_time;
    private MultipartFile lokarpan_photo2;
    private String lokarpan_photo2_lat;
    private String lokarpan_photo2_lng;
    private String lokarpan_photo2_time;
    private Integer no_location_shramdaan;
    private Integer no_people_shramdaan;
    private Integer man;
    private MultipartFile shramdaan_photo1;
    private String shramdaan_photo1_lat;
    private String shramdaan_photo1_lng;
    private String shramdaan_photo1_time;
    private MultipartFile shramdaan_photo2;
    private String shramdaan_photo2_lat;
    private String shramdaan_photo2_lng;
    private String shramdaan_photo2_time;
    private BigDecimal area_plantation;
    private Integer no_plantation;
    private MultipartFile plantation_photo1;
    private String plantation_photo1_lat;
    private String plantation_photo1_lng;
    private String plantation_photo1_time;
    private MultipartFile plantation_photo2;
    private String plantation_photo2_lat;
    private String plantation_photo2_lng;
    private String plantation_photo2_time;
    private Integer no_awards;
    private MultipartFile award_photo1;
    private String award_photo1_lat;
    private String award_photo1_lng;
    private String award_photo1_time;
    private MultipartFile award_photo2;
    private String award_photo2_lat;
    private String award_photo2_lng;
    private String award_photo2_time;
    private Integer dept_stalls;
    private MultipartFile dept_stalls_photo1;
    private String dept_stalls_photo1_lat;
    private String dept_stalls_photo1_lng;
    private String dept_stalls_photo1_time;
    private MultipartFile dept_stalls_photo2;
    private String dept_stalls_photo2_lat;
    private String dept_stalls_photo2_lng;
    private String dept_stalls_photo2_time;
    private Integer shg_fpo_stalls;
    private MultipartFile shg_fpo_stalls_photo1;
    private String shg_fpo_stalls_photo1_lat;
    private String shg_fpo_stalls_photo1_lng;
    private String shg_fpo_stalls_photo1_time;
    private MultipartFile shg_fpo_stalls_photo2;
    private String shg_fpo_stalls_photo2_lat;
    private String shg_fpo_stalls_photo2_lng;
    private String shg_fpo_stalls_photo2_time;
    private Integer no_lakhpati_didi;
    private MultipartFile lakhpati_didi_photo1;
    private String lakhpati_didi_photo1_lat;
    private String lakhpati_didi_photo1_lng;
    private String lakhpati_didi_photo1_time;
    private MultipartFile lakhpati_didi_photo2;
    private String lakhpati_didi_photo2_lat;
    private String lakhpati_didi_photo2_lng;
    private String lakhpati_didi_photo2_time;
    private Integer inauguaration_id;
    private Integer st_code;
    private String stname;
    private String distname;
    private String blockname;
    private Integer image_count;
    
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public String getFlagoff() {
		return flagoff;
	}
	public void setFlagoff(String flagoff) {
		this.flagoff = flagoff;
	}
	public MultipartFile getFlagoff_photo1() {
		return flagoff_photo1;
	}
	public void setFlagoff_photo1(MultipartFile flagoff_photo1) {
		this.flagoff_photo1 = flagoff_photo1;
	}
	public MultipartFile getFlagoff_photo2() {
		return flagoff_photo2;
	}
	public void setFlagoff_photo2(MultipartFile flagoff_photo2) {
		this.flagoff_photo2 = flagoff_photo2;
	}
	public String getThemesong() {
		return themesong;
	}
	public void setThemesong(String themesong) {
		this.themesong = themesong;
	}
	public MultipartFile getThemesong_photo1() {
		return themesong_photo1;
	}
	public void setThemesong_photo1(MultipartFile themesong_photo1) {
		this.themesong_photo1 = themesong_photo1;
	}
	public MultipartFile getThemesong_photo2() {
		return themesong_photo2;
	}
	public void setThemesong_photo2(MultipartFile themesong_photo2) {
		this.themesong_photo2 = themesong_photo2;
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
	public MultipartFile getBhoomipoojan_photo1() {
		return bhoomipoojan_photo1;
	}
	public void setBhoomipoojan_photo1(MultipartFile bhoomipoojan_photo1) {
		this.bhoomipoojan_photo1 = bhoomipoojan_photo1;
	}
	public MultipartFile getBhoomipoojan_photo2() {
		return bhoomipoojan_photo2;
	}
	public void setBhoomipoojan_photo2(MultipartFile bhoomipoojan_photo2) {
		this.bhoomipoojan_photo2 = bhoomipoojan_photo2;
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
	public MultipartFile getLokarpan_photo1() {
		return lokarpan_photo1;
	}
	public void setLokarpan_photo1(MultipartFile lokarpan_photo1) {
		this.lokarpan_photo1 = lokarpan_photo1;
	}
	public MultipartFile getLokarpan_photo2() {
		return lokarpan_photo2;
	}
	public void setLokarpan_photo2(MultipartFile lokarpan_photo2) {
		this.lokarpan_photo2 = lokarpan_photo2;
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
	public Integer getMan() {
		return man;
	}
	public void setMan(Integer man) {
		this.man = man;
	}
	public MultipartFile getShramdaan_photo1() {
		return shramdaan_photo1;
	}
	public void setShramdaan_photo1(MultipartFile shramdaan_photo1) {
		this.shramdaan_photo1 = shramdaan_photo1;
	}
	public MultipartFile getShramdaan_photo2() {
		return shramdaan_photo2;
	}
	public void setShramdaan_photo2(MultipartFile shramdaan_photo2) {
		this.shramdaan_photo2 = shramdaan_photo2;
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
	public MultipartFile getPlantation_photo1() {
		return plantation_photo1;
	}
	public void setPlantation_photo1(MultipartFile plantation_photo1) {
		this.plantation_photo1 = plantation_photo1;
	}
	public MultipartFile getPlantation_photo2() {
		return plantation_photo2;
	}
	public void setPlantation_photo2(MultipartFile plantation_photo2) {
		this.plantation_photo2 = plantation_photo2;
	}
	public Integer getNo_awards() {
		return no_awards;
	}
	public void setNo_awards(Integer no_awards) {
		this.no_awards = no_awards;
	}
	public MultipartFile getAward_photo1() {
		return award_photo1;
	}
	public void setAward_photo1(MultipartFile award_photo1) {
		this.award_photo1 = award_photo1;
	}
	public MultipartFile getAward_photo2() {
		return award_photo2;
	}
	public void setAward_photo2(MultipartFile award_photo2) {
		this.award_photo2 = award_photo2;
	}
	public Integer getDept_stalls() {
		return dept_stalls;
	}
	public void setDept_stalls(Integer dept_stalls) {
		this.dept_stalls = dept_stalls;
	}
	public MultipartFile getDept_stalls_photo1() {
		return dept_stalls_photo1;
	}
	public void setDept_stalls_photo1(MultipartFile dept_stalls_photo1) {
		this.dept_stalls_photo1 = dept_stalls_photo1;
	}
	public MultipartFile getDept_stalls_photo2() {
		return dept_stalls_photo2;
	}
	public void setDept_stalls_photo2(MultipartFile dept_stalls_photo2) {
		this.dept_stalls_photo2 = dept_stalls_photo2;
	}
	public Integer getShg_fpo_stalls() {
		return shg_fpo_stalls;
	}
	public void setShg_fpo_stalls(Integer shg_fpo_stalls) {
		this.shg_fpo_stalls = shg_fpo_stalls;
	}
	public MultipartFile getShg_fpo_stalls_photo1() {
		return shg_fpo_stalls_photo1;
	}
	public void setShg_fpo_stalls_photo1(MultipartFile shg_fpo_stalls_photo1) {
		this.shg_fpo_stalls_photo1 = shg_fpo_stalls_photo1;
	}
	public MultipartFile getShg_fpo_stalls_photo2() {
		return shg_fpo_stalls_photo2;
	}
	public void setShg_fpo_stalls_photo2(MultipartFile shg_fpo_stalls_photo2) {
		this.shg_fpo_stalls_photo2 = shg_fpo_stalls_photo2;
	}
	public Integer getNo_lakhpati_didi() {
		return no_lakhpati_didi;
	}
	public void setNo_lakhpati_didi(Integer no_lakhpati_didi) {
		this.no_lakhpati_didi = no_lakhpati_didi;
	}
	public MultipartFile getLakhpati_didi_photo1() {
		return lakhpati_didi_photo1;
	}
	public void setLakhpati_didi_photo1(MultipartFile lakhpati_didi_photo1) {
		this.lakhpati_didi_photo1 = lakhpati_didi_photo1;
	}
	public MultipartFile getLakhpati_didi_photo2() {
		return lakhpati_didi_photo2;
	}
	public void setLakhpati_didi_photo2(MultipartFile lakhpati_didi_photo2) {
		this.lakhpati_didi_photo2 = lakhpati_didi_photo2;
	}
	public Integer getInauguaration_id() {
		return inauguaration_id;
	}
	public void setInauguaration_id(Integer inauguaration_id) {
		this.inauguaration_id = inauguaration_id;
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
	public Integer getImage_count() {
		return image_count;
	}
	public void setImage_count(Integer image_count) {
		this.image_count = image_count;
	}
	public String getFlagoff_photo1_lat() {
		return flagoff_photo1_lat;
	}
	public void setFlagoff_photo1_lat(String flagoff_photo1_lat) {
		this.flagoff_photo1_lat = flagoff_photo1_lat;
	}
	public String getFlagoff_photo1_lng() {
		return flagoff_photo1_lng;
	}
	public void setFlagoff_photo1_lng(String flagoff_photo1_lng) {
		this.flagoff_photo1_lng = flagoff_photo1_lng;
	}
	public String getFlagoff_photo1_time() {
		return flagoff_photo1_time;
	}
	public void setFlagoff_photo1_time(String flagoff_photo1_time) {
		this.flagoff_photo1_time = flagoff_photo1_time;
	}
	public String getFlagoff_photo2_lat() {
		return flagoff_photo2_lat;
	}
	public void setFlagoff_photo2_lat(String flagoff_photo2_lat) {
		this.flagoff_photo2_lat = flagoff_photo2_lat;
	}
	public String getFlagoff_photo2_lng() {
		return flagoff_photo2_lng;
	}
	public void setFlagoff_photo2_lng(String flagoff_photo2_lng) {
		this.flagoff_photo2_lng = flagoff_photo2_lng;
	}
	public String getFlagoff_photo2_time() {
		return flagoff_photo2_time;
	}
	public void setFlagoff_photo2_time(String flagoff_photo2_time) {
		this.flagoff_photo2_time = flagoff_photo2_time;
	}
	public String getThemesong_photo1_lat() {
		return themesong_photo1_lat;
	}
	public void setThemesong_photo1_lat(String themesong_photo1_lat) {
		this.themesong_photo1_lat = themesong_photo1_lat;
	}
	public String getThemesong_photo1_lng() {
		return themesong_photo1_lng;
	}
	public void setThemesong_photo1_lng(String themesong_photo1_lng) {
		this.themesong_photo1_lng = themesong_photo1_lng;
	}
	public String getThemesong_photo1_time() {
		return themesong_photo1_time;
	}
	public void setThemesong_photo1_time(String themesong_photo1_time) {
		this.themesong_photo1_time = themesong_photo1_time;
	}
	public String getThemesong_photo2_lat() {
		return themesong_photo2_lat;
	}
	public void setThemesong_photo2_lat(String themesong_photo2_lat) {
		this.themesong_photo2_lat = themesong_photo2_lat;
	}
	public String getThemesong_photo2_lng() {
		return themesong_photo2_lng;
	}
	public void setThemesong_photo2_lng(String themesong_photo2_lng) {
		this.themesong_photo2_lng = themesong_photo2_lng;
	}
	public String getThemesong_photo2_time() {
		return themesong_photo2_time;
	}
	public void setThemesong_photo2_time(String themesong_photo2_time) {
		this.themesong_photo2_time = themesong_photo2_time;
	}
	public String getBhoomipoojan_photo1_lat() {
		return bhoomipoojan_photo1_lat;
	}
	public void setBhoomipoojan_photo1_lat(String bhoomipoojan_photo1_lat) {
		this.bhoomipoojan_photo1_lat = bhoomipoojan_photo1_lat;
	}
	public String getBhoomipoojan_photo1_lng() {
		return bhoomipoojan_photo1_lng;
	}
	public void setBhoomipoojan_photo1_lng(String bhoomipoojan_photo1_lng) {
		this.bhoomipoojan_photo1_lng = bhoomipoojan_photo1_lng;
	}
	public String getBhoomipoojan_photo1_time() {
		return bhoomipoojan_photo1_time;
	}
	public void setBhoomipoojan_photo1_time(String bhoomipoojan_photo1_time) {
		this.bhoomipoojan_photo1_time = bhoomipoojan_photo1_time;
	}
	public String getBhoomipoojan_photo2_lat() {
		return bhoomipoojan_photo2_lat;
	}
	public void setBhoomipoojan_photo2_lat(String bhoomipoojan_photo2_lat) {
		this.bhoomipoojan_photo2_lat = bhoomipoojan_photo2_lat;
	}
	public String getBhoomipoojan_photo2_lng() {
		return bhoomipoojan_photo2_lng;
	}
	public void setBhoomipoojan_photo2_lng(String bhoomipoojan_photo2_lng) {
		this.bhoomipoojan_photo2_lng = bhoomipoojan_photo2_lng;
	}
	public String getBhoomipoojan_photo2_time() {
		return bhoomipoojan_photo2_time;
	}
	public void setBhoomipoojan_photo2_time(String bhoomipoojan_photo2_time) {
		this.bhoomipoojan_photo2_time = bhoomipoojan_photo2_time;
	}
	public String getLokarpan_photo1_lat() {
		return lokarpan_photo1_lat;
	}
	public void setLokarpan_photo1_lat(String lokarpan_photo1_lat) {
		this.lokarpan_photo1_lat = lokarpan_photo1_lat;
	}
	public String getLokarpan_photo1_lng() {
		return lokarpan_photo1_lng;
	}
	public void setLokarpan_photo1_lng(String lokarpan_photo1_lng) {
		this.lokarpan_photo1_lng = lokarpan_photo1_lng;
	}
	public String getLokarpan_photo1_time() {
		return lokarpan_photo1_time;
	}
	public void setLokarpan_photo1_time(String lokarpan_photo1_time) {
		this.lokarpan_photo1_time = lokarpan_photo1_time;
	}
	public String getLokarpan_photo2_lat() {
		return lokarpan_photo2_lat;
	}
	public void setLokarpan_photo2_lat(String lokarpan_photo2_lat) {
		this.lokarpan_photo2_lat = lokarpan_photo2_lat;
	}
	public String getLokarpan_photo2_lng() {
		return lokarpan_photo2_lng;
	}
	public void setLokarpan_photo2_lng(String lokarpan_photo2_lng) {
		this.lokarpan_photo2_lng = lokarpan_photo2_lng;
	}
	public String getLokarpan_photo2_time() {
		return lokarpan_photo2_time;
	}
	public void setLokarpan_photo2_time(String lokarpan_photo2_time) {
		this.lokarpan_photo2_time = lokarpan_photo2_time;
	}
	public String getShramdaan_photo1_lat() {
		return shramdaan_photo1_lat;
	}
	public void setShramdaan_photo1_lat(String shramdaan_photo1_lat) {
		this.shramdaan_photo1_lat = shramdaan_photo1_lat;
	}
	public String getShramdaan_photo1_lng() {
		return shramdaan_photo1_lng;
	}
	public void setShramdaan_photo1_lng(String shramdaan_photo1_lng) {
		this.shramdaan_photo1_lng = shramdaan_photo1_lng;
	}
	public String getShramdaan_photo1_time() {
		return shramdaan_photo1_time;
	}
	public void setShramdaan_photo1_time(String shramdaan_photo1_time) {
		this.shramdaan_photo1_time = shramdaan_photo1_time;
	}
	public String getShramdaan_photo2_lat() {
		return shramdaan_photo2_lat;
	}
	public void setShramdaan_photo2_lat(String shramdaan_photo2_lat) {
		this.shramdaan_photo2_lat = shramdaan_photo2_lat;
	}
	public String getShramdaan_photo2_lng() {
		return shramdaan_photo2_lng;
	}
	public void setShramdaan_photo2_lng(String shramdaan_photo2_lng) {
		this.shramdaan_photo2_lng = shramdaan_photo2_lng;
	}
	public String getShramdaan_photo2_time() {
		return shramdaan_photo2_time;
	}
	public void setShramdaan_photo2_time(String shramdaan_photo2_time) {
		this.shramdaan_photo2_time = shramdaan_photo2_time;
	}
	public String getPlantation_photo1_lat() {
		return plantation_photo1_lat;
	}
	public void setPlantation_photo1_lat(String plantation_photo1_lat) {
		this.plantation_photo1_lat = plantation_photo1_lat;
	}
	public String getPlantation_photo1_lng() {
		return plantation_photo1_lng;
	}
	public void setPlantation_photo1_lng(String plantation_photo1_lng) {
		this.plantation_photo1_lng = plantation_photo1_lng;
	}
	public String getPlantation_photo1_time() {
		return plantation_photo1_time;
	}
	public void setPlantation_photo1_time(String plantation_photo1_time) {
		this.plantation_photo1_time = plantation_photo1_time;
	}
	public String getPlantation_photo2_lat() {
		return plantation_photo2_lat;
	}
	public void setPlantation_photo2_lat(String plantation_photo2_lat) {
		this.plantation_photo2_lat = plantation_photo2_lat;
	}
	public String getPlantation_photo2_lng() {
		return plantation_photo2_lng;
	}
	public void setPlantation_photo2_lng(String plantation_photo2_lng) {
		this.plantation_photo2_lng = plantation_photo2_lng;
	}
	public String getPlantation_photo2_time() {
		return plantation_photo2_time;
	}
	public void setPlantation_photo2_time(String plantation_photo2_time) {
		this.plantation_photo2_time = plantation_photo2_time;
	}
	public String getAward_photo1_lat() {
		return award_photo1_lat;
	}
	public void setAward_photo1_lat(String award_photo1_lat) {
		this.award_photo1_lat = award_photo1_lat;
	}
	public String getAward_photo1_lng() {
		return award_photo1_lng;
	}
	public void setAward_photo1_lng(String award_photo1_lng) {
		this.award_photo1_lng = award_photo1_lng;
	}
	public String getAward_photo1_time() {
		return award_photo1_time;
	}
	public void setAward_photo1_time(String award_photo1_time) {
		this.award_photo1_time = award_photo1_time;
	}
	public String getAward_photo2_lat() {
		return award_photo2_lat;
	}
	public void setAward_photo2_lat(String award_photo2_lat) {
		this.award_photo2_lat = award_photo2_lat;
	}
	public String getAward_photo2_lng() {
		return award_photo2_lng;
	}
	public void setAward_photo2_lng(String award_photo2_lng) {
		this.award_photo2_lng = award_photo2_lng;
	}
	public String getAward_photo2_time() {
		return award_photo2_time;
	}
	public void setAward_photo2_time(String award_photo2_time) {
		this.award_photo2_time = award_photo2_time;
	}
	public String getDept_stalls_photo1_lat() {
		return dept_stalls_photo1_lat;
	}
	public void setDept_stalls_photo1_lat(String dept_stalls_photo1_lat) {
		this.dept_stalls_photo1_lat = dept_stalls_photo1_lat;
	}
	public String getDept_stalls_photo1_lng() {
		return dept_stalls_photo1_lng;
	}
	public void setDept_stalls_photo1_lng(String dept_stalls_photo1_lng) {
		this.dept_stalls_photo1_lng = dept_stalls_photo1_lng;
	}
	public String getDept_stalls_photo1_time() {
		return dept_stalls_photo1_time;
	}
	public void setDept_stalls_photo1_time(String dept_stalls_photo1_time) {
		this.dept_stalls_photo1_time = dept_stalls_photo1_time;
	}
	public String getDept_stalls_photo2_lat() {
		return dept_stalls_photo2_lat;
	}
	public void setDept_stalls_photo2_lat(String dept_stalls_photo2_lat) {
		this.dept_stalls_photo2_lat = dept_stalls_photo2_lat;
	}
	public String getDept_stalls_photo2_lng() {
		return dept_stalls_photo2_lng;
	}
	public void setDept_stalls_photo2_lng(String dept_stalls_photo2_lng) {
		this.dept_stalls_photo2_lng = dept_stalls_photo2_lng;
	}
	public String getDept_stalls_photo2_time() {
		return dept_stalls_photo2_time;
	}
	public void setDept_stalls_photo2_time(String dept_stalls_photo2_time) {
		this.dept_stalls_photo2_time = dept_stalls_photo2_time;
	}
	public String getShg_fpo_stalls_photo1_lat() {
		return shg_fpo_stalls_photo1_lat;
	}
	public void setShg_fpo_stalls_photo1_lat(String shg_fpo_stalls_photo1_lat) {
		this.shg_fpo_stalls_photo1_lat = shg_fpo_stalls_photo1_lat;
	}
	public String getShg_fpo_stalls_photo1_lng() {
		return shg_fpo_stalls_photo1_lng;
	}
	public void setShg_fpo_stalls_photo1_lng(String shg_fpo_stalls_photo1_lng) {
		this.shg_fpo_stalls_photo1_lng = shg_fpo_stalls_photo1_lng;
	}
	public String getShg_fpo_stalls_photo1_time() {
		return shg_fpo_stalls_photo1_time;
	}
	public void setShg_fpo_stalls_photo1_time(String shg_fpo_stalls_photo1_time) {
		this.shg_fpo_stalls_photo1_time = shg_fpo_stalls_photo1_time;
	}
	public String getShg_fpo_stalls_photo2_lat() {
		return shg_fpo_stalls_photo2_lat;
	}
	public void setShg_fpo_stalls_photo2_lat(String shg_fpo_stalls_photo2_lat) {
		this.shg_fpo_stalls_photo2_lat = shg_fpo_stalls_photo2_lat;
	}
	public String getShg_fpo_stalls_photo2_lng() {
		return shg_fpo_stalls_photo2_lng;
	}
	public void setShg_fpo_stalls_photo2_lng(String shg_fpo_stalls_photo2_lng) {
		this.shg_fpo_stalls_photo2_lng = shg_fpo_stalls_photo2_lng;
	}
	public String getShg_fpo_stalls_photo2_time() {
		return shg_fpo_stalls_photo2_time;
	}
	public void setShg_fpo_stalls_photo2_time(String shg_fpo_stalls_photo2_time) {
		this.shg_fpo_stalls_photo2_time = shg_fpo_stalls_photo2_time;
	}
	public String getLakhpati_didi_photo1_lat() {
		return lakhpati_didi_photo1_lat;
	}
	public void setLakhpati_didi_photo1_lat(String lakhpati_didi_photo1_lat) {
		this.lakhpati_didi_photo1_lat = lakhpati_didi_photo1_lat;
	}
	public String getLakhpati_didi_photo1_lng() {
		return lakhpati_didi_photo1_lng;
	}
	public void setLakhpati_didi_photo1_lng(String lakhpati_didi_photo1_lng) {
		this.lakhpati_didi_photo1_lng = lakhpati_didi_photo1_lng;
	}
	public String getLakhpati_didi_photo1_time() {
		return lakhpati_didi_photo1_time;
	}
	public void setLakhpati_didi_photo1_time(String lakhpati_didi_photo1_time) {
		this.lakhpati_didi_photo1_time = lakhpati_didi_photo1_time;
	}
	public String getLakhpati_didi_photo2_lat() {
		return lakhpati_didi_photo2_lat;
	}
	public void setLakhpati_didi_photo2_lat(String lakhpati_didi_photo2_lat) {
		this.lakhpati_didi_photo2_lat = lakhpati_didi_photo2_lat;
	}
	public String getLakhpati_didi_photo2_lng() {
		return lakhpati_didi_photo2_lng;
	}
	public void setLakhpati_didi_photo2_lng(String lakhpati_didi_photo2_lng) {
		this.lakhpati_didi_photo2_lng = lakhpati_didi_photo2_lng;
	}
	public String getLakhpati_didi_photo2_time() {
		return lakhpati_didi_photo2_time;
	}
	public void setLakhpati_didi_photo2_time(String lakhpati_didi_photo2_time) {
		this.lakhpati_didi_photo2_time = lakhpati_didi_photo2_time;
	}
    
	
}
