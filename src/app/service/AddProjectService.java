package app.service;

import java.math.BigDecimal;
import java.util.List;

import app.bean.ProjectSanctionedBean;
import app.model.IwmpMCsShare;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;

public interface AddProjectService {
	public void addProject(IwmpMProject project);
	public IwmpMProject getProject(int projId);
	public int deleteProject(int projId);
	public void addProjectList(List<IwmpMProject> project,IwmpMCsShare share,int statecode);
	public void updateProject(IwmpMProject project);
	public int updateProjectList(List<IwmpMProject> project);
	public List<IwmpMProject> getListSanctionedProjectNew(int statecode,int distcode, int finyear);
	public List<IwmpMProject> getListSanctionedProjectOpen(int statecode,int distcode, int finyear);
	public List<IwmpMProject> getListSanctionedProject(int statecode,int distcode, int finyear,int period,int areatype,String status);
	public IwmpMProject getSequenceFinDistrict(int finYear,int dcode,int projectSeqNo);
	public List<IwmpMProject> getListSanctionedProjectStatus(int statecode,int distcode, int finyear,String status);
	public List<IwmpMProject> getListSanctionedProjectStatus(int statecode,int distcode, int finyear,String status,String Login);
	public List<IwmpMProject> getListSanctionedProjectStatus(String status);
	public List<ProjectSanctionedBean> getProjectSanctioned(int finyear);
}
