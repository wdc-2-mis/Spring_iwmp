package app.dao;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.bean.ProjectPrepareBean;
import app.model.master.IwmpMProjectPrepare;
import app.model.project.IwmpProjectPrepare;

public interface ProjectPrepareDao {
	
	List<IwmpMProjectPrepare> getprojectPreparedness();
	Integer saveProjectPreparedness(String ProjId, HttpSession session, HashMap<Integer,String> map);
	List<ProjectPrepareBean> getAddPreparedness(Integer ProjId);

}
