package app.bean;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import app.model.IwmpDistrict;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;

public class VillageList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@NotNull(message = "Please Select State.")
	@Valid
	private int stateCode;
//	@NotNull(message = "Please Select District.")
//	@Valid
	private int distCode;
	private int blockCode;
	@NotNull(message = "Please Select Directory Level.")
	@Valid
	private int directoryLevel;
	private int grampanchayatCode;
	
	private List<IwmpVillage> inactivevillageList;
	private List<IwmpBlock> inactiveblockList;
	private List<IwmpGramPanchayat> inactivegpList;
	private List<IwmpDistrict> districtList;
	
	public List<IwmpDistrict> getDistrictList() {
		return districtList;
	}
	public void setDistrictList(List<IwmpDistrict> districtList) {
		this.districtList = districtList;
	}
	public List<IwmpBlock> getInactiveblockList() {
		return inactiveblockList;
	}
	public void setInactiveblockList(List<IwmpBlock> inactiveblockList) {
		this.inactiveblockList = inactiveblockList;
	}
	public List<IwmpGramPanchayat> getInactivegpList() {
		return inactivegpList;
	}
	public void setInactivegpList(List<IwmpGramPanchayat> inactivegpList) {
		this.inactivegpList = inactivegpList;
	}
	public int getStateCode() {
		return stateCode;
	}
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public int getDistCode() {
		return distCode;
	}
	public void setDistCode(int distCode) {
		this.distCode = distCode;
	}
	public int getBlockCode() {
		return blockCode;
	}
	public void setBlockCode(int blockCode) {
		this.blockCode = blockCode;
	}
	public int getGrampanchayatCode() {
		return grampanchayatCode;
	}
	public void setGrampanchayatCode(int grampanchayatCode) {
		this.grampanchayatCode = grampanchayatCode;
	}
	public List<IwmpVillage> getInactivevillageList() {
		return inactivevillageList;
	}
	public void setInactivevillageList(List<IwmpVillage> villageList) {
		this.inactivevillageList = villageList;
	}
	public int getDirectoryLevel() {
		return directoryLevel;
	}
	public void setDirectoryLevel(int directoryLevel) {
		this.directoryLevel = directoryLevel;
	}
	
}
