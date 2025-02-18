package app.watershedyatra.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;

@Entity
@Table(name = "watershed_yatra_inauguaration", schema = "public")
public class WatershedYatraInauguaration {
	
	
    private Integer inauguarationId;
    private Date inauguarationDate;
    private String inauguarationLocation;
    private Integer maleParticipants;
    private Integer femaleParticipants;
    private Integer centralMinister;
    private Integer stateMinister;
    private Integer parliamentMembers;
    private Integer legislativeAssemblyMembers;
    private Integer legislativeCouncilMembers;
    private Integer otherPublicRepresentatives;
    private Integer govOfficials;
    private Boolean gramSabha;
    private Boolean prabhatPheri;
    private Boolean vanFlagOff;
    private String vanFlagPath1;
    private String vanFlagPath2;
    private Boolean themeSong;
    private String themeSongPath1;
    private String themeSongPath2;
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
    private Integer manHr;
    private String shramdaanPath1;
    private String shramdaanPath2;
    private BigDecimal plantationArea;
    private Integer noOfAgroForsetry;
    private String plantationPath1;
    private String plantationPath2;
    private Integer awardDistribution;
    private String awardDistributionPath1;
    private String awardDistributionPath2;
    private Integer noDeptStalls;
    private String deptStallsPath1;
    private String deptStallsPath2;
    private Integer noShgFpo;
    private String shgFpoPath1;
    private String shgFpoPath2;
    private Integer noLakhpatiDidi;
    private String lakhpatiDidiPath1;
    private String lakhpatiDidiPath2;
    private String status;
    private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    private IwmpState iwmpState;
    private IwmpDistrict iwmpDistrict;
    private IwmpBlock iwmpBlock;
    
    private String van_flag_path1_longitute;
    private String van_flag_path1_latitude;
    private Timestamp van_flag_path1_time;
    private String van_flag_path2_longitute;
    private String van_flag_path2_latitude;
    private Timestamp van_flag_path2_time;
    private String theme_song_path1_longitute;
    private String theme_song_path1_latitude;
    private Timestamp theme_song_path1_time;
    private String theme_song_path2_longitute;
    private String theme_song_path2_latitude;
    private Timestamp theme_song_path2_time;
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
    private String dept_stalls_path1_longitute;
    private String dept_stalls_path1_latitude;
    private Timestamp dept_stalls_path1_time;
    private String dept_stalls_path2_longitute;
    private String dept_stalls_path2_latitude;
    private Timestamp dept_stalls_path2_time;
    private String shg_fpo_path1_longitute;
    private String shg_fpo_path1_latitude;
    private Timestamp shg_fpo_path1_time;
    private String shg_fpo_path2_longitute;
    private String shg_fpo_path2_latitude;
    private Timestamp shg_fpo_path2_time;
    private String lakhpati_didi_path1_longitute;
    private String lakhpati_didi_path1_latitude;
    private Timestamp lakhpati_didi_path1_time;
    private String lakhpati_didi_path2_longitute;
    private String lakhpati_didi_path2_latitude;
    private Timestamp lakhpati_didi_path2_time;
    private String remarks;
    
    public WatershedYatraInauguaration() { }
   
    public WatershedYatraInauguaration(Integer inauguarationId) {
    	this.inauguarationId = inauguarationId;
    }
    
	public WatershedYatraInauguaration(Integer inauguarationId,
			Date inauguarationDate, String inauguarationLocation, Integer maleParticipants,
			Integer femaleParticipants, Integer centralMinister, Integer stateMinister, Integer parliamentMembers,
			Integer legislativeAssemblyMembers, Integer legislativeCouncilMembers, Integer otherPublicRepresentatives,
			Integer govOfficials, Boolean gramSabha, Boolean prabhatPheri, Boolean vanFlagOff, String vanFlagPath1, String vanFlagPath2, Boolean themeSong,
			String themeSongPath1, String themeSongPath2, Integer bhoomiPoojanNoOfWorks,
			BigDecimal bhoomiPoojanCostOfWorks, String bhoomiPoojanPath1, String bhoomiPoojanPath2,
			Integer lokarpanNoOfWorks, BigDecimal lokarpanCostOfWorks, String lokarpanPath1, String lokarpanPath2,
			Integer shramdaanNoOfLocation, Integer shramdaanNoOfParticipatedPeople, Integer manHr, String shramdaanPath1,
			String shramdaanPath2, BigDecimal plantationArea, Integer noOfAgroForsetry, String plantationPath1,
			String plantationPath2, Integer awardDistribution, String awardDistributionPath1,
			String awardDistributionPath2, Integer noDeptStalls, String deptStallsPath1, String deptStallsPath2,
		    Integer noShgFpo, String shgFpoPath1, String shgFpoPath2, Integer noLakhpatiDidi, String lakhpatiDidiPath1,
		    String lakhpatiDidiPath2, String status, String requestedIp, String updatedBy, Date updatedDate,
			String createdBy, Date createdDate, IwmpState iwmpState, IwmpDistrict iwmpDistrict,
			IwmpBlock iwmpBlock, String remarks) {
		this.inauguarationId = inauguarationId;
		this.inauguarationDate = inauguarationDate;
		this.inauguarationLocation = inauguarationLocation;
		this.maleParticipants = maleParticipants;
		this.femaleParticipants = femaleParticipants;
		this.centralMinister = centralMinister;
		this.stateMinister = stateMinister;
		this.parliamentMembers = parliamentMembers;
		this.legislativeAssemblyMembers = legislativeAssemblyMembers;
		this.legislativeCouncilMembers = legislativeCouncilMembers;
		this.otherPublicRepresentatives = otherPublicRepresentatives;
		this.govOfficials = govOfficials;
		this.gramSabha = gramSabha;
		this.prabhatPheri = prabhatPheri;
		this.vanFlagOff = vanFlagOff;
		this.vanFlagPath1 = vanFlagPath1;
		this.vanFlagPath2 = vanFlagPath2;
		this.themeSong = themeSong;
		this.themeSongPath1 = themeSongPath1;
		this.themeSongPath2 = themeSongPath2;
		this.bhoomiPoojanNoOfWorks = bhoomiPoojanNoOfWorks;
		this.bhoomiPoojanCostOfWorks = bhoomiPoojanCostOfWorks;
		this.bhoomiPoojanPath1 = bhoomiPoojanPath1;
		this.bhoomiPoojanPath2 = bhoomiPoojanPath2;
		this.lokarpanNoOfWorks = lokarpanNoOfWorks;
		this.lokarpanCostOfWorks = lokarpanCostOfWorks;
		this.lokarpanPath1 = lokarpanPath1;
		this.lokarpanPath2 = lokarpanPath2;
		this.shramdaanNoOfLocation = shramdaanNoOfLocation;
		this.shramdaanNoOfParticipatedPeople = shramdaanNoOfParticipatedPeople;
		this.manHr = manHr;
		this.shramdaanPath1 = shramdaanPath1;
		this.shramdaanPath2 = shramdaanPath2;
		this.plantationArea = plantationArea;
		this.noOfAgroForsetry = noOfAgroForsetry;
		this.plantationPath1 = plantationPath1;
		this.plantationPath2 = plantationPath2;
		this.awardDistribution = awardDistribution;
		this.awardDistributionPath1 = awardDistributionPath1;
		this.awardDistributionPath2 = awardDistributionPath2;
		this.noDeptStalls = noDeptStalls;
		this.deptStallsPath1 = deptStallsPath1;
		this.deptStallsPath2 = deptStallsPath2;
		this.noShgFpo = noShgFpo;
		this.shgFpoPath1 = shgFpoPath1;
		this.shgFpoPath2 = shgFpoPath2;
		this.noLakhpatiDidi = noLakhpatiDidi;
		this.lakhpatiDidiPath1 = lakhpatiDidiPath1;
		this.lakhpatiDidiPath2 = lakhpatiDidiPath2;
		this.status = status;
		this.requestedIp = requestedIp;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.iwmpState = iwmpState;
		this.iwmpDistrict = iwmpDistrict;
		this.iwmpBlock = iwmpBlock;
		this.remarks=remarks;
	}
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="inauguaration_id", unique=true, nullable=false)
	public Integer getInauguarationId() {
		return inauguarationId;
	}

	public void setInauguarationId(Integer inauguarationId) {
		this.inauguarationId = inauguarationId;
	}

    @Column(name = "inauguaration_date")
	public Date getInauguarationDate() {
		return inauguarationDate;
	}

	public void setInauguarationDate(Date inauguarationDate) {
		this.inauguarationDate = inauguarationDate;
	}

	@Column(name = "inauguaration_location")
	public String getInauguarationLocation() {
		return inauguarationLocation;
	}

	public void setInauguarationLocation(String inauguarationLocation) {
		this.inauguarationLocation = inauguarationLocation;
	}

	@Column(name = "male_participants")
	public Integer getMaleParticipants() {
		return maleParticipants;
	}

	public void setMaleParticipants(Integer maleParticipants) {
		this.maleParticipants = maleParticipants;
	}

	@Column(name = "female_participants")
	public Integer getFemaleParticipants() {
		return femaleParticipants;
	}

	public void setFemaleParticipants(Integer femaleParticipants) {
		this.femaleParticipants = femaleParticipants;
	}

	@Column(name = "central_minister")
	public Integer getCentralMinister() {
		return centralMinister;
	}

	public void setCentralMinister(Integer centralMinister) {
		this.centralMinister = centralMinister;
	}

	@Column(name = "state_minister")
	public Integer getStateMinister() {
		return stateMinister;
	}

	public void setStateMinister(Integer stateMinister) {
		this.stateMinister = stateMinister;
	}

	@Column(name = "parliament_members")
	public Integer getParliamentMembers() {
		return parliamentMembers;
	}

	public void setParliamentMembers(Integer parliamentMembers) {
		this.parliamentMembers = parliamentMembers;
	}

	@Column(name = "legislative_assembly_members")
	public Integer getLegislativeAssemblyMembers() {
		return legislativeAssemblyMembers;
	}

	public void setLegislativeAssemblyMembers(Integer legislativeAssemblyMembers) {
		this.legislativeAssemblyMembers = legislativeAssemblyMembers;
	}

	@Column(name = "legislative_council_members")
	public Integer getLegislativeCouncilMembers() {
		return legislativeCouncilMembers;
	}

	public void setLegislativeCouncilMembers(Integer legislativeCouncilMembers) {
		this.legislativeCouncilMembers = legislativeCouncilMembers;
	}

	@Column(name = "other_public_representatives")
	public Integer getOtherPublicRepresentatives() {
		return otherPublicRepresentatives;
	}

	public void setOtherPublicRepresentatives(Integer otherPublicRepresentatives) {
		this.otherPublicRepresentatives = otherPublicRepresentatives;
	}

	@Column(name = "gov_officials")
	public Integer getGovOfficials() {
		return govOfficials;
	}

	public void setGovOfficials(Integer govOfficials) {
		this.govOfficials = govOfficials;
	}
	
	@Column(name = "gram_sabha")
	public Boolean getGramSabha() {
		return gramSabha;
	}

	public void setGramSabha(Boolean gramSabha) {
		this.gramSabha = gramSabha;
	}

	@Column(name = "prabhat_pheri")
	public Boolean getPrabhatPheri() {
		return prabhatPheri;
	}

	public void setPrabhatPheri(Boolean prabhatPheri) {
		this.prabhatPheri = prabhatPheri;
	}

	@Column(name = "van_flag_off")
	public Boolean getVanFlagOff() {
		return vanFlagOff;
	}

	public void setVanFlagOff(Boolean vanFlagOff) {
		this.vanFlagOff = vanFlagOff;
	}

	@Column(name = "van_flag_path1")
	public String getVanFlagPath1() {
		return vanFlagPath1;
	}

	public void setVanFlagPath1(String vanFlagPath1) {
		this.vanFlagPath1 = vanFlagPath1;
	}

	 @Column(name = "van_flag_path2")
	public String getVanFlagPath2() {
		return vanFlagPath2;
	}

	public void setVanFlagPath2(String vanFlagPath2) {
		this.vanFlagPath2 = vanFlagPath2;
	}

	@Column(name = "theme_song")
	public Boolean getThemeSong() {
		return themeSong;
	}

	public void setThemeSong(Boolean themeSong) {
		this.themeSong = themeSong;
	}

	@Column(name = "theme_song_path1")
	public String getThemeSongPath1() {
		return themeSongPath1;
	}

	public void setThemeSongPath1(String themeSongPath1) {
		this.themeSongPath1 = themeSongPath1;
	}

	@Column(name = "theme_song_path2")
	public String getThemeSongPath2() {
		return themeSongPath2;
	}

	public void setThemeSongPath2(String themeSongPath2) {
		this.themeSongPath2 = themeSongPath2;
	}

	@Column(name = "bhoomi_poojan_no_of_works")
	public Integer getBhoomiPoojanNoOfWorks() {
		return bhoomiPoojanNoOfWorks;
	}

	public void setBhoomiPoojanNoOfWorks(Integer bhoomiPoojanNoOfWorks) {
		this.bhoomiPoojanNoOfWorks = bhoomiPoojanNoOfWorks;
	}

	@Column(name = "bhoomi_poojan_cost_of_works")
	public BigDecimal getBhoomiPoojanCostOfWorks() {
		return bhoomiPoojanCostOfWorks;
	}

	public void setBhoomiPoojanCostOfWorks(BigDecimal bhoomiPoojanCostOfWorks) {
		this.bhoomiPoojanCostOfWorks = bhoomiPoojanCostOfWorks;
	}

	@Column(name = "bhoomi_poojan_path1")
	public String getBhoomiPoojanPath1() {
		return bhoomiPoojanPath1;
	}

	public void setBhoomiPoojanPath1(String bhoomiPoojanPath1) {
		this.bhoomiPoojanPath1 = bhoomiPoojanPath1;
	}

	@Column(name = "bhoomi_poojan_path2")
	public String getBhoomiPoojanPath2() {
		return bhoomiPoojanPath2;
	}

	public void setBhoomiPoojanPath2(String bhoomiPoojanPath2) {
		this.bhoomiPoojanPath2 = bhoomiPoojanPath2;
	}

	@Column(name = "lokarpan_no_of_works")
	public Integer getLokarpanNoOfWorks() {
		return lokarpanNoOfWorks;
	}

	public void setLokarpanNoOfWorks(Integer lokarpanNoOfWorks) {
		this.lokarpanNoOfWorks = lokarpanNoOfWorks;
	}

	@Column(name = "lokarpan_cost_of_works")
	public BigDecimal getLokarpanCostOfWorks() {
		return lokarpanCostOfWorks;
	}

	public void setLokarpanCostOfWorks(BigDecimal lokarpanCostOfWorks) {
		this.lokarpanCostOfWorks = lokarpanCostOfWorks;
	}

	@Column(name = "lokarpan_path1")
	public String getLokarpanPath1() {
		return lokarpanPath1;
	}

	public void setLokarpanPath1(String lokarpanPath1) {
		this.lokarpanPath1 = lokarpanPath1;
	}

	@Column(name = "lokarpan_path2")
	public String getLokarpanPath2() {
		return lokarpanPath2;
	}

	public void setLokarpanPath2(String lokarpanPath2) {
		this.lokarpanPath2 = lokarpanPath2;
	}

	@Column(name = "shramdaan_no_of_location")
	public Integer getShramdaanNoOfLocation() {
		return shramdaanNoOfLocation;
	}

	public void setShramdaanNoOfLocation(Integer shramdaanNoOfLocation) {
		this.shramdaanNoOfLocation = shramdaanNoOfLocation;
	}

	@Column(name = "shramdaan_no_of_participated_people")
	public Integer getShramdaanNoOfParticipatedPeople() {
		return shramdaanNoOfParticipatedPeople;
	}

	public void setShramdaanNoOfParticipatedPeople(Integer shramdaanNoOfParticipatedPeople) {
		this.shramdaanNoOfParticipatedPeople = shramdaanNoOfParticipatedPeople;
	}
	
	@Column(name = "man_hr")
	public Integer getManHr() {
		return manHr;
	}

	public void setManHr(Integer manHr) {
		this.manHr = manHr;
	}

	@Column(name = "shramdaan_path1")
	public String getShramdaanPath1() {
		return shramdaanPath1;
	}

	public void setShramdaanPath1(String shramdaanPath1) {
		this.shramdaanPath1 = shramdaanPath1;
	}

	@Column(name = "shramdaan_path2")
	public String getShramdaanPath2() {
		return shramdaanPath2;
	}

	public void setShramdaanPath2(String shramdaanPath2) {
		this.shramdaanPath2 = shramdaanPath2;
	}

	@Column(name = "plantation_area")
	public BigDecimal getPlantationArea() {
		return plantationArea;
	}

	public void setPlantationArea(BigDecimal plantationArea) {
		this.plantationArea = plantationArea;
	}

	@Column(name = "no_of_agro_forsetry")
	public Integer getNoOfAgroForsetry() {
		return noOfAgroForsetry;
	}

	public void setNoOfAgroForsetry(Integer noOfAgroForsetry) {
		this.noOfAgroForsetry = noOfAgroForsetry;
	}

	@Column(name = "plantation_path1")
	public String getPlantationPath1() {
		return plantationPath1;
	}

	public void setPlantationPath1(String plantationPath1) {
		this.plantationPath1 = plantationPath1;
	}

	@Column(name = "plantation_path2")
	public String getPlantationPath2() {
		return plantationPath2;
	}

	public void setPlantationPath2(String plantationPath2) {
		this.plantationPath2 = plantationPath2;
	}

	@Column(name = "award_distribution")
	public Integer getAwardDistribution() {
		return awardDistribution;
	}

	public void setAwardDistribution(Integer awardDistribution) {
		this.awardDistribution = awardDistribution;
	}

	@Column(name = "award_distribution_path1")
	public String getAwardDistributionPath1() {
		return awardDistributionPath1;
	}

	public void setAwardDistributionPath1(String awardDistributionPath1) {
		this.awardDistributionPath1 = awardDistributionPath1;
	}

	@Column(name = "award_distribution_path2")
	public String getAwardDistributionPath2() {
		return awardDistributionPath2;
	}

	public void setAwardDistributionPath2(String awardDistributionPath2) {
		this.awardDistributionPath2 = awardDistributionPath2;
	}
	
	@Column(name = "no_dept_stalls")
	public Integer getNoDeptStalls() {
		return noDeptStalls;
	}

	public void setNoDeptStalls(Integer noDeptStalls) {
		this.noDeptStalls = noDeptStalls;
	}

	@Column(name = "dept_stalls_path1")
	public String getDeptStallsPath1() {
		return deptStallsPath1;
	}

	public void setDeptStallsPath1(String deptStallsPath1) {
		this.deptStallsPath1 = deptStallsPath1;
	}

	@Column(name = "dept_stalls_path2")
	public String getDeptStallsPath2() {
		return deptStallsPath2;
	}

	public void setDeptStallsPath2(String deptStallsPath2) {
		this.deptStallsPath2 = deptStallsPath2;
	}

	@Column(name = "no_shg_fpo")
	public Integer getNoShgFpo() {
		return noShgFpo;
	}

	public void setNoShgFpo(Integer noShgFpo) {
		this.noShgFpo = noShgFpo;
	}

	@Column(name = "shg_fpo_path1")
	public String getShgFpoPath1() {
		return shgFpoPath1;
	}

	public void setShgFpoPath1(String shgFpoPath1) {
		this.shgFpoPath1 = shgFpoPath1;
	}

	@Column(name = "shg_fpo_path2")
	public String getShgFpoPath2() {
		return shgFpoPath2;
	}

	public void setShgFpoPath2(String shgFpoPath2) {
		this.shgFpoPath2 = shgFpoPath2;
	}

	@Column(name = "no_lakhpati_didi")
	public Integer getNoLakhpatiDidi() {
		return noLakhpatiDidi;
	}

	public void setNoLakhpatiDidi(Integer noLakhpatiDidi) {
		this.noLakhpatiDidi = noLakhpatiDidi;
	}

	@Column(name = "lakhpati_didi_path1")
	public String getLakhpatiDidiPath1() {
		return lakhpatiDidiPath1;
	}

	public void setLakhpatiDidiPath1(String lakhpatiDidiPath1) {
		this.lakhpatiDidiPath1 = lakhpatiDidiPath1;
	}

	@Column(name = "lakhpati_didi_path2")
	public String getLakhpatiDidiPath2() {
		return lakhpatiDidiPath2;
	}

	public void setLakhpatiDidiPath2(String lakhpatiDidiPath2) {
		this.lakhpatiDidiPath2 = lakhpatiDidiPath2;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "requested_ip", length=20)
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	@Column(name = "updated_by", length=25)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="updated_date", length=13)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "created_by", length=25)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="created_date", length=13)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "st_code")
	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dcode")
	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "bcode")
	public IwmpBlock getIwmpBlock() {
		return iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}

	@Column(name ="van_flag_path1_longitute")
	public String getVan_flag_path1_longitute() {
		return van_flag_path1_longitute;
	}

	public void setVan_flag_path1_longitute(String van_flag_path1_longitute) {
		this.van_flag_path1_longitute = van_flag_path1_longitute;
	}

	@Column(name ="van_flag_path1_latitude")
	public String getVan_flag_path1_latitude() {
		return van_flag_path1_latitude;
	}

	public void setVan_flag_path1_latitude(String van_flag_path1_latitude) {
		this.van_flag_path1_latitude = van_flag_path1_latitude;
	}

	@Column(name ="van_flag_path1_time")
	public Timestamp getVan_flag_path1_time() {
		return van_flag_path1_time;
	}

	public void setVan_flag_path1_time(Timestamp van_flag_path1_time) {
		this.van_flag_path1_time = van_flag_path1_time;
	}

	@Column(name ="van_flag_path2_longitute")
	public String getVan_flag_path2_longitute() {
		return van_flag_path2_longitute;
	}

	public void setVan_flag_path2_longitute(String van_flag_path2_longitute) {
		this.van_flag_path2_longitute = van_flag_path2_longitute;
	}

	@Column(name ="van_flag_path2_latitude")
	public String getVan_flag_path2_latitude() {
		return van_flag_path2_latitude;
	}

	public void setVan_flag_path2_latitude(String van_flag_path2_latitude) {
		this.van_flag_path2_latitude = van_flag_path2_latitude;
	}

	@Column(name ="van_flag_path2_time")
	public Timestamp getVan_flag_path2_time() {
		return van_flag_path2_time;
	}

	public void setVan_flag_path2_time(Timestamp van_flag_path2_time) {
		this.van_flag_path2_time = van_flag_path2_time;
	}

	@Column(name ="theme_song_path1_longitute")
	public String getTheme_song_path1_longitute() {
		return theme_song_path1_longitute;
	}

	public void setTheme_song_path1_longitute(String theme_song_path1_longitute) {
		this.theme_song_path1_longitute = theme_song_path1_longitute;
	}

	@Column(name ="theme_song_path1_latitude")
	public String getTheme_song_path1_latitude() {
		return theme_song_path1_latitude;
	}

	public void setTheme_song_path1_latitude(String theme_song_path1_latitude) {
		this.theme_song_path1_latitude = theme_song_path1_latitude;
	}

	@Column(name ="theme_song_path1_time")
	public Timestamp getTheme_song_path1_time() {
		return theme_song_path1_time;
	}

	public void setTheme_song_path1_time(Timestamp theme_song_path1_time) {
		this.theme_song_path1_time = theme_song_path1_time;
	}

	@Column(name ="theme_song_path2_longitute")
	public String getTheme_song_path2_longitute() {
		return theme_song_path2_longitute;
	}

	public void setTheme_song_path2_longitute(String theme_song_path2_longitute) {
		this.theme_song_path2_longitute = theme_song_path2_longitute;
	}

	@Column(name ="theme_song_path2_latitude")
	public String getTheme_song_path2_latitude() {
		return theme_song_path2_latitude;
	}

	public void setTheme_song_path2_latitude(String theme_song_path2_latitude) {
		this.theme_song_path2_latitude = theme_song_path2_latitude;
	}

	@Column(name ="theme_song_path2_time")
	public Timestamp getTheme_song_path2_time() {
		return theme_song_path2_time;
	}

	public void setTheme_song_path2_time(Timestamp theme_song_path2_time) {
		this.theme_song_path2_time = theme_song_path2_time;
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

	@Column(name ="dept_stalls_path1_longitute")
	public String getDept_stalls_path1_longitute() {
		return dept_stalls_path1_longitute;
	}

	public void setDept_stalls_path1_longitute(String dept_stalls_path1_longitute) {
		this.dept_stalls_path1_longitute = dept_stalls_path1_longitute;
	}

	@Column(name ="dept_stalls_path1_latitude")
	public String getDept_stalls_path1_latitude() {
		return dept_stalls_path1_latitude;
	}

	public void setDept_stalls_path1_latitude(String dept_stalls_path1_latitude) {
		this.dept_stalls_path1_latitude = dept_stalls_path1_latitude;
	}

	@Column(name ="dept_stalls_path1_time")
	public Timestamp getDept_stalls_path1_time() {
		return dept_stalls_path1_time;
	}

	public void setDept_stalls_path1_time(Timestamp dept_stalls_path1_time) {
		this.dept_stalls_path1_time = dept_stalls_path1_time;
	}

	@Column(name ="dept_stalls_path2_longitute")
	public String getDept_stalls_path2_longitute() {
		return dept_stalls_path2_longitute;
	}

	public void setDept_stalls_path2_longitute(String dept_stalls_path2_longitute) {
		this.dept_stalls_path2_longitute = dept_stalls_path2_longitute;
	}

	@Column(name ="dept_stalls_path2_latitude")
	public String getDept_stalls_path2_latitude() {
		return dept_stalls_path2_latitude;
	}

	public void setDept_stalls_path2_latitude(String dept_stalls_path2_latitude) {
		this.dept_stalls_path2_latitude = dept_stalls_path2_latitude;
	}

	@Column(name ="dept_stalls_path2_time")
	public Timestamp getDept_stalls_path2_time() {
		return dept_stalls_path2_time;
	}

	public void setDept_stalls_path2_time(Timestamp dept_stalls_path2_time) {
		this.dept_stalls_path2_time = dept_stalls_path2_time;
	}

	@Column(name ="shg_fpo_path1_longitute")
	public String getShg_fpo_path1_longitute() {
		return shg_fpo_path1_longitute;
	}

	public void setShg_fpo_path1_longitute(String shg_fpo_path1_longitute) {
		this.shg_fpo_path1_longitute = shg_fpo_path1_longitute;
	}

	@Column(name ="shg_fpo_path1_latitude")
	public String getShg_fpo_path1_latitude() {
		return shg_fpo_path1_latitude;
	}

	public void setShg_fpo_path1_latitude(String shg_fpo_path1_latitude) {
		this.shg_fpo_path1_latitude = shg_fpo_path1_latitude;
	}

	@Column(name ="shg_fpo_path1_time")
	public Timestamp getShg_fpo_path1_time() {
		return shg_fpo_path1_time;
	}

	public void setShg_fpo_path1_time(Timestamp shg_fpo_path1_time) {
		this.shg_fpo_path1_time = shg_fpo_path1_time;
	}

	@Column(name ="shg_fpo_path2_longitute")
	public String getShg_fpo_path2_longitute() {
		return shg_fpo_path2_longitute;
	}

	public void setShg_fpo_path2_longitute(String shg_fpo_path2_longitute) {
		this.shg_fpo_path2_longitute = shg_fpo_path2_longitute;
	}

	@Column(name ="shg_fpo_path2_latitude")
	public String getShg_fpo_path2_latitude() {
		return shg_fpo_path2_latitude;
	}

	public void setShg_fpo_path2_latitude(String shg_fpo_path2_latitude) {
		this.shg_fpo_path2_latitude = shg_fpo_path2_latitude;
	}

	@Column(name ="shg_fpo_path2_time")
	public Timestamp getShg_fpo_path2_time() {
		return shg_fpo_path2_time;
	}

	public void setShg_fpo_path2_time(Timestamp shg_fpo_path2_time) {
		this.shg_fpo_path2_time = shg_fpo_path2_time;
	}

	@Column(name ="lakhpati_didi_path1_longitute")
	public String getLakhpati_didi_path1_longitute() {
		return lakhpati_didi_path1_longitute;
	}

	public void setLakhpati_didi_path1_longitute(String lakhpati_didi_path1_longitute) {
		this.lakhpati_didi_path1_longitute = lakhpati_didi_path1_longitute;
	}

	@Column(name ="lakhpati_didi_path1_latitude")
	public String getLakhpati_didi_path1_latitude() {
		return lakhpati_didi_path1_latitude;
	}

	public void setLakhpati_didi_path1_latitude(String lakhpati_didi_path1_latitude) {
		this.lakhpati_didi_path1_latitude = lakhpati_didi_path1_latitude;
	}

	@Column(name ="lakhpati_didi_path1_time")
	public Timestamp getLakhpati_didi_path1_time() {
		return lakhpati_didi_path1_time;
	}

	public void setLakhpati_didi_path1_time(Timestamp lakhpati_didi_path1_time) {
		this.lakhpati_didi_path1_time = lakhpati_didi_path1_time;
	}

	@Column(name ="lakhpati_didi_path2_longitute")
	public String getLakhpati_didi_path2_longitute() {
		return lakhpati_didi_path2_longitute;
	}

	public void setLakhpati_didi_path2_longitute(String lakhpati_didi_path2_longitute) {
		this.lakhpati_didi_path2_longitute = lakhpati_didi_path2_longitute;
	}

	@Column(name ="lakhpati_didi_path2_latitude")
	public String getLakhpati_didi_path2_latitude() {
		return lakhpati_didi_path2_latitude;
	}

	public void setLakhpati_didi_path2_latitude(String lakhpati_didi_path2_latitude) {
		this.lakhpati_didi_path2_latitude = lakhpati_didi_path2_latitude;
	}

	@Column(name ="lakhpati_didi_path2_time")
	public Timestamp getLakhpati_didi_path2_time() {
		return lakhpati_didi_path2_time;
	}

	public void setLakhpati_didi_path2_time(Timestamp lakhpati_didi_path2_time) {
		this.lakhpati_didi_path2_time = lakhpati_didi_path2_time;
	}

	@Column(name ="remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	


}
