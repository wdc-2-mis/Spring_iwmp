package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.PfmsStateDistProjExpndtrBean;
import app.dao.PfmsStateDistProjExpndtrDao;
import app.service.PfmsStateDistProjExpndtrService;

@Service
public class PfmsStateDistProjExpndtrServiceImpl implements PfmsStateDistProjExpndtrService {
	
	@Autowired
	PfmsStateDistProjExpndtrDao pfmsStDistProjExpndDao;

	@Override
	public List<PfmsStateDistProjExpndtrBean> getStatewiseExpndtr() {
		// TODO Auto-generated method stub
		return pfmsStDistProjExpndDao.getStatewiseExpndtr();
	}

	@Override
	public List<PfmsStateDistProjExpndtrBean> getDistwiseExpndtr(Integer stCode) {
		// TODO Auto-generated method stub
		return pfmsStDistProjExpndDao.getDistwiseExpndtr(stCode);
	}

	@Override
	public List<PfmsStateDistProjExpndtrBean> getProjwiseExpndtr(Integer dcode) {
		// TODO Auto-generated method stub
		return pfmsStDistProjExpndDao.getProjwiseExpndtr(dcode);
	}

}
