package app.service.unfreeze;

import java.util.List;

import app.bean.UnfreezeEpaLivProdBean;

public interface UnfreezeEpaLivProdService {
	
	List<UnfreezeEpaLivProdBean> getUnfreezeEpaLivProdData(Integer projid, String head);
	boolean unfreezeEpaLivProdData(String actCode[], String head);
	
	List<UnfreezeEpaLivProdBean> getUnfreezeWorkIdEpaLivProd(Integer projid, String head, Integer act);
	boolean unfreezeWorkIdEpaLivProdData(String actCode[], String head);
	
}
