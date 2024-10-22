package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.ProjectLocationBean;

@Service("projectLocationService")
public interface ProjectLocationService {
	
	Boolean saveProjectLocationAsDraft(List<Integer> vCode,Integer pcode,String loginId);
	Boolean completeProjectLocation(List<Integer> vCode,Integer pcode,String loginId);
	List<ProjectLocationBean> getPreFilledProjectLocationData(Integer pcode);
	List<ProjectLocationBean> getPiaAssignWc(Integer pcode);
	LinkedHashMap<String,List<ProjectLocationBean>> getmpngprolist(String user_id);
	List<ProjectLocationBean> getWsCommittee(Integer projectId);
	Boolean saveWCLocationAsDraft(String final1, String loginId);
	List<ProjectLocationBean> getPiaAssigndraftWc(Integer pcode);
	Boolean detPiaAssigndraftWc(Integer pwccode);
	Boolean completeWCMapping(String projwcid, String loginId, Integer projid);
	List<ProjectLocationBean> getcheckWCStatus(Integer projectId);
	List<ProjectLocationBean> getPiaAssignfinalWc(Integer projectId);
	
	List<ProjectLocationBean> getProjectLocationList(Integer state, Integer district, Integer project);
	
	List<ProjectLocationBean> getLocationAndBaselineDraft(Integer st_code, String userType);
	
}
