package app.dao.master;

import java.util.List;


import app.model.IwmpMProject;

public interface RemoveActiveUserProjectDao {
	
	List<IwmpMProject>   getUserProjectList(String regId);
	Integer userProjectRemove(String regId, String project);

}
