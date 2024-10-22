package app.dao.master;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.ProjectLocationBean;

public interface AddExistingProjectLocationDao {
	
	LinkedHashMap<Integer,String> getProjectLocationProject(Integer regId);
	LinkedHashMap<Integer,String> getVillageBlockWise(Integer bcode, Integer project);
	Boolean saveProjectLocationAsDraft(List<Integer> vCode,Integer pcode,String loginId);
	List<ProjectLocationBean> getPiaAssignWc(Integer pcode);
	Boolean saveWCLocationAsDraft(List<String> activity, List<Integer> plid, String loginId, Integer project);
	Boolean completeWCMappingExisting(String loginId, Integer projid);
	List<ProjectLocationBean> getPiaAssignWcFirst(Integer pcode);

}
