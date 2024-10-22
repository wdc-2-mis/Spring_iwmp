package app.dao.unfreeze;

import java.util.List;

import app.bean.PfmsTransactionBean;

public interface UnfreezePfmsTransactionMappingDao {
	
	List<PfmsTransactionBean> getCompPfmsTranMapWithProj(Integer projId);
	String updateAsDraftPfmsTransaction(String[] eatmisdataId, Integer projId);
	List<PfmsTransactionBean> getCompPfmsTranMapWithWorkId(Integer fyear, Integer projId);

}
