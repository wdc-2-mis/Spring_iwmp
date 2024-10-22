package app.dao.unfreeze;

import java.util.List;

import app.bean.ShgDetailBean;

public interface UnfreezeShgDetailDao {

	List<ShgDetailBean> unfreezeListShgDetails(Integer project, String grp, String headType);
	
	public boolean unfreezeShgDetailsData(String[] shg_id, String headType);
}
