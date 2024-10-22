package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.ProjectLocationBean;
import app.model.IwmpMProject;

public interface ProjectLocationDao {
	
		Boolean saveProjectLocationAsDraft(List<Integer> vCode,Integer pcode,String loginId);
		Boolean completeProjectLocation(List<Integer> vCode,Integer pcode,String loginId);
		List<ProjectLocationBean> getPreFilledProjectLocationData(Integer pcode);
		List<ProjectLocationBean> getPiaAssignWc(Integer pcode);
		LinkedHashMap<String, List<ProjectLocationBean>> getmpngprolist(String user_id);
		List<ProjectLocationBean> getWsCommittee(Integer pcode);
		Boolean saveWCLocationAsDraft(String final1, String loginId);
		List<ProjectLocationBean> getPiaAssigndraftWc(Integer pcode);
		Boolean detPiaAssigndraftWc(Integer pcode);
		Boolean completeWCMapping(String projwcid, String loginId, Integer projid);
		List<ProjectLocationBean> getcheckWCStatus(Integer projectId);
		List<ProjectLocationBean> getPiaAssignfinalWc(Integer pcode);
		List<ProjectLocationBean> getProjectLocationList(Integer state, Integer district, Integer project);
		List<ProjectLocationBean> getLocationAndBaselineDraft(Integer st_code, String userType);
}
