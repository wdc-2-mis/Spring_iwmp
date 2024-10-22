package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.ProjectLocationBean;
import app.dao.ProjectLocationDao;
import app.model.IwmpMProject;
import app.service.ProjectLocationService;

@Service("projectLocationService")
public class ProjectLocationServiceImpl implements ProjectLocationService{
	
	@Autowired
	ProjectLocationDao dao;

	

	@Override
	public Boolean saveProjectLocationAsDraft(List<Integer> vCode, Integer pcode,String loginId) {
		// TODO Auto-generated method stub
		return dao.saveProjectLocationAsDraft(vCode,pcode,loginId);
	}

	@Override
	public Boolean saveWCLocationAsDraft(String final1, String loginId) {
		// TODO Auto-generated method stub
		return dao.saveWCLocationAsDraft(final1,loginId);
	}
	
	
	@Override
	public List<ProjectLocationBean> getPreFilledProjectLocationData(Integer pcode) {
		// TODO Auto-generated method stub
		return dao.getPreFilledProjectLocationData(pcode);
	}

	@Override
	public Boolean completeProjectLocation(List<Integer> vCode, Integer pcode, String loginId) {
		// TODO Auto-generated method stub
		return dao.completeProjectLocation(vCode,pcode,loginId);
	}

	@Override
	public Boolean completeWCMapping(String projwcid, String loginId, Integer projid ) {
		// TODO Auto-generated method stub
		return dao.completeWCMapping(projwcid,loginId, projid);
	}
	
	
@Override
	public List<ProjectLocationBean> getPiaAssignWc(Integer pcode) {
		return dao.getPiaAssignWc(pcode);
	}


@Override
public List<ProjectLocationBean> getPiaAssigndraftWc(Integer pcode) {
	return dao.getPiaAssigndraftWc(pcode);
}

@Override
public List<ProjectLocationBean> getPiaAssignfinalWc(Integer pcode) {
	return dao.getPiaAssignfinalWc(pcode);
}


@Override
public Boolean detPiaAssigndraftWc(Integer pwccode) {
	return dao.detPiaAssigndraftWc(pwccode);
}

@Override
public LinkedHashMap<String, List<ProjectLocationBean>> getmpngprolist(String user_id) {
	  return dao.getmpngprolist(user_id);
}

@Override
public List<ProjectLocationBean> getWsCommittee(Integer pcode) {
	return dao.getWsCommittee(pcode);
}

@Override
public List<ProjectLocationBean> getcheckWCStatus(Integer projectId) {
	return dao.getcheckWCStatus(projectId);
}

@Override
public List<ProjectLocationBean> getProjectLocationList(Integer state, Integer district, Integer project) {
	
	return dao.getProjectLocationList(state, district, project);
}

@Override
public List<ProjectLocationBean> getLocationAndBaselineDraft(Integer st_code, String userType) {
	
	return dao.getLocationAndBaselineDraft(st_code, userType);
}

}
