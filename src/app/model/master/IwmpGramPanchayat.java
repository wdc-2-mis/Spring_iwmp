package app.model.master;
// Generated 12 Mar, 2021 11:27:35 AM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaSWCKAccount;
import app.watershedyatra.model.PreYatraPreparation;
import app.watershedyatra.model.RoutePlanVanTravel;
import app.watershedyatra.model.WatershedYatVill;

/**
 * IwmpGramPanchayat generated by hbm2java
 */
@Entity
@Table(name = "iwmp_gram_panchayat", schema = "public")
public class IwmpGramPanchayat implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int gcode;
	private IwmpBlock iwmpBlock;
	private int stCode;
	private int distCode;
	private int blockCode;
	private int gramPanchayatLgdCode;
	private String gramPanchayatName;
	private String importType;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private String requestIp;
	private Set<IwmpVillage> iwmpVillages = new HashSet<IwmpVillage>(0);
	private Set<PfmsEatmisdataDetail> pfmsEatmisdataDetails = new HashSet<PfmsEatmisdataDetail>(0);
	private Boolean active;
	private Set<WatershedYatVill> watershedYatVill = new HashSet<WatershedYatVill>(0);
	private Set<RoutePlanVanTravel> routePlanVanTravel = new HashSet<RoutePlanVanTravel>(0);
	private Set<PreYatraPreparation> preYatraPreparation = new HashSet<PreYatraPreparation>(0);
	private Set<JanbhagidariPratiyogitaSWCKAccount> janbhagidariPratiyogitaSWCKAccount = new HashSet<JanbhagidariPratiyogitaSWCKAccount>(0);
	
	
	@Transient
	private boolean updatestatus;

	@Transient
	public boolean getUpdatestatus() {
		return updatestatus;
	}

	public void setUpdatestatus(boolean updatestatus) {
		this.updatestatus = updatestatus;
	}

	public IwmpGramPanchayat() {
	}

	public IwmpGramPanchayat(int gcode, int stCode, int distCode, int blockCode, int gramPanchayatLgdCode) {
		this.gcode = gcode;
		this.stCode = stCode;
		this.distCode = distCode;
		this.blockCode = blockCode;
		this.gramPanchayatLgdCode = gramPanchayatLgdCode;
	}

	public IwmpGramPanchayat(int gcode, IwmpBlock iwmpBlock, int stCode, int distCode, int blockCode,
			int gramPanchayatLgdCode, String gramPanchayatName, String importType, String lastUpdatedBy,
			Date lastUpdatedDate, String requestIp, Set<IwmpVillage> iwmpVillages, Set<PfmsEatmisdataDetail> pfmsEatmisdataDetails,
			Set<WatershedYatVill> watershedYatVill, Set<RoutePlanVanTravel> routePlanVanTravel, Set<PreYatraPreparation> preYatraPreparation) {
		this.gcode = gcode;
		this.iwmpBlock = iwmpBlock;
		this.stCode = stCode;
		this.distCode = distCode;
		this.blockCode = blockCode;
		this.gramPanchayatLgdCode = gramPanchayatLgdCode;
		this.gramPanchayatName = gramPanchayatName;
		this.importType = importType;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
		this.requestIp = requestIp;
		this.iwmpVillages = iwmpVillages;
		this.pfmsEatmisdataDetails = pfmsEatmisdataDetails;
		this.watershedYatVill=watershedYatVill;
		this.routePlanVanTravel=routePlanVanTravel;
		this.preYatraPreparation=preYatraPreparation;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name = "gcode", unique = true, nullable = false)
	public int getGcode() {
		return this.gcode;
	}

	public void setGcode(int gcode) {
		this.gcode = gcode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bcode")
	public IwmpBlock getIwmpBlock() {
		return this.iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}

	@Column(name = "st_code", nullable = false)
	public int getStCode() {
		return this.stCode;
	}

	public void setStCode(int stCode) {
		this.stCode = stCode;
	}

	@Column(name = "dist_code", nullable = false)
	public int getDistCode() {
		return this.distCode;
	}

	public void setDistCode(int distCode) {
		this.distCode = distCode;
	}

	@Column(name = "block_code", nullable = false)
	public int getBlockCode() {
		return this.blockCode;
	}

	public void setBlockCode(int blockCode) {
		this.blockCode = blockCode;
	}

	@Column(name = "gram_panchayat_lgd_code", nullable = false)
	public int getGramPanchayatLgdCode() {
		return this.gramPanchayatLgdCode;
	}

	public void setGramPanchayatLgdCode(int gramPanchayatLgdCode) {
		this.gramPanchayatLgdCode = gramPanchayatLgdCode;
	}

	@Column(name = "gram_panchayat_name", length = 100)
	public String getGramPanchayatName() {
		return this.gramPanchayatName;
	}

	public void setGramPanchayatName(String gramPanchayatName) {
		this.gramPanchayatName = gramPanchayatName;
	}

	@Column(name = "import_type", length = 4)
	public String getImportType() {
		return this.importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	@Column(name = "last_updated_by", length = 20)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "last_updated_date", length = 13)
	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Column(name = "request_ip", length = 20)
	public String getRequestIp() {
		return this.requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "iwmpGramPanchayat")
	public Set<IwmpVillage> getIwmpVillages() {
		return this.iwmpVillages;
	}

	public void setIwmpVillages(Set<IwmpVillage> iwmpVillages) {
		this.iwmpVillages = iwmpVillages;
	}

	@Column(name = "active")
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpGramPanchayat")
    public Set<PfmsEatmisdataDetail> getPfmsEatmisdataDetails() {
        return this.pfmsEatmisdataDetails;
    }
    
    public void setPfmsEatmisdataDetails(Set<PfmsEatmisdataDetail> pfmsEatmisdataDetails) {
        this.pfmsEatmisdataDetails = pfmsEatmisdataDetails;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpGramPanchayat")
	public Set<WatershedYatVill> getWatershedYatVill() {
		return watershedYatVill;
	}

	public void setWatershedYatVill(Set<WatershedYatVill> watershedYatVill) {
		this.watershedYatVill = watershedYatVill;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpGramPanchayat")
	public Set<RoutePlanVanTravel> getRoutePlanVanTravel() {
		return routePlanVanTravel;
	}

	public void setRoutePlanVanTravel(Set<RoutePlanVanTravel> routePlanVanTravel) {
		this.routePlanVanTravel = routePlanVanTravel;
	}
	@OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpGramPanchayat")
	public Set<PreYatraPreparation> getPreYatraPreparation() {
		return preYatraPreparation;
	}

	public void setPreYatraPreparation(Set<PreYatraPreparation> preYatraPreparation) {
		this.preYatraPreparation = preYatraPreparation;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpGramPanchayat")
	public Set<JanbhagidariPratiyogitaSWCKAccount> getJanbhagidariPratiyogitaSWCKAccount() {
		return janbhagidariPratiyogitaSWCKAccount;
	}

	public void setJanbhagidariPratiyogitaSWCKAccount(
			Set<JanbhagidariPratiyogitaSWCKAccount> janbhagidariPratiyogitaSWCKAccount) {
		this.janbhagidariPratiyogitaSWCKAccount = janbhagidariPratiyogitaSWCKAccount;
	}
    
    
    
    
    
    
    
}
