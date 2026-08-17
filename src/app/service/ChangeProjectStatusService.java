package app.service;

import java.util.List;

import app.bean.ProjectDetailsBean;

public interface ChangeProjectStatusService {
	
	List<ProjectDetailsBean> getProjectDetails(Integer st_code, Integer dcode);
	
	public void updateProjectStatus(List<Integer> projectIds, String username, String ipAddress);

}
