package app.service.unfreeze;

import java.util.List;

import org.springframework.stereotype.Service;

import app.UnfreezeWorkStatusDataBean;

@Service("UnfreezeWorkStatusServices")
public interface UnfreezeWorkStatusServices {
	
	List<UnfreezeWorkStatusDataBean> getUnfreezeWorkStatusData(Integer finyr, Integer project, Integer district);
	boolean unfreezeWorkStatusComplete(String workcode[], String project, String finCode);

}
