package app.dao.unfreeze;

import java.util.List;

import app.UnfreezeWorkStatusDataBean;

public interface UnfreezeWorkStatusDao {
	
	List<UnfreezeWorkStatusDataBean> getUnfreezeWorkStatusData(Integer finyr, Integer project, Integer district);
	boolean unfreezeWorkStatusComplete(String workcode[], String project, String finCode);

}
