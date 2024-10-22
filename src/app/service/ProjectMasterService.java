package app.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.model.IwmpMProject;

@Service("projectMasterService")
public interface ProjectMasterService {
	LinkedHashMap<String,String> getProjectByDcode(Integer dCode);
	IwmpMProject getProjectByProjectId(Integer projectId);

	String getProjectByProjectCode(String projectCd);
	LinkedHashMap<Integer,String> getProjectByRegIdPlan(Integer regId);
	LinkedHashMap<Integer,String> getProjectByRegId(Integer regId);
	LinkedHashMap<Integer,String> getProjNACByDcode(Integer dCode);
	LinkedHashMap<Integer,String> getProjBystCodedCode(Integer stCode, Integer dCode);
	LinkedHashMap<Integer,String> getProjByStateCode(Integer stCode);
}
