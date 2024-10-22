package app.service;

import org.springframework.stereotype.Service;

import app.bean.ProjectPrepareBean;
import app.model.master.IwmpMProjectPrepare;
import app.model.project.IwmpProjectPrepare;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

@Service("ProjectPrepareService")
public interface ProjectPrepareService {
	
	List<IwmpMProjectPrepare> getprojectPreparedness();
	Integer saveProjectPreparedness(String ProjId, HttpSession session, HashMap<Integer,String> map);
	List<ProjectPrepareBean> getAddPreparedness(Integer ProjId);

}
