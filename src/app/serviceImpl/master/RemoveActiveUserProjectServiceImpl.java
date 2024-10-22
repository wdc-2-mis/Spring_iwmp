package app.serviceImpl.master;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.master.RemoveActiveUserProjectDao;
import app.model.IwmpMProject;
import app.service.master.RemoveActiveUserProjectService;

@Service("removeActiveUserProject")
public class RemoveActiveUserProjectServiceImpl implements RemoveActiveUserProjectService{

	
	@Autowired
	private RemoveActiveUserProjectDao dao;
	
	
	@Override
	public Map<String, String> getUserProjectList(String regId) {
		Map<String, String> projList=new LinkedHashMap<String, String>();
		for (IwmpMProject temp : dao.getUserProjectList(regId)) 
		{
			projList.put(temp.getProjectId()+"", temp.getProjName());
		}
		return projList ;
	}


	@Override
	public Integer userProjectRemove(String regId, String project) {
		
		return dao.userProjectRemove(regId, project);
	}

}
