package app.service.UnfreezeImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.UnfreezeEpaLivProdBean;
import app.dao.unfreeze.UnfreezeEpaLivProdDao;
import app.service.unfreeze.UnfreezeEpaLivProdService;

@Service("UnfreezeEpaLivProdService")
public class UnfreezeEpaLivProdServiceImpl implements UnfreezeEpaLivProdService{
	
	@Autowired(required = true)
	UnfreezeEpaLivProdDao elpdao;
	
	@Override
	public List<UnfreezeEpaLivProdBean> getUnfreezeEpaLivProdData(Integer projid, String head) {
		return elpdao.getUnfreezeEpaLivProdData(projid, head);
	}

	@Override
	public boolean unfreezeEpaLivProdData(String[] actCode, String head) {
		return elpdao.unfreezeEpaLivProdData(actCode, head);
	}
	
	@Override
	public List<UnfreezeEpaLivProdBean> getUnfreezeWorkIdEpaLivProd(Integer projid, String head, Integer act) {
		return elpdao.getUnfreezeWorkIdEpaLivProd(projid, head, act);
	}
	
	@Override
	public boolean unfreezeWorkIdEpaLivProdData(String[] actCode, String head) {
		return elpdao.unfreezeWorkIdEpaLivProdData(actCode, head);
	}

}
