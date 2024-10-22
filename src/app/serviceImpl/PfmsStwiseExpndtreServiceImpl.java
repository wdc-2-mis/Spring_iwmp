package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.bean.pfms.PfmsTranxMappedWithProjBean;
import app.dao.PfmsStwiseExpndtreDao;
import app.service.PfmsStwiseExpndtreService;

 

@Service("PfmsStwiseExpndtreService")
public class PfmsStwiseExpndtreServiceImpl implements PfmsStwiseExpndtreService{
	
	@Autowired
	PfmsStwiseExpndtreDao pfmsStwiseExpndtreDao;

	@Override
	public List<PfmsStwiseExpndtreBean> getStwiseExpndtr() {
		// TODO Auto-generated method stub
		return pfmsStwiseExpndtreDao.getStwiseExpndtr();
	}

	@Override
	public List<PfmsStwiseExpndtreBean> getDistwiseExpndtr(int stCode) {
		// TODO Auto-generated method stub
		return pfmsStwiseExpndtreDao.getDistwiseExpndtr(stCode);
	}

	@Override
	public List<PfmsTranxMappedWithProjBean> getTrnxMappedWithProjData(int distCode) {
		// TODO Auto-generated method stub
		return pfmsStwiseExpndtreDao.getTrnxMappedWithProjData(distCode);
	}

}
