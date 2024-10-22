package app.service.master;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.ProjectLocationBean;

@Service("AddExistingProjectLocationService")
public interface AddExistingProjectLocationService {
	
	LinkedHashMap<Integer,String> getProjectLocationProject(Integer regId);
	LinkedHashMap<Integer,String> getVillageBlockWise(Integer bcode, Integer project);
	Boolean saveProjectLocationAsDraft(List<Integer> vCode,Integer pcode,String loginId);
	List<ProjectLocationBean> getPiaAssignWc(Integer pcode);
	List<ProjectLocationBean> getPiaAssignWcFirst(Integer pcode);
	Boolean saveWCLocationAsDraft(List<String> activity, List<Integer> plid, String loginId, Integer project);
	Boolean completeWCMappingExisting(String loginId, Integer projid);

}
