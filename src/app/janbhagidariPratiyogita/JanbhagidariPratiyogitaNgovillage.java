package app.janbhagidariPratiyogita;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.master.IwmpVillage;

@Entity
@Table(name = "janbhagidari_pratiyogita_ngovillage", schema = "public")
public class JanbhagidariPratiyogitaNgovillage {
	
	
	private Integer ngovillage_id;
	private JanbhagidariPratiyogitaNgoname janbhagidariPratiyogitaNgoname;
	private IwmpVillage iwmpVillage;
	private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
	
	
public JanbhagidariPratiyogitaNgovillage() {}

public JanbhagidariPratiyogitaNgovillage(Integer ngovillage_id) {
	this.ngovillage_id=ngovillage_id;
}

public JanbhagidariPratiyogitaNgovillage(Integer ngovillage_id, JanbhagidariPratiyogitaNgoname janbhagidariPratiyogitaNgoname, IwmpVillage iwmpVillage, String requestedIp, 
		String updatedBy, Date updatedDate, String createdBy, Date createdDate) {
	
	this.ngovillage_id=ngovillage_id;
	this.janbhagidariPratiyogitaNgoname=janbhagidariPratiyogitaNgoname;
	this.iwmpVillage=iwmpVillage;
	this.requestedIp=requestedIp;
	this.updatedBy=updatedBy;
	this.updatedDate=updatedDate;
	this.createdBy=createdBy;
	this.createdDate=createdDate;
}

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="ngovillage_id", unique=true, nullable=false)
public Integer getNgovillage_id() {
	return ngovillage_id;
}

public void setNgovillage_id(Integer ngovillage_id) {
	this.ngovillage_id = ngovillage_id;
}

@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name = "ngoname_id")
public JanbhagidariPratiyogitaNgoname getJanbhagidariPratiyogitaNgoname() {
	return janbhagidariPratiyogitaNgoname;
}

public void setJanbhagidariPratiyogitaNgoname(JanbhagidariPratiyogitaNgoname janbhagidariPratiyogitaNgoname) {
	this.janbhagidariPratiyogitaNgoname = janbhagidariPratiyogitaNgoname;
}

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "vcode")
public IwmpVillage getIwmpVillage() {
	return iwmpVillage;
}

public void setIwmpVillage(IwmpVillage iwmpVillage) {
	this.iwmpVillage = iwmpVillage;
}

@Column(name = "requested_ip")
public String getRequestedIp() {
	return requestedIp;
}

public void setRequestedIp(String requestedIp) {
	this.requestedIp = requestedIp;
}
@Column(name = "updated_by")
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
@Column(name = "created_by")
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



	
	
}
