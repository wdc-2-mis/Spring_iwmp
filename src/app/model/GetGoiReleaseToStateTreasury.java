package app.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pfms_goi_release_to_state_treasury", schema="public")
public class GetGoiReleaseToStateTreasury implements java.io.Serializable{
	
	private int goireleasestatetreasurydataId;
	private String sanctionNumber;
	private String financialyear;
	private int adviceNo;
	private Date adviceDate;
	private int clearanceMemoNo;
	private Date clearanceMemoDate;
	private int schemeCode;
	private String schemeName;
	private IwmpState iwmpStateByStateLgdCode;
	private IwmpState iwmpStateByStCode;
	private BigDecimal sanctionAmount;
	private String transaction_id;
	private Date transactionDate;
	private String apiResponseStatus;
	private IwmpMFinYear iwmpMFinYear;
	//private IwmpMFinYear iwmpFinYearbyFinYrCd;
	
	
	public GetGoiReleaseToStateTreasury() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public GetGoiReleaseToStateTreasury(int goireleasestatetreasurydataId) {
		super();
		this.goireleasestatetreasurydataId = goireleasestatetreasurydataId;
	}


	public GetGoiReleaseToStateTreasury(int goireleasestatetreasurydataId, String sanctionNumber, String financialyear, int adviceNo,
			Date adviceDate, int clearanceMemoNo, Date clearanceMemoDate, int schemeCode, String schemeName,
			IwmpState iwmpStateByStateLgdCode, IwmpState iwmpStateByStCode, BigDecimal sanctionAmount, String transaction_id, Date transactionDate,
			String apiResponseStatus, IwmpMFinYear iwmpFinYear) {
		super();
		this.goireleasestatetreasurydataId = goireleasestatetreasurydataId;
		this.sanctionNumber = sanctionNumber;
		this.financialyear = financialyear;
		this.adviceNo = adviceNo;
		this.adviceDate = adviceDate;
		this.clearanceMemoNo = clearanceMemoNo;
		this.clearanceMemoDate = clearanceMemoDate;
		this.schemeCode = schemeCode;
		this.schemeName = schemeName;
		this.iwmpStateByStateLgdCode = iwmpStateByStateLgdCode;
		this.iwmpStateByStCode = iwmpStateByStCode;
		this.sanctionAmount = sanctionAmount;
		this.transaction_id = transaction_id;
		this.transactionDate = transactionDate;
		this.apiResponseStatus = apiResponseStatus;
		this.iwmpMFinYear = iwmpFinYear;
	//	this.iwmpFinYearbyFinYrCd = iwmpFinYearbyFinYrCd;
	}
	
	 @Id 

	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name="goireleasestatetreasurydata_id", unique=true, nullable=false)
	public int getGoireleasestatetreasurydataId() {
		return goireleasestatetreasurydataId;
	}
	public void setGoireleasestatetreasurydataId(int goireleasestatetreasurydataId) {
		this.goireleasestatetreasurydataId = goireleasestatetreasurydataId;
	}
	
	@Column(name="sanctionnumber")
	public String getSanctionNumber() {
		return sanctionNumber;
	}
	public void setSanctionNumber(String sanctionNumber) {
		this.sanctionNumber = sanctionNumber;
	}
	
	public String getFinancialyear() {
		return financialyear;
	}

	public void setFinancialyear(String financialyear) {
		this.financialyear = financialyear;
	}


	@Column(name="adviceno")
	public int getAdviceNo() {
		return adviceNo;
	}
	public void setAdviceNo(int adviceNo) {
		this.adviceNo = adviceNo;
	}
	
	@Column(name="advicedate")
	public Date getAdviceDate() {
		return adviceDate;
	}
	public void setAdviceDate(Date adviceDate) {
		this.adviceDate = adviceDate;
	}
	
	@Column(name="clearancememono")
	public int getClearanceMemoNo() {
		return clearanceMemoNo;
	}
	public void setClearanceMemoNo(int clearanceMemoNo) {
		this.clearanceMemoNo = clearanceMemoNo;
	}
	
	@Column(name="clearancememodate")
	public Date getClearanceMemoDate() {
		return clearanceMemoDate;
	}
	public void setClearanceMemoDate(Date clearanceMemoDate) {
		this.clearanceMemoDate = clearanceMemoDate;
	}
	
	@Column(name="schemecode")
	public int getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(int schemeCode) {
		this.schemeCode = schemeCode;
	}
	
	@Column(name="schemename")
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="statelgdcode")
    public IwmpState getIwmpStateByStateLgdCode() {
        return this.iwmpStateByStateLgdCode;
    }
    
    public void setIwmpStateByStateLgdCode(IwmpState iwmpStateByStateLgdCode) {
        this.iwmpStateByStateLgdCode = iwmpStateByStateLgdCode;
    }
	
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="st_code")
	public IwmpState getIwmpStateByStCode() {
		return iwmpStateByStCode;
	}
	public void setIwmpStateByStCode(IwmpState iwmpStateByStCode) {
		this.iwmpStateByStCode = iwmpStateByStCode;
	}
	
	
	@Column(name="sanctionamount")
	public BigDecimal getSanctionAmount() {
		return sanctionAmount;
	}
	public void setSanctionAmount(BigDecimal sanctionAmount) {
		this.sanctionAmount = sanctionAmount;
	}
	
	@Column(name="transaction_id")
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	
	@Column(name="transactiondate")
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	@Column(name="apiresponsestatus")
	public String getApiResponseStatus() {
		return apiResponseStatus;
	}
	public void setApiResponseStatus(String apiResponseStatus) {
		this.apiResponseStatus = apiResponseStatus;
	}
	
	

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fin_yr_cd")
    public IwmpMFinYear getIwmpMFinYear() {
        return this.iwmpMFinYear;
    }
    
    public void setIwmpMFinYear(IwmpMFinYear iwmpMFinYear) {
        this.iwmpMFinYear = iwmpMFinYear;
    }

//	@ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="fin_yr_cd")
//	public IwmpMFinYear getIwmpFinYear() {
//		return iwmpFinYear;
//	}
//	public void setIwmpFinYear(IwmpMFinYear iwmpFinYear) {
//		this.iwmpFinYear = iwmpFinYear;
//	}

}
