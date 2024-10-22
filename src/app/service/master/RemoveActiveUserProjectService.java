package app.service.master;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service("removeActiveUserProject")
public interface RemoveActiveUserProjectService {
	
	Map<String, String>  getUserProjectList(String regId);
	Integer userProjectRemove(String regId, String project);

}
