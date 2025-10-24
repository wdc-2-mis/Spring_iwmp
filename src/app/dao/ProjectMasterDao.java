package app.dao;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import app.model.IwmpMProject;

public interface ProjectMasterDao {
	LinkedHashMap<String, String> getProjectByDcode(Integer dCode);
	IwmpMProject getProjectByProjectId(Integer projectId);

	String getProjectByProjectCode(String projectCd); 
	LinkedHashMap<Integer,String> getProjectByRegIdPlan(Integer regId);
	LinkedHashMap<Integer,String> getProjectByRegId(Integer regId);
	LinkedHashMap<Integer, String> getDistrictByStcode(Integer stcode);
	LinkedHashMap<Integer, String> getProjNACByDcode(Integer dCode);
	LinkedHashMap<Integer,String> getProjBystCodedCode(Integer stCode, Integer dCode);
	LinkedHashMap<Integer,String> getProjByStateCode(Integer stCode);
	LinkedHashMap<Integer,String> getAdditionalMonth();
	
	
}
