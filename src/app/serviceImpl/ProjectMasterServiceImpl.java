package app.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.ProjectMasterDao;
import app.model.IwmpMProject;
import app.service.ProjectMasterService;

@Service("projectMasterService")
public class ProjectMasterServiceImpl implements ProjectMasterService{

	@Autowired(required = true)
	ProjectMasterDao projectMasterDao;
	
	@Override
	//@Transactional
	public LinkedHashMap<String, String> getProjectByDcode(Integer dCode) {
		// TODO Auto-generated method stub
		return projectMasterDao.getProjectByDcode(dCode);
	}

	@Override
	//@Transactional
	public IwmpMProject getProjectByProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		return projectMasterDao.getProjectByProjectId(projectId);
	}
	
	
	  @Override //@Transactional 
	  public String getProjectByProjectCode(String projectCd) { 
		  // TODO Auto-generated method stub 
		  return projectMasterDao.getProjectByProjectCode(projectCd); }
	 
	
	
	@Override
	public LinkedHashMap<Integer, String> getProjectByRegId(Integer regId) {
		// TODO Auto-generated method stub
		return projectMasterDao.getProjectByRegId(regId);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjNACByDcode(Integer dCode) {
		// TODO Auto-generated method stub
		return projectMasterDao.getProjNACByDcode(dCode);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjBystCodedCode(Integer stCode, Integer dCode) {
		// TODO Auto-generated method stub
		return projectMasterDao.getProjBystCodedCode(stCode, dCode);
	}


	@Override
	public LinkedHashMap<Integer, String> getProjByStateCode(Integer stCode) {
		// TODO Auto-generated method stub
		return projectMasterDao.getProjByStateCode(stCode);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectByRegIdPlan(Integer regId) {
		// TODO Auto-generated method stub
		return projectMasterDao.getProjectByRegIdPlan(regId);
	}

	

}
