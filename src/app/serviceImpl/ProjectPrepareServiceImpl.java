package app.serviceImpl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.ProjectPrepareBean;
import app.dao.ProjectPrepareDao;
import app.model.master.IwmpMProjectPrepare;
import app.model.project.IwmpProjectPrepare;
import app.service.ProjectPrepareService;

@Service("ProjectPrepareService")
public class ProjectPrepareServiceImpl implements ProjectPrepareService{

	@Autowired
	private ProjectPrepareDao  ppDao;
	
	@Override
	public List<IwmpMProjectPrepare> getprojectPreparedness() {
		
		return ppDao.getprojectPreparedness();
	}

	

	@Override
	public List<ProjectPrepareBean> getAddPreparedness(Integer ProjId) {
		return ppDao.getAddPreparedness( ProjId);
	}



	@Override
	public Integer saveProjectPreparedness(String ProjId, HttpSession session, HashMap<Integer, String> map) {
		// TODO Auto-generated method stub
		return ppDao.saveProjectPreparedness(ProjId, session, map);
	}

}
