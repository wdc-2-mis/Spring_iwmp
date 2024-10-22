package app.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.bean.ProjectSanctionedBean;
import app.dao.IwmpProjectDao;
import app.dao.MenuDao;
import app.model.IwmpMCsShare;
import app.model.IwmpMProject;
import app.service.AddProjectService;

@Service
//@Transactional
public class AddProjectServiceImpl implements AddProjectService{
	@Autowired
    private IwmpProjectDao projectDAO;

	@Override
	//@Transactional(rollbackFor = {RuntimeException.class})
	public void addProject(IwmpMProject project) {
		// TODO Auto-generated method stub
		projectDAO.addProject(project);
	}

	@Override
	public void addProjectList(List<IwmpMProject> project,IwmpMCsShare share,int statecode) {
		// TODO Auto-generated method stub
		projectDAO.addProjectList(project,share,statecode);
		
	}

	@Override
	public IwmpMProject getSequenceFinDistrict(int finYear, int dcode, int projectSeqNo) {
		// TODO Auto-generated method stub
		return projectDAO.getSequenceFinDistrict(finYear, dcode,projectSeqNo);
	}

	@Override
	public List<IwmpMProject> getListSanctionedProjectNew(int statecode,int distcode, int finyear) {
		// TODO Auto-generated method stub
		return projectDAO.ListSanctionedProjectNew(statecode,distcode,finyear);
	}

	@Override
	public List<IwmpMProject> getListSanctionedProjectOpen(int statecode,int distcode, int finyear) {
		// TODO Auto-generated method stub
		return projectDAO.ListSanctionedProjectOpen(statecode, distcode, finyear);
	}
	
	@Override
	public List<ProjectSanctionedBean> getProjectSanctioned(int finyear) {
		return projectDAO.getProjectSanctioned(finyear);
	}
	
	@Override
	public List<IwmpMProject> getListSanctionedProject(int statecode,int distcode, int finyear,int period,int areatype,String status) {
		// TODO Auto-generated method stub
		return projectDAO.ListSanctionedProject(statecode, distcode, finyear,period,areatype,status);
	}
	@Override
	public List<IwmpMProject> getListSanctionedProjectStatus(int statecode,int distcode, int finyear,String status) {
		// TODO Auto-generated method stub
		return projectDAO.ListSanctionedProjectStatus(statecode, distcode, finyear,status);
	}

	@Override
	public int deleteProject(int projId) {
		// TODO Auto-generated method stub
		 return projectDAO.deleteProject(projId);
	}
	public IwmpMProject getProject(int projId) {
		 return projectDAO.getProject(projId);
	}
	@Override
	public int updateProjectList(List<IwmpMProject> project) {
		// TODO Auto-generated method stub
		return projectDAO.updateProjectList(project);
		
	}

	@Override
	public void updateProject(IwmpMProject project) {
		// TODO Auto-generated method stub
		projectDAO.updateProject(project);
		
	}

	@Override
	public List<IwmpMProject> getListSanctionedProjectStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IwmpMProject> getListSanctionedProjectStatus(int statecode, int distcode, int finyear, String status,
			String Login) {
		// TODO Auto-generated method stub
		return projectDAO.ListSanctionedProjectStatus(statecode, distcode, finyear,status,Login);
	}

	

}
