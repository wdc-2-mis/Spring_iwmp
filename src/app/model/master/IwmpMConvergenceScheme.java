package app.model.master;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.model.ConvergenceWorkDetail;

@SuppressWarnings("serial")
@Entity
@Table(name="iwmp_m_convergence_scheme",schema="public")

public class IwmpMConvergenceScheme implements java.io.Serializable{

	private Integer schemeId;
	private String schemeName;
	private Set<ConvergenceWorkDetail> convergenceWorkDetail = new HashSet<ConvergenceWorkDetail>(0);
	
	
	public IwmpMConvergenceScheme() {
		
	}


	public IwmpMConvergenceScheme(int schemeId, String schemeName) {
		this.schemeId = schemeId;
		this.schemeName = schemeName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scheme_id", unique = true, nullable = false)
	public Integer getSchemeId() {
		return schemeId;
	}


	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	@Column(name="scheme_name", length=100)
	public String getSchemeName() {
		return schemeName;
	}


	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	 @OneToMany(fetch=FetchType.LAZY, mappedBy="iwmpmConvergenceScheme")
		public Set<ConvergenceWorkDetail> getConvergenceWorkDetail() {
			return convergenceWorkDetail;
		}

		public void setConvergenceWorkDetail(Set<ConvergenceWorkDetail> convergenceWorkDetail) {
			this.convergenceWorkDetail = convergenceWorkDetail;
		}
	
}
