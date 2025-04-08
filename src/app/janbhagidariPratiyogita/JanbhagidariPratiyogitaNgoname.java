package app.janbhagidariPratiyogita;

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

@Entity
@Table(name = "janbhagidari_pratiyogita_ngoname", schema = "public")
public class JanbhagidariPratiyogitaNgoname {
	
	private Integer ngoname_id;
	private JanbhagidariPratiyogita janbhagidariPratiyogita;
	private String ngo_name;
	private String requestedIp;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    private Set<JanbhagidariPratiyogitaNgovillage> janbhagidariPratiyogitaNgovillage = new HashSet<JanbhagidariPratiyogitaNgovillage>(0);
    
    
    
    
    public JanbhagidariPratiyogitaNgoname() {}
    
    public JanbhagidariPratiyogitaNgoname(Integer ngoname_id) {
    	
    	this.ngoname_id=ngoname_id;
    }
    
    public JanbhagidariPratiyogitaNgoname(Integer ngoname_id, JanbhagidariPratiyogita janbhagidariPratiyogita, String ngo_name, String requestedIp, 
    		String updatedBy, Date updatedDate, String createdBy, Date createdDate, Set<JanbhagidariPratiyogitaNgovillage> janbhagidariPratiyogitaNgovillage ) {
    	
    	this.ngoname_id=ngoname_id;
    	this.janbhagidariPratiyogita=janbhagidariPratiyogita;
    	this.ngo_name=ngo_name;
    	this.requestedIp=requestedIp;
		this.updatedBy=updatedBy;
		this.updatedDate=updatedDate;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.janbhagidariPratiyogitaNgovillage=janbhagidariPratiyogitaNgovillage;
    	
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ngoname_id", unique=true, nullable=false)
	public Integer getNgoname_id() {
		return ngoname_id;
	}

	public void setNgoname_id(Integer ngoname_id) {
		this.ngoname_id = ngoname_id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "pratiyogita_id")
	public JanbhagidariPratiyogita getJanbhagidariPratiyogita() {
		return janbhagidariPratiyogita;
	}

	public void setJanbhagidariPratiyogita(JanbhagidariPratiyogita janbhagidariPratiyogita) {
		this.janbhagidariPratiyogita = janbhagidariPratiyogita;
	}

	@Column(name = "ngo_name")
	public String getNgo_name() {
		return ngo_name;
	}

	public void setNgo_name(String ngo_name) {
		this.ngo_name = ngo_name;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="janbhagidariPratiyogitaNgoname")
	public Set<JanbhagidariPratiyogitaNgovillage> getJanbhagidariPratiyogitaNgovillage() {
		return janbhagidariPratiyogitaNgovillage;
	}

	public void setJanbhagidariPratiyogitaNgovillage(
			Set<JanbhagidariPratiyogitaNgovillage> janbhagidariPratiyogitaNgovillage) {
		this.janbhagidariPratiyogitaNgovillage = janbhagidariPratiyogitaNgovillage;
	}
    
    
    
    
    
    
    
    

}
