package app.watershedyatra.model;

import java.util.Date;
import javax.persistence.*;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;

@Entity
@Table(name = "route_plan_van_travel")
public class RoutePlanVanTravel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routePlanId;

    @Column(name = "st_code")
    private Integer stCode;

    @Column(name = "dcode")
    private Integer dCode;

    @Column(name = "bcode")
    private Integer bCode;

    @Column(name = "gcode")
    private Integer gCode;

    @Column(name = "vcode")
    private Integer vCode;

    @Column(name = "location1")
    private String location1;

    @Column(name = "date1")
    private Date date1;

    @Column(name = "time1")
    private String time1;

    @Column(name = "location2")
    private String location2;

    @Column(name = "date2")
    private Date date2;

    @Column(name = "time2")
    private String time2;

    @Column(name = "status")
    private String status;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "requested_ip")
    private String requestedIp;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "st_code", referencedColumnName = "st_code", insertable = false, updatable = false)
    private IwmpState iwmpState;

    @ManyToOne
    @JoinColumn(name = "dcode", referencedColumnName = "dcode", insertable = false, updatable = false)
    private IwmpDistrict iwmpDistrict;

    @ManyToOne
    @JoinColumn(name = "bcode", referencedColumnName = "bcode", insertable = false, updatable = false)
    private IwmpBlock iwmpBlock;

    @ManyToOne
    @JoinColumn(name = "gcode", referencedColumnName = "gcode", insertable = false, updatable = false)
    private IwmpGramPanchayat iwmpGramPanchayat;

    @ManyToOne
    @JoinColumn(name = "vcode", referencedColumnName = "vcode", insertable = false, updatable = false)
    private IwmpVillage iwmpVillage;
    
    
	public Integer getRoutePlanId() {
		return routePlanId;
	}

	public void setRoutePlanId(Integer routePlanId) {
		this.routePlanId = routePlanId;
	}

	public Integer getStCode() {
		return stCode;
	}

	public void setStCode(Integer stCode) {
		this.stCode = stCode;
	}

	public Integer getdCode() {
		return dCode;
	}

	public void setdCode(Integer dCode) {
		this.dCode = dCode;
	}

	public Integer getbCode() {
		return bCode;
	}

	public void setbCode(Integer bCode) {
		this.bCode = bCode;
	}

	public Integer getgCode() {
		return gCode;
	}

	public void setgCode(Integer gCode) {
		this.gCode = gCode;
	}

	public Integer getvCode() {
		return vCode;
	}

	public void setvCode(Integer vCode) {
		this.vCode = vCode;
	}

	public String getLocation1() {
		return location1;
	}

	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	public IwmpBlock getIwmpBlock() {
		return iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}

	public IwmpGramPanchayat getIwmpGramPanchayat() {
		return iwmpGramPanchayat;
	}

	public void setIwmpGramPanchayat(IwmpGramPanchayat iwmpGramPanchayat) {
		this.iwmpGramPanchayat = iwmpGramPanchayat;
	}

	public IwmpVillage getIwmpVillage() {
		return iwmpVillage;
	}

	public void setIwmpVillage(IwmpVillage iwmpVillage) {
		this.iwmpVillage = iwmpVillage;
	}
    
	
}

