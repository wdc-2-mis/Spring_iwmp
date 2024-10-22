package app.dao.unfreeze;

import java.util.List;

import app.bean.UnfreezeEpaLivProdBean;

public interface UnfreezeEpaLivProdDao {
	
	List<UnfreezeEpaLivProdBean> getUnfreezeEpaLivProdData(Integer projid, String head);
	boolean unfreezeEpaLivProdData(String actCode[], String head);
	
}
