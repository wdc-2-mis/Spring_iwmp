package app.model;

import java.math.BigInteger;
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
@Table(name ="asset_geo_reference", schema ="public")
public class AssetGeoReference {

	private BigInteger assetGeoRefId;
	private String stName;
	private String disName;
	private String projName ;
	private String headDes;
	private String activity_description ;
	private String activity_type_description ;
	private String work_type ;
	private String work_serial_code ;
	private String observername ;
	private String stage ;
	private String server_time ;
	private String project_code ;
	private Integer state_code ;
	private Integer district_code ;
	private Integer activity_code;
	private Integer activity_type_code ;
	private Character head_code ;
	private String feature_type ;
	private String status ;
	private BigInteger collection_sno;
	private BigInteger mobile ;
	private Set<AssetGeoImages> images;
	
	public AssetGeoReference() {
		super();
	}

	public AssetGeoReference(BigInteger assetGeoRefId, String stName, String disName, String projName, String headDes,
			String activity_description, String activity_type_description, String work_type,
			String work_serial_code, String observername, String stage, String server_time, String project_code,
			Integer state_code, Integer district_code, Integer activity_code, Integer activity_type_code,
			Character head_code, String feature_type, String status, BigInteger collection_sno, BigInteger mobile, Set<AssetGeoImages> images) {
		super();
		this.assetGeoRefId = assetGeoRefId;
		this.stName = stName;
		this.disName = disName;
		this.projName = projName;
		this.headDes = headDes;
		this.activity_description = activity_description;
		this.activity_type_description = activity_type_description;
		this.work_type = work_type;
		this.work_serial_code = work_serial_code;
		this.observername = observername;
		this.stage = stage;
		this.server_time = server_time;
		this.project_code = project_code;
		this.state_code = state_code;
		this.district_code = district_code;
		this.activity_code = activity_code;
		this.activity_type_code = activity_type_code;
		this.head_code = head_code;
		this.feature_type = feature_type;
		this.status = status;
		this.collection_sno = collection_sno;
		this.mobile = mobile;
		this.images = images;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="asset_geo_reference_id")
	public BigInteger getAssetGeoRefId() {
		return assetGeoRefId;
	}

	public void setAssetGeoRefId(BigInteger assetGeoRefId) {
		this.assetGeoRefId = assetGeoRefId;
	}

	@Column(name ="state_name")
	public String getStName() {
		return stName;
	}

	public void setStName(String stName) {
		this.stName = stName;
	}

	@Column(name ="district_name")
	public String getDisName() {
		return disName;
	}

	public void setDisName(String disName) {
		this.disName = disName;
	}

	@Column(name ="project_name")
	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	@Column(name ="head_description")
	public String getHeadDes() {
		return headDes;
	}

	public void setHeadDes(String headDes) {
		this.headDes = headDes;
	}

	public String getActivity_description() {
		return activity_description;
	}

	public void setActivity_description(String activity_description) {
		this.activity_description = activity_description;
	}

	public String getActivity_type_description() {
		return activity_type_description;
	}

	public void setActivity_type_description(String activity_type_description) {
		this.activity_type_description = activity_type_description;
	}

	public String getWork_type() {
		return work_type;
	}

	public void setWork_type(String work_type) {
		this.work_type = work_type;
	}

	public String getWork_serial_code() {
		return work_serial_code;
	}

	public void setWork_serial_code(String work_serial_code) {
		this.work_serial_code = work_serial_code;
	}

	public String getObservername() {
		return observername;
	}

	public void setObservername(String observername) {
		this.observername = observername;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getServer_time() {
		return server_time;
	}

	public void setServer_time(String server_time) {
		this.server_time = server_time;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public Integer getState_code() {
		return state_code;
	}

	public void setState_code(Integer state_code) {
		this.state_code = state_code;
	}

	public Integer getDistrict_code() {
		return district_code;
	}

	public void setDistrict_code(Integer district_code) {
		this.district_code = district_code;
	}

	public Integer getActivity_code() {
		return activity_code;
	}

	public void setActivity_code(Integer activity_code) {
		this.activity_code = activity_code;
	}

	public Integer getActivity_type_code() {
		return activity_type_code;
	}

	public void setActivity_type_code(Integer activity_type_code) {
		this.activity_type_code = activity_type_code;
	}

	public Character getHead_code() {
		return head_code;
	}

	public void setHead_code(Character head_code) {
		this.head_code = head_code;
	}

	public String getFeature_type() {
		return feature_type;
	}

	public void setFeature_type(String feature_type) {
		this.feature_type = feature_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigInteger getCollection_sno() {
		return collection_sno;
	}

	public void setCollection_sno(BigInteger collection_sno) {
		this.collection_sno = collection_sno;
	}

	public BigInteger getMobile() {
		return mobile;
	}

	public void setMobile(BigInteger mobile) {
		this.mobile = mobile;
	}

	@OneToMany(mappedBy = "assetGeoReference",fetch = FetchType.LAZY)
	public Set<AssetGeoImages> getImages() {
		return images;
	}

	public void setImages(Set<AssetGeoImages> images) {
		this.images = images;
	}

	
	
	
}
