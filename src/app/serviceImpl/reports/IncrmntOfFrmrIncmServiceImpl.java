package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.reports.IncrmntOfFrmrIncmBean;
import app.dao.reports.IncrmntOfFrmrIncmDao;
import app.service.reports.IncrmntOfFrmrIncmService;

@Service("IncrmntOfFrmrIncmService")
public class IncrmntOfFrmrIncmServiceImpl implements IncrmntOfFrmrIncmService {

	@Autowired
	IncrmntOfFrmrIncmDao incrmntOfFrmrIncmDao;
	
	@Override
	public List<IncrmntOfFrmrIncmBean> getStWiseFrmrIncmDetail(int stcode, int finyrcd) {
		// TODO Auto-generated method stub
		return incrmntOfFrmrIncmDao.getStWiseFrmrIncmDetail(stcode,finyrcd);
	}

	@Override
	public List<IncrmntOfFrmrIncmBean> getStWiseAreaPercentage() {
		// TODO Auto-generated method stub
		return incrmntOfFrmrIncmDao.getStWiseAreaPercentage();
	}

	@Override
	public List<IncrmntOfFrmrIncmBean> getTarIncmAreaDetails(int finyrcd) {
		// TODO Auto-generated method stub
		return incrmntOfFrmrIncmDao.getTarIncmAreaDetails(finyrcd);
	}

	@Override
	public List<IncrmntOfFrmrIncmBean> getDistWiseFrmrIncmDetail(int stcode, int finyrcd) {
		// TODO Auto-generated method stub
		return incrmntOfFrmrIncmDao.getDistWiseFrmrIncmDetail(stcode,finyrcd);
	}

	@Override
	public List<IncrmntOfFrmrIncmBean> getProjWiseFrmrIncmDetail(int dcode, int finyrcd) {
		// TODO Auto-generated method stub
		return incrmntOfFrmrIncmDao.getProjWiseFrmrIncmDetail(dcode, finyrcd);
	}

}
