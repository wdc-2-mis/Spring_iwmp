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
    private MultipartFile flagoff_photo2;
    private String themesong;
    private MultipartFile themesong_photo1;
    private MultipartFile themesong_photo2;
    private Integer no_works_bhoomipoojan;
    private Integer tot_works_bhoomipoojan;
    private MultipartFile bhoomipoojan_photo1;
    private MultipartFile bhoomipoojan_photo2;
    private Integer no_works_lokarpan;
    private Integer tot_works_lokarpan;
    private MultipartFile lokarpan_photo1;
    private MultipartFile lokarpan_photo2;
    private Integer no_location_shramdaan;
    private Integer no_people_shramdaan;
    private Integer man;
    private MultipartFile shramdaan_photo1;
    private MultipartFile shramdaan_photo2;
    private BigDecimal area_plantation;
    private Integer no_plantation;
    private MultipartFile plantation_photo1;
    private MultipartFile plantation_photo2;
    private Integer no_awards;
    private MultipartFile award_photo1;
    private MultipartFile award_photo2;

    private Integer inauguaration_id;
    private Integer st_code;
    private String stname;
    private String distname;
    private String blockname;
    
    
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
	public Integer getTot_works_bhoomipoojan() {
		return tot_works_bhoomipoojan;
	}
	public void setTot_works_bhoomipoojan(Integer tot_works_bhoomipoojan) {
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
	public Integer getTot_works_lokarpan() {
		return tot_works_lokarpan;
	}
	public void setTot_works_lokarpan(Integer tot_works_lokarpan) {
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
    
	
}
