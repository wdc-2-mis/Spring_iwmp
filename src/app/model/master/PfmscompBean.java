package app.model.master;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pfms_eatmisdata_detail"
    ,schema="public"
)
public class PfmscompBean implements java.io.Serializable  {
	
	private String component_code;
    private String component_name;
    
    public PfmscompBean() {
    }

	
    public PfmscompBean(String component_code) {
        this.component_code = component_code;
    }
    public PfmscompBean(String component_code, String component_name) {
       this.component_code = component_code;
       this.component_name = component_name;
    }
    
    
    @Id 

    @Column(name="component_code", unique=true, nullable=false)
	public String getComponent_code() {
		return component_code;
	}
	public void setComponent_code(String component_code) {
		this.component_code = component_code;
	}
	
	@Column(name="component_name", length=100)
	public String getComponent_name() {
		return component_name;
	}
	public void setComponent_name(String component_name) {
		this.component_name = component_name;
	}
	
	
    
    

}
