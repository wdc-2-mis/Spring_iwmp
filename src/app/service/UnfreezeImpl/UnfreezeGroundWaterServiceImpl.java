package app.service.UnfreezeImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.bean.GroundWaterTableBean;
import app.dao.unfreeze.UnfreezeGroundWaterDao;
import app.service.unfreeze.UnfreezeGroundWaterService;

@Service("UnfreezeGroundWaterService")
public class UnfreezeGroundWaterServiceImpl implements UnfreezeGroundWaterService{

	@Autowired
	UnfreezeGroundWaterDao gwDao;

	@Override
	public List<GroundWaterTableBean> unfreezeListGW(Integer proj, String level, Integer dcode, Integer finyr) {
		
		return gwDao.unfreezeListGW(proj, level, dcode, finyr);
	}

	@Override
	public boolean unfreezeGWTData(String[] proj_id, String level, Integer dcode, Integer finyr) {
		
		return gwDao.unfreezeGWTData(proj_id,level, dcode, finyr);
	}


	
	
	
}


