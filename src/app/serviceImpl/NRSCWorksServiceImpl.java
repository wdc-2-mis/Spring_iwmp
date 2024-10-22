package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.NRSCWorksBean;
import app.dao.NRSCWorksDao;
import app.service.NRSCWorksService;

@Service("NRSCWorksService")
public class NRSCWorksServiceImpl implements NRSCWorksService{
	
	@Autowired
	NRSCWorksDao nrscWorksDao;
	
	@Override
	public List<NRSCWorksBean> getNRSCWorks() {
		return nrscWorksDao.getNRSCWorks();
	}
	
	@Override
	public List<NRSCWorksBean> getNRSCDistWorks(Integer stcd) {
		return nrscWorksDao.getNRSCDistWorks(stcd);
	}
	
	@Override
	public List<NRSCWorksBean> getNRSCProjWorks(Integer dcode) {
		return nrscWorksDao.getNRSCProjWorks(dcode);
	}

}
