package app.watershedyatra.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;

@Entity
@Table(name = "route_plan_van_travel", schema="public")
public class RoutePlanVanTravel {
	
	
    private Integer routePlanId;
    private IwmpState iwmpState;
	private IwmpDistrict iwmpDistrict;
	private IwmpBlock iwmpBlock;
    private IwmpGramPanchayat iwmpGramPanchayat;
    private IwmpVillage iwmpVillage;
    private String location1;
    private Timestamp date1;
    private String location2;
    private Timestamp date2;
    private String status;
    private Date createdDate;
    private String createdBy;
    private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    
    

    public RoutePlanVanTravel() {}
	
	public RoutePlanVanTravel(Integer routePlanId) {
		this.routePlanId=routePlanId;
	}
	
	public RoutePlanVanTravel(Integer routePlanId, IwmpState iwmpState,IwmpDistrict iwmpDistrict,IwmpBlock iwmpBlock, IwmpGramPanchayat iwmpGramPanchayat,Timestamp date1, Timestamp date2,
			IwmpVillage iwmpVillage, String location1, String location2, String status, Date createdDate,String createdBy,String requestedIp,String updatedBy,Date updatedDate) {
		
		this.routePlanId=routePlanId;
		this.iwmpState=iwmpState;
		this.iwmpDistrict=iwmpDistrict;
		this.iwmpBlock=iwmpBlock;
		this.iwmpGramPanchayat=iwmpGramPanchayat;
		this.iwmpVillage=iwmpVillage;
		this.location1=location1;
		this.location2=location2;
		this.status=status;
		this.createdDate=createdDate;
		this.createdBy=createdBy;
		this.requestedIp=requestedIp;
		this.updatedBy=updatedBy;
		this.updatedDate=updatedDate;
		this.date1=date1;
		this.date2=date2;
		
	}

	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="route_plan_id", unique=true, nullable=false)
	public Integer getRoutePlanId() {
		return routePlanId;
	}

	public void setRoutePlanId(Integer routePlanId) {
		this.routePlanId = routePlanId;
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

	@Column(name="location1", length=200)
	public String getLocation1() {
		return location1;
	}

	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	@Column(name="location2", length=200)
	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	@Column(name="status", length=1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Temporal(TemporalType.DATE)
    @Column(name="created_date", length=13)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="created_by", length=25)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="requested_ip", length=25)
	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	@Column(name="updated_by", length=25)
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
	
	@Column(name="date1")
	public Timestamp getDate1() {
		return date1;
	}

	public void setDate1(Timestamp date1) {
		this.date1 = date1;
	}
	
	@Column(name="date2")
	public Timestamp getDate2() {
		return date2;
	}

	public void setDate2(Timestamp date2) {
		this.date2 = date2;
	}
	
	
	
	
	
	
	
	
	
	
}

