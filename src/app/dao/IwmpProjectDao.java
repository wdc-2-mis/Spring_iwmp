package app.dao;

import java.math.BigDecimal;
import java.util.List;

import app.bean.ProjectSanctionedBean;
import app.model.IwmpMCsShare;
import app.model.IwmpMProject;

public interface IwmpProjectDao {
	public void addProject(IwmpMProject project);
	public int updateProjectList(List<IwmpMProject> project);
	public int deleteProject(int projId);
	public IwmpMProject getProject(int projId);
	public void updateProject(IwmpMProject project);
	public IwmpMProject getSequenceFinDistrict(int finYear,int dcode, int projectSeqNo);
	public List<IwmpMProject> ListSanctionedProjectNew(int statecode,int distcode, int finyear);
	public List<IwmpMProject> ListSanctionedProjectOpen(int statecode,int distcode, int finyear);
	public List<IwmpMProject> ListSanctionedProject(int stcode, int distcode, int finyear,int period,int areatype,String status); 
	public void addProjectList(List<IwmpMProject> project,IwmpMCsShare share,int statecode);
	public List<IwmpMProject> ListSanctionedProjectStatus(int stcode, int distcode, int finyear,  String status);
	public List<IwmpMProject> ListSanctionedProjectStatus(int statecode,int distcode, int finyear,String status,String Login);
	public List<IwmpMProject> ListSanctionedProjectStatus( String status);
	public List<ProjectSanctionedBean> getProjectSanctioned(int finyear);

}
