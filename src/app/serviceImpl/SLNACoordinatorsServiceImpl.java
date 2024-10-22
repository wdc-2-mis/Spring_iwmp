package app.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.SLNACoordinatorsBean;
import app.dao.SLNACoordinatorsDao;
import app.service.SLNACoordinatorsService;

@Service
public class SLNACoordinatorsServiceImpl implements SLNACoordinatorsService {
	
	@Autowired
	private SLNACoordinatorsDao slnaCoordinatorsDao;
	
	@Override
	public List<SLNACoordinatorsBean> getSLNACoordinatorsList() {
		// TODO Auto-generated method stub
		return slnaCoordinatorsDao.getSLNACoordinatorsList();
	}

	@Override
	public String updateSLNACrdntrDetails(Integer stCode, String name, String email, BigDecimal mobile) {
		// TODO Auto-generated method stub
		return slnaCoordinatorsDao.updateSLNACrdntrDetails(stCode, name, email, mobile);
	}

}
