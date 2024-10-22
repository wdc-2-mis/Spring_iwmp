package app.bean;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import app.model.IwmpMAreaType;
import app.model.IwmpMProject;

public class ProjectState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int stateCode;
	private int distCode;
	private int finCode;
	private int iwmpMAreaType;
	private String projectStatus;
	private int iwmpMProjectPrd;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public int getIwmpMAreaType() {
		return iwmpMAreaType;
	}
	public void setIwmpMAreaType(int iwmpMAreaType) {
		this.iwmpMAreaType = iwmpMAreaType;
	}
	public int getIwmpMProjectPrd() {
		return iwmpMProjectPrd;
	}
	public void setIwmpMProjectPrd(int iwmpMProjectPrd) {
		this.iwmpMProjectPrd = iwmpMProjectPrd;
	}
	
	@Valid
	private List<IwmpMProject> iwmpMProjectList;
	
	public int getStateCode() {
		return stateCode;
	}
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public List<IwmpMProject> getIwmpMProjectList() {
		return iwmpMProjectList;
	}
	public void setIwmpMProjectList(List<IwmpMProject> iwmpMProjectList) {
		this.iwmpMProjectList = iwmpMProjectList;
	}
	public int getFinCode() {
		return finCode;
	}
	public void setFinCode(int finCode) {
		this.finCode = finCode;
	}
	public int getDistCode() {
		return distCode;
	}
	public void setDistCode(int distCode) {
		this.distCode = distCode;
	}
	

}
