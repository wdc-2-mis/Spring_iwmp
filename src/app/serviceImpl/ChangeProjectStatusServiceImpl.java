package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.ProjectDetailsBean;
import app.dao.ChangeProjectStatusDao;
import app.service.ChangeProjectStatusService;

@Service
public class ChangeProjectStatusServiceImpl implements ChangeProjectStatusService{

	@Autowired
	ChangeProjectStatusDao changeProjectStatusDao;
	
	@Override
	public List<ProjectDetailsBean> getProjectDetails(Integer st_code, Integer dcode) {
		// TODO Auto-generated method stub
		return changeProjectStatusDao.getProjectDetails(st_code, dcode);
	}

	@Override
	public void updateProjectStatus(List<Integer> projectIds, String username, String ipAddress) {
		// TODO Auto-generated method stub
		changeProjectStatusDao.updateProjectStatus(projectIds, username, ipAddress);
	}

}
