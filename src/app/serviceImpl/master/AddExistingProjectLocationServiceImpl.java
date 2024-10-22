package app.serviceImpl.master;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.ProjectLocationBean;
import app.dao.master.AddExistingProjectLocationDao;
import app.service.master.AddExistingProjectLocationService;

@Service("AddExistingProjectLocationService")
public class AddExistingProjectLocationServiceImpl implements AddExistingProjectLocationService {

	@Autowired(required = true)
	AddExistingProjectLocationDao dao;
	
	@Override
	public LinkedHashMap<Integer, String> getProjectLocationProject(Integer regId) {
		
		return dao.getProjectLocationProject(regId);
	}

	@Override
	public LinkedHashMap<Integer, String> getVillageBlockWise(Integer bcode, Integer project) {
		// TODO Auto-generated method stub
		return dao.getVillageBlockWise(bcode, project);
	}

	@Override
	public Boolean saveProjectLocationAsDraft(List<Integer> vCode, Integer pcode, String loginId) {
		// TODO Auto-generated method stub
		return dao.saveProjectLocationAsDraft(vCode, pcode, loginId);
	}

	@Override
	public List<ProjectLocationBean> getPiaAssignWc(Integer pcode) {
		// TODO Auto-generated method stub
		return dao.getPiaAssignWc(pcode);
	}

	@Override
	public Boolean saveWCLocationAsDraft(List<String> activity, List<Integer> plid, String loginId, Integer project) {
		// TODO Auto-generated method stub
		return dao.saveWCLocationAsDraft(activity, plid, loginId, project);
	}

	@Override
	public Boolean completeWCMappingExisting(String loginId, Integer projid) {
		// TODO Auto-generated method stub
		return dao.completeWCMappingExisting(loginId, projid);
	}

	@Override
	public List<ProjectLocationBean> getPiaAssignWcFirst(Integer pcode) {
		// TODO Auto-generated method stub
		return dao.getPiaAssignWcFirst( pcode);
	}

}
