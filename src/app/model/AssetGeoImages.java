package app.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name ="asset_geo_images", schema ="public")
public class AssetGeoImages {

	private BigInteger asset_geo_images_id ;
	private AssetGeoReference assetGeoReference;
	private String images ;
	public AssetGeoImages() {
		
	}
	public AssetGeoImages(BigInteger asset_geo_images_id, AssetGeoReference assetGeoReference, String images) {
		this.asset_geo_images_id = asset_geo_images_id;
		this.assetGeoReference = assetGeoReference;
		this.images = images;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="asset_geo_images_id")
	public BigInteger getAsset_geo_images_id() {
		return asset_geo_images_id;
	}
	public void setAsset_geo_images_id(BigInteger asset_geo_images_id) {
		this.asset_geo_images_id = asset_geo_images_id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="asset_geo_reference_id")
	public AssetGeoReference getAssetGeoReference() {
		return assetGeoReference;
	}
	public void setAssetGeoReference(AssetGeoReference assetGeoReference) {
		this.assetGeoReference = assetGeoReference;
	}
	
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
}