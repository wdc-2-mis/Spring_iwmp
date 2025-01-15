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
    private Integer maleParticipants;
    private Integer femaleParticipants;
    private Integer centralMinisters;
    private Integer stateMinisters;
    private Integer parliament;
    private Integer assemblyMembers;
    private Integer councilMembers;
    private Integer others;
    private Integer govOfficials;
    private String flagOff;
    private MultipartFile flagOffPhoto1;
    private MultipartFile flagOffPhoto2;
    private String themeSong;
    private MultipartFile themeSongPhoto1;
    private MultipartFile themeSongPhoto2;
    private Integer noWorksBhoomiPoojan;
    private Integer totWorksBhoomiPoojan;
    private MultipartFile bhoomiPoojanPhoto1;
    private MultipartFile bhoomiPoojanPhoto2;
    private Integer noWorksLokarpan;
    private Integer totWorksLokarpan;
    private MultipartFile lokarpanPhoto1;
    private MultipartFile lokarpanPhoto2;
    private Integer noLocationShramdaan;
    private Integer costPeopleShramdaan;
    private MultipartFile shramdaanPhoto1;
    private MultipartFile shramdaanPhoto2;
    private BigDecimal areaPlantation;
    private Integer noPlantation;
    private MultipartFile plantationPhoto1;
    private MultipartFile plantationPhoto2;
    private Integer noAwards;
    private MultipartFile awardPhoto1;
    private MultipartFile awardPhoto2;

    
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
	public Integer getParliament() {
		return parliament;
	}
	public void setParliament(Integer parliament) {
		this.parliament = parliament;
	}
	public Integer getAssemblyMembers() {
		return assemblyMembers;
	}
	public void setAssemblyMembers(Integer assemblyMembers) {
		this.assemblyMembers = assemblyMembers;
	}
	public Integer getCouncilMembers() {
		return councilMembers;
	}
	public void setCouncilMembers(Integer councilMembers) {
		this.councilMembers = councilMembers;
	}
	public Integer getOthers() {
		return others;
	}
	public void setOthers(Integer others) {
		this.others = others;
	}
	public Integer getGovOfficials() {
		return govOfficials;
	}
	public void setGovOfficials(Integer govOfficials) {
		this.govOfficials = govOfficials;
	}
	public String getFlagOff() {
		return flagOff;
	}
	public void setFlagOff(String flagOff) {
		this.flagOff = flagOff;
	}
	public MultipartFile getFlagOffPhoto1() {
		return flagOffPhoto1;
	}
	public void setFlagOffPhoto1(MultipartFile flagOffPhoto1) {
		this.flagOffPhoto1 = flagOffPhoto1;
	}
	public MultipartFile getFlagOffPhoto2() {
		return flagOffPhoto2;
	}
	public void setFlagOffPhoto2(MultipartFile flagOffPhoto2) {
		this.flagOffPhoto2 = flagOffPhoto2;
	}
	public String getThemeSong() {
		return themeSong;
	}
	public void setThemeSong(String themeSong) {
		this.themeSong = themeSong;
	}
	public MultipartFile getThemeSongPhoto1() {
		return themeSongPhoto1;
	}
	public void setThemeSongPhoto1(MultipartFile themeSongPhoto1) {
		this.themeSongPhoto1 = themeSongPhoto1;
	}
	public MultipartFile getThemeSongPhoto2() {
		return themeSongPhoto2;
	}
	public void setThemeSongPhoto2(MultipartFile themeSongPhoto2) {
		this.themeSongPhoto2 = themeSongPhoto2;
	}
	public Integer getNoWorksBhoomiPoojan() {
		return noWorksBhoomiPoojan;
	}
	public void setNoWorksBhoomiPoojan(Integer noWorksBhoomiPoojan) {
		this.noWorksBhoomiPoojan = noWorksBhoomiPoojan;
	}
	public Integer getTotWorksBhoomiPoojan() {
		return totWorksBhoomiPoojan;
	}
	public void setTotWorksBhoomiPoojan(Integer totWorksBhoomiPoojan) {
		this.totWorksBhoomiPoojan = totWorksBhoomiPoojan;
	}
	public MultipartFile getBhoomiPoojanPhoto1() {
		return bhoomiPoojanPhoto1;
	}
	public void setBhoomiPoojanPhoto1(MultipartFile bhoomiPoojanPhoto1) {
		this.bhoomiPoojanPhoto1 = bhoomiPoojanPhoto1;
	}
	public MultipartFile getBhoomiPoojanPhoto2() {
		return bhoomiPoojanPhoto2;
	}
	public void setBhoomiPoojanPhoto2(MultipartFile bhoomiPoojanPhoto2) {
		this.bhoomiPoojanPhoto2 = bhoomiPoojanPhoto2;
	}
	public Integer getNoWorksLokarpan() {
		return noWorksLokarpan;
	}
	public void setNoWorksLokarpan(Integer noWorksLokarpan) {
		this.noWorksLokarpan = noWorksLokarpan;
	}
	public Integer getTotWorksLokarpan() {
		return totWorksLokarpan;
	}
	public void setTotWorksLokarpan(Integer totWorksLokarpan) {
		this.totWorksLokarpan = totWorksLokarpan;
	}
	public MultipartFile getLokarpanPhoto1() {
		return lokarpanPhoto1;
	}
	public void setLokarpanPhoto1(MultipartFile lokarpanPhoto1) {
		this.lokarpanPhoto1 = lokarpanPhoto1;
	}
	public MultipartFile getLokarpanPhoto2() {
		return lokarpanPhoto2;
	}
	public void setLokarpanPhoto2(MultipartFile lokarpanPhoto2) {
		this.lokarpanPhoto2 = lokarpanPhoto2;
	}
	public Integer getNoLocationShramdaan() {
		return noLocationShramdaan;
	}
	public void setNoLocationShramdaan(Integer noLocationShramdaan) {
		this.noLocationShramdaan = noLocationShramdaan;
	}
	public Integer getCostPeopleShramdaan() {
		return costPeopleShramdaan;
	}
	public void setCostPeopleShramdaan(Integer costPeopleShramdaan) {
		this.costPeopleShramdaan = costPeopleShramdaan;
	}
	public MultipartFile getShramdaanPhoto1() {
		return shramdaanPhoto1;
	}
	public void setShramdaanPhoto1(MultipartFile shramdaanPhoto1) {
		this.shramdaanPhoto1 = shramdaanPhoto1;
	}
	public MultipartFile getShramdaanPhoto2() {
		return shramdaanPhoto2;
	}
	public void setShramdaanPhoto2(MultipartFile shramdaanPhoto2) {
		this.shramdaanPhoto2 = shramdaanPhoto2;
	}
	public BigDecimal getAreaPlantation() {
		return areaPlantation;
	}
	public void setAreaPlantation(BigDecimal areaPlantation) {
		this.areaPlantation = areaPlantation;
	}
	public Integer getNoPlantation() {
		return noPlantation;
	}
	public void setNoPlantation(Integer noPlantation) {
		this.noPlantation = noPlantation;
	}
	public MultipartFile getPlantationPhoto1() {
		return plantationPhoto1;
	}
	public void setPlantationPhoto1(MultipartFile plantationPhoto1) {
		this.plantationPhoto1 = plantationPhoto1;
	}
	public MultipartFile getPlantationPhoto2() {
		return plantationPhoto2;
	}
	public void setPlantationPhoto2(MultipartFile plantationPhoto2) {
		this.plantationPhoto2 = plantationPhoto2;
	}
	public Integer getNoAwards() {
		return noAwards;
	}
	public void setNoAwards(Integer noAwards) {
		this.noAwards = noAwards;
	}
	public MultipartFile getAwardPhoto1() {
		return awardPhoto1;
	}
	public void setAwardPhoto1(MultipartFile awardPhoto1) {
		this.awardPhoto1 = awardPhoto1;
	}
	public MultipartFile getAwardPhoto2() {
		return awardPhoto2;
	}
	public void setAwardPhoto2(MultipartFile awardPhoto2) {
		this.awardPhoto2 = awardPhoto2;
	}
	
}
