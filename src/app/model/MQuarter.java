package app.model;


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

@Entity
@Table(name="m_quarter" ,schema="public")
public class MQuarter  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int quartCd;
    private String quartDesc;
//    private Set<WdcpmksyConvergenceDetail> convergenceDetails = new HashSet<>(0);

    public MQuarter() {
    }
	
    public MQuarter(int quartCd) {
        this.quartCd = quartCd;
    }
    public MQuarter(int quartCd, String quartDesc) {
       this.quartCd = quartCd;
       this.quartDesc = quartDesc;
//       this.convergenceDetails = convergenceDetails;
    }
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="quart_cd", unique=true, nullable=false)
    public int getQuartCd() {
        return this.quartCd;
    }
    
    public void setQuartCd(int quartCd) {
        this.quartCd = quartCd;
    }
    
    @Column(name="quart_desc", length=50)
    public String getQuartDesc() {
        return this.quartDesc;
    }
    
    public void setQuartDesc(String quartDesc) {
        this.quartDesc = quartDesc;
    }

//    @OneToMany(fetch=FetchType.LAZY, mappedBy="MQuarter")
//	public Set<WdcpmksyConvergenceDetail> getConvergenceDetails() {
//		return convergenceDetails;
//	}

//	public void setConvergenceDetails(Set<WdcpmksyConvergenceDetail> convergenceDetails) {
//		this.convergenceDetails = convergenceDetails;
//	}
    
    

}
