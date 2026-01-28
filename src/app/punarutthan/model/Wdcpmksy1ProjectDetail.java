package app.punarutthan.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.model.master.IwmpBlock;

@Entity
@Table(name="wdcpmksy1_project_detail" ,schema="public")
public class Wdcpmksy1ProjectDetail implements java.io.Serializable{
	
	
	
	private int projdtlId;
	private String projectCd;
	private String projName;
	private BigDecimal areaProposed;
	private BigDecimal projectCost;
	private BigDecimal centralShareAmt;
	private BigDecimal stateShareAmt;
	private Integer stateLgd;
	private Integer distLgd;
	private Integer blockLgd;
	private IwmpBlock iwmpBlock;
	private Integer blockCode;
	
	
	public Wdcpmksy1ProjectDetail() { }
    
    public Wdcpmksy1ProjectDetail(Integer projdtlId) {
    	this.projdtlId = projdtlId;
    }
	
    public Wdcpmksy1ProjectDetail(Integer projdtlId, String projectCd, String projName, BigDecimal areaProposed, BigDecimal projectCost, BigDecimal centralShareAmt,
    		BigDecimal stateShareAmt, Integer stateLgd, Integer distLgd, Integer blockLgd, IwmpBlock iwmpBlock, Integer blockCode) {
    	this.projdtlId=projdtlId;
    	this.projectCd=projectCd;
    	this.projName=projName;
    	this.areaProposed=areaProposed;
    	this.projectCost=projectCost;
    	this.centralShareAmt=centralShareAmt;
    	this.stateShareAmt=stateShareAmt;
    	this.stateLgd=stateLgd;
    	this.distLgd=distLgd;
    	this.blockLgd=blockLgd;
    	this.iwmpBlock=iwmpBlock;
    	this.blockCode=blockCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="projdtl_id", unique=true, nullable=false)
	public int getProjdtlId() {
		return projdtlId;
	}

	public void setProjdtlId(int projdtlId) {
		this.projdtlId = projdtlId;
	}

	@Column(name = "project_cd")
	public String getProjectCd() {
		return projectCd;
	}

	public void setProjectCd(String projectCd) {
		this.projectCd = projectCd;
	}

	@Column(name = "proj_name")
	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	@Column(name = "area_proposed")
	public BigDecimal getAreaProposed() {
		return areaProposed;
	}

	public void setAreaProposed(BigDecimal areaProposed) {
		this.areaProposed = areaProposed;
	}

	@Column(name = "project_cost")
	public BigDecimal getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(BigDecimal projectCost) {
		this.projectCost = projectCost;
	}

	@Column(name = "central_share_amt")
	public BigDecimal getCentralShareAmt() {
		return centralShareAmt;
	}

	public void setCentralShareAmt(BigDecimal centralShareAmt) {
		this.centralShareAmt = centralShareAmt;
	}

	@Column(name = "state_share_amt")
	public BigDecimal getStateShareAmt() {
		return stateShareAmt;
	}

	public void setStateShareAmt(BigDecimal stateShareAmt) {
		this.stateShareAmt = stateShareAmt;
	}

	@Column(name = "state_lgd")
	public Integer getStateLgd() {
		return stateLgd;
	}

	public void setStateLgd(Integer stateLgd) {
		this.stateLgd = stateLgd;
	}

	@Column(name = "dist_lgd")
	public Integer getDistLgd() {
		return distLgd;
	}

	public void setDistLgd(Integer distLgd) {
		this.distLgd = distLgd;
	}

	@Column(name = "block_lgd")
	public Integer getBlockLgd() {
		return blockLgd;
	}

	public void setBlockLgd(Integer blockLgd) {
		this.blockLgd = blockLgd;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "bcode")
	public IwmpBlock getIwmpBlock() {
		return iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}

	@Column(name = "block_code")
	public Integer getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(Integer blockCode) {
		this.blockCode = blockCode;
	}
	
	
	

}
