package app.projectevaluation.model;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="wdcpmksy_quality_shape_files" ,schema="public")
public class WdcpmksyQualityShapeFiles implements java.io.Serializable{
	
	private int qualityShapeId;								//   quality_shape_id
	private WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation;   ///   project_profile_id
	private BigDecimal shapeFileArea;							//		shape_file_area
	private String shapeFileAreaRemark;							//		shape_file_area_remark 
	private BigDecimal variationArea;							//		variation_area
	private String variationAreaRemark;							//      variation_area_remark
	private Date createdOn ;                    //created_on ;
	private String createdBy ;         //created_by ;
	private Date updatedOn ;                 //updated_on ; 
	private String requestIp; 
	
	public WdcpmksyQualityShapeFiles() {
    }

	
    public WdcpmksyQualityShapeFiles(int qualityShapeId) {
        this.qualityShapeId = qualityShapeId;
    }
    
    public WdcpmksyQualityShapeFiles(int qualityShapeId, WdcpmksyProjectProfileEvaluation  wdcpmksyProjectProfileEvaluation, BigDecimal shapeFileArea, String shapeFileAreaRemark, BigDecimal variationArea, String variationAreaRemark,
    		Date createdOn, String createdBy, Date updatedOn, String requestIp) {
        this.qualityShapeId = qualityShapeId;
        this.wdcpmksyProjectProfileEvaluation=wdcpmksyProjectProfileEvaluation;
        this.shapeFileArea=shapeFileArea;
        this.shapeFileAreaRemark=shapeFileAreaRemark;
        this.variationArea=variationArea;
        this.variationAreaRemark=variationAreaRemark;
        this.createdOn=createdOn;
        this.createdBy=createdBy;
        this.updatedOn=updatedOn;
        this.requestIp=requestIp;
        
    }

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="quality_shape_id", unique=true, nullable=false)
	public int getQualityShapeId() {
		return qualityShapeId;
	}


	public void setQualityShapeId(int qualityShapeId) {
		this.qualityShapeId = qualityShapeId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_profile_id")
	public WdcpmksyProjectProfileEvaluation getWdcpmksyProjectProfileEvaluation() {
		return wdcpmksyProjectProfileEvaluation;
	}


	public void setWdcpmksyProjectProfileEvaluation(WdcpmksyProjectProfileEvaluation wdcpmksyProjectProfileEvaluation) {
		this.wdcpmksyProjectProfileEvaluation = wdcpmksyProjectProfileEvaluation;
	}

	@Column(name="shape_file_area", precision=20)
	public BigDecimal getShapeFileArea() {
		return shapeFileArea;
	}


	public void setShapeFileArea(BigDecimal shapeFileArea) {
		this.shapeFileArea = shapeFileArea;
	}

	@Column(name="shape_file_area_remark", length=200)
	public String getShapeFileAreaRemark() {
		return shapeFileAreaRemark;
	}


	public void setShapeFileAreaRemark(String shapeFileAreaRemark) {
		this.shapeFileAreaRemark = shapeFileAreaRemark;
	}

	@Column(name="variation_area", precision=20)
	public BigDecimal getVariationArea() {
		return variationArea;
	}


	public void setVariationArea(BigDecimal variationArea) {
		this.variationArea = variationArea;
	}

	@Column(name="variation_area_remark", length=200)
	public String getVariationAreaRemark() {
		return variationAreaRemark;
	}


	public void setVariationAreaRemark(String variationAreaRemark) {
		this.variationAreaRemark = variationAreaRemark;
	}


	@Temporal(TemporalType.DATE)
    @Column(name="created_on")
	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="created_by", length=20)
	public String getCreatedBy() {
		return createdBy;
	}

	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name="request_ip", length=20)
	public String getRequestIp() {
		return requestIp;
	}


	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
    
    
    
    
	
	
	
	
	
	
	
	
	

}
