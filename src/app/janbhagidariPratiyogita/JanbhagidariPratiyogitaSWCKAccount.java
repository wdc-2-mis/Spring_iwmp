package app.janbhagidariPratiyogita;

import java.util.Date;
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

import app.model.master.IwmpGramPanchayat;

@Entity
@Table(name = "janbhagidari_pratiyogita_swck_account", schema = "public")
public class JanbhagidariPratiyogitaSWCKAccount {

	private Integer swck_account_id;
	private JanbhagidariPratiyogita janbhagidariPratiyogita;
	private IwmpGramPanchayat iwmpGramPanchayat;
	private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    
   public JanbhagidariPratiyogitaSWCKAccount() {}
   
   public JanbhagidariPratiyogitaSWCKAccount(Integer swck_account_id) {
	   this.swck_account_id=swck_account_id;
   } 
    
    
   public JanbhagidariPratiyogitaSWCKAccount(Integer swck_account_id, JanbhagidariPratiyogita janbhagidariPratiyogita, IwmpGramPanchayat iwmpGramPanchayat, 
		   String requestedIp, String updatedBy, Date updatedDate, String createdBy, Date createdDate) {
	   
	   this.swck_account_id=swck_account_id;
	   this.janbhagidariPratiyogita=janbhagidariPratiyogita;
	   this.iwmpGramPanchayat=iwmpGramPanchayat;
	   this.requestedIp=requestedIp;
	   this.updatedBy=updatedBy;
	   this.updatedDate=updatedDate;
	   this.createdBy=createdBy;
	   this.createdDate=createdDate;
	   
   }

   	@Id
   	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="swck_account_id", unique=true, nullable=false)
	public Integer getSwck_account_id() {
		return swck_account_id;
	}
	
	public void setSwck_account_id(Integer swck_account_id) {
		this.swck_account_id = swck_account_id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "pratiyogita_id")
	public JanbhagidariPratiyogita getJanbhagidariPratiyogita() {
		return janbhagidariPratiyogita;
	}
	
	public void setJanbhagidariPratiyogita(JanbhagidariPratiyogita janbhagidariPratiyogita) {
		this.janbhagidariPratiyogita = janbhagidariPratiyogita;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gcode")
	public IwmpGramPanchayat getIwmpGramPanchayat() {
		return iwmpGramPanchayat;
	}
	
	public void setIwmpGramPanchayat(IwmpGramPanchayat iwmpGramPanchayat) {
		this.iwmpGramPanchayat = iwmpGramPanchayat;
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
