package app.service.UnfreezeImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.PfmsTransactionBean;
import app.dao.unfreeze.UnfreezePfmsTransactionMappingDao;
import app.service.unfreeze.UnfreezePfmsTransactionMappingService;

@Service
public class UnfreezePfmsTransactionMappingServiceImpl implements UnfreezePfmsTransactionMappingService {

	@Autowired
	UnfreezePfmsTransactionMappingDao pfmsTranMapDao;
	
	@Override
	public List<PfmsTransactionBean> getCompPfmsTranMapWithProj(Integer projId) {
		return pfmsTranMapDao.getCompPfmsTranMapWithProj(projId);
	}

	@Override
	public String updateAsDraftPfmsTransaction(String[] eatmisdataId, Integer projId) {
		return pfmsTranMapDao.updateAsDraftPfmsTransaction(eatmisdataId, projId);
	}

	@Override
	public List<PfmsTransactionBean> getCompPfmsTranMapWithWorkId(Integer fyear, Integer projId) {
		return pfmsTranMapDao.getCompPfmsTranMapWithWorkId(fyear, projId);
	}

}
